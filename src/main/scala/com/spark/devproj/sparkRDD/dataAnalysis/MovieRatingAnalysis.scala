package com.spark.devproj.sparkRDD.dataAnalysis

import java.nio.charset.CodingErrorAction

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

import scala.io.Codec
import scala.io.Source
import java.io.IOException

/**
 * Created by krish on 14-04-2020.
 * Find the movies with the most ratings
 */

object MovieRatingAnalysis {

  def sum(rating: Iterable[Int]): Int = {
    val iterator = rating.iterator
    var sumofRating = 0
    while (iterator.hasNext) {
      sumofRating = sumofRating + iterator.next().toInt
    }
    sumofRating
  }

  def count(rating: Iterable[Int]): Int = {
    val iterator = rating.iterator
    var countofRating = 0
    while (iterator.hasNext) {
      countofRating = countofRating + 1
    }
    countofRating
  }

  def parseMovieData(line: String) = {
    val fields = line.split("\t")
    val userID = fields(0)
    val movieID = fields(1).toInt
    val rating = fields(2).toInt
    val ratingTime = fields(3)
    (userID, movieID, rating, ratingTime)
  }

  /** Load up a Map of movie IDs to movie names. */
  def loadMovieNames(): Map[Int, String] = {
    // Handle character encoding issues:
    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)
    // Create a Map of Ints to Strings, and populate it from u.item.
    var movieNames: Map[Int, String] = Map()
    try {
      val lines = Source.fromFile(CommonUtils.inputFile("u.item")).getLines()
      for (line <- lines) {
        var fields = line.split('|')
        if (fields.length > 1) {
          movieNames += (fields(0).toInt -> fields(1))
        }
      }
    } catch {
      case e: IOException => e.printStackTrace()
    }
    return movieNames
  }

  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkConfigs.localConfig("local", "MovieRatingAnalysis")
    val movieData = sc.textFile(CommonUtils.inputFile("u.data"))
    val moviesDatSet = movieData.map(parseMovieData)
    val movies = moviesDatSet.map(x => (x._2, 1))
    val movieCount = movies.reduceByKey((x, y) => (x + y)).sortByKey()
    /*
    for (movie <- movieCount) {
      println(movie._1 + "\t" + movie._2)
    }
    */
    val flipped = movies.map(x => (x._2, x._1))
    val sortedMovies = flipped.sortByKey()
    val results = sortedMovies.collect()
    //results.foreach(println)
    /**
     *  1. Top User Rated Movie
     *  2. Least User Rated Movie
     *  3. Average Movie Rating
     */
    val topUserRatedMovie = movies.reduceByKey((x, y) => (x + y)).sortBy(_._2, ascending = false).take(1)
    val lestUserRatedMovie = movies.reduceByKey((x, y) => (x + y)).sortBy(_._2, ascending = false).take(1)
    /* --- Need to Work on Average Movie Rating
    val ds_movies = moviesDatSet.map(x => (x._2, x._3)).sortByKey()
    ds_movies.foreach(println)
    val grouped = ds_movies.groupByKey()
    grouped.foreach(println)
    val output = grouped.reduceByKey((x,y) => (sum(x) / count(x)))
    output.foreach(println)
    //average = sum_of(rating + number_of_people)/total_sum_of_people
  */
    topUserRatedMovie.foreach(println)
    /** Display the Movie Name in the Place of Movie Id
     * Create a broadcast variable of our ID -> movie name map
     */
    val nameDict = sc.broadcast(loadMovieNames)
    val loadMovieNa = loadMovieNames
    // Fold in the movie names from the broadcast variable
    val topmovieWithName = topUserRatedMovie.map(x => (nameDict.value(x._1), x._2))
    topmovieWithName.foreach(println)
  }
}
