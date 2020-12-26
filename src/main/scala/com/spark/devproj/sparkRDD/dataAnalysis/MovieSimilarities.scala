package com.spark.devproj.sparkRDD.dataAnalysis

import java.nio.charset.CodingErrorAction

import com.spark.devproj.config.Common
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.{Codec, Source}

/**
 * Author   : Krish - htharibo
 * Date     : 4/27/2020
 * Details  : Extract similarities for the movie
 *
 */
object MovieSimilarities {

  type MovieRating = (Int, Double)
  type UserRatingPair = (Int, (MovieRating, MovieRating))
  type RatingPair = (Double, Double)
  type RatingPairs = Iterable[RatingPair]

  def main(args: Array[String]): Unit = {

    val sc: SparkContext = new SparkContext(new SparkConf().setAppName("MovieSimilarities").setMaster("local[*]"))
    val movieName = loadMovieName()

    //Map ratings to key / value pairs: user ID => movie ID, rating
    val ratings = sc.textFile(Common.inputFile("ml-100k/u.data"))
      .map(line => line.split("\t"))
      .map(rec => (rec(0).toInt, (rec(1).toInt, rec(2).toDouble))) //.filter(x => x._1 == 4)
    val joinedRating = ratings.join(ratings)
    val uniqueJoinedRatings = joinedRating.filter(filterDuplicates)
    //    uniqueJoinedRatings.saveAsTextFile(Common.outputFile("Ratings"))
    val moivePairs = uniqueJoinedRatings.map(makePairs)
    val moviePairRatings = moivePairs.groupByKey()
    val moviePairSimilarities = moviePairRatings.mapValues(computeCosineSimilarity).cache()
    //    moviePairSimilarities.sortByKey().saveAsTextFile(Common.outputFile("Ratings"))

    // Extract similarities for the movie we care about that are "good".
    val scoreThreshold = 0.97;
    val movieID = 50 // Input movie
    val coOccuranceThreshold = 50.0
    // Filter for movies with this sim that are "good" as defined by our quality thresholds above
    val filteredResult = moviePairSimilarities.filter(x => {
      val pair = x._1
      val sim = x._2
      (pair._1 == movieID || pair._2 == movieID) && sim._1 > scoreThreshold && sim._2 > coOccuranceThreshold
    })
    val results = filteredResult.map(x => (x._2, x._1)).sortByKey(false).take(10)

    println("\nTop 10 similar movies for " + movieName(movieID))
    for (result <- results) {
      val sim = result._1
      val pair = result._2
      // Display the similarity result that isn't the movie we're looking at
      var similarMovieID = pair._1
      if (similarMovieID == movieID) {
        similarMovieID = pair._2
      }
      println(movieName(similarMovieID) + "\tscore: " + sim._1 + "\tstrength: " + sim._2)
    }

  }

  def loadMovieName(): Map[Int, String] = {
    /**
     * Handling Character encoding issues:
     * Exception in thread "main" java.nio.charset.MalformedInputException
     */
    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    val lines = Source.fromFile(Common.inputFile("ml-100k/u.item")).getLines()
    var movieNames: Map[Int, String] = Map()

    for (line <- lines) {
      var fields = line.split('|')
      if (fields.length > 1) movieNames += fields(0).toInt -> fields(1).toString
    }
    return movieNames
  }

  def filterDuplicates(userRatingPair: UserRatingPair): Boolean = {
    val movieRating1 = userRatingPair._2._1
    val movieRating2 = userRatingPair._2._2
    val movie1 = movieRating1._1
    val movie2 = movieRating2._1
    return movie1 < movie2
  }

  def makePairs(userRating: UserRatingPair) = {
    val movieRating1 = userRating._2._1
    val movieRating2 = userRating._2._2
    val movie1 = movieRating1._1
    val rating1 = movieRating1._2
    val movie2 = movieRating2._1
    val rating2 = movieRating2._2
    ((movie1, movie2), (rating1, rating2))

  }

  def computeCosineSimilarity(ratingPairs: RatingPairs): (Double, Int) = {
    var numPairs: Int = 0
    var sum_xx: Double = 0.0
    var sum_yy: Double = 0.0
    var sum_xy: Double = 0.0

    for (pair <- ratingPairs) {
      val ratingX = pair._1
      val ratingY = pair._2
      sum_xx += ratingX * ratingX
      sum_yy += ratingY * ratingY
      sum_xy += ratingX * ratingY
      numPairs += 1
    }

    val numerator: Double = sum_xy
    val denominator: Double = math.sqrt(sum_xx) * math.sqrt(sum_yy)
    var score: Double = 0.0

    if (denominator > 0) score = numerator / denominator

    return (score, numPairs)
  }
}
