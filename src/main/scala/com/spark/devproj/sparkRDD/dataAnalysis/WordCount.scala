package com.spark.devproj.sparkRDD.dataAnalysis

import com.spark.devproj.config.{Common, SparkConfiguration}
import org.apache.spark.SparkContext

/**
 * Created by krish on 06-04-2020.
 * Work Count Program
 */

object WordCount {

  def main(args: Array[String]): Unit = {

    println("********* Word Count Example ********")
    val sparkConfig = new SparkConfiguration()
    val sc: SparkContext = sparkConfig.localConfig("local", "WordCount")
    val threshold = 10
    val textFile = sc.textFile(Common.inputFile("book.txt"))
    /*
     Example 1:  Word Count
    val words = textFile.flatMap(x=> x.split("\\W+"))
    val wordCount = words.countByValue()
    //    val wordCount = words.map(word => (word,1)).reduceByKey(_+_)
    val filterCount = wordCount.filter(_._2>=threshold)
    filterCount.foreach(println)
    */

    /* Example 2 :
    val wordCount = textFile.flatMap(x => x.split("\\W+")).countByValue().filter(x => x._2 >= threshold)
    wordCount.foreach(x => println(x._1 + " " + x._2))
    */
    val wordCount = textFile.flatMap(x => x.split("\\W+")).map(x => (x.toUpperCase(), 1)).reduceByKey((x, y) => (x + y)).sortByKey()
    wordCount.foreach(x => println(x._1 + " " + x._2))
  }
}
