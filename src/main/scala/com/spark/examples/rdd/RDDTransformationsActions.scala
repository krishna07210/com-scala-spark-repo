package com.spark.examples.rdd

import com.spark.devproj.config.CommonUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object RDDTransformationsActions {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[5]")
      .appName("RDDRepartitionVsCoalesce")
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)
    val rdd = spark.sparkContext.textFile(CommonUtils.getInputFilePath("word_count.text"), 3)
    rdd.saveAsTextFile(CommonUtils.getOutputFilePath("tmp/rdd/wordfile"))
    val rdd2 = rdd.flatMap(f => f.split(" "))
    rdd2.saveAsTextFile(CommonUtils.getOutputFilePath("tmp/rdd/FlatMap"))
    val rdd3 = rdd2.map(m => (m, 1))
    val rdd4 = rdd3.filter(a => a._1.replace(",", "").startsWith("a"))
    //    rdd4.foreach(println)
    val rdd5 = rdd4.reduceByKey((accumulatedValue, currentValue) => accumulatedValue + currentValue)
    println("--- RDD5 ----")
    //    rdd5.foreach(println)
    val rdd6 = rdd4.reduceByKey(_ + _, 4)
    println("--- RDD6 ----")
    //    rdd6.foreach(println)
    val rdd7 = rdd5.map(a => (a._2, a._1)).sortByKey()
    rdd7.foreach(println)
    //Action - count
    println("Count : " + rdd6.count())
    val firstRec = rdd6.first()
    println("First Record : " + firstRec._1 + "," + firstRec._2)

    //Action - max
    val datMax = rdd6.max()
    println("Max Record : " + datMax._1 + "," + datMax._2)

    //Action - reduce
      val totalWordCount = rdd6.reduce((a, b) => (a._1 + b._1, a._2))
      println("dataReduce Record : " + totalWordCount._1)

    //Action - take
    val data3 = rdd6.take(3)
    data3.foreach(f => {
      println("data3 Key:" + f._1 + ", Value:" + f._2)
    })

    //Action - collect
    /**
     * collect – Returns all data from RDD as an array. Be careful when you use
     * this action when you are working with huge RDD
     */
    val data = rdd6.collect()
    data.foreach(f=>{
      println("Key:"+ f._1 +", Value:"+f._2)
    })

  }
}
