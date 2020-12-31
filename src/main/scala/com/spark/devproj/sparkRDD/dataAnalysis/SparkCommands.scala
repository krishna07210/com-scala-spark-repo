package com.spark.devproj.sparkRDD.dataAnalysis

import com.spark.devproj.config.{CommonUtils, SparkConfiguration}
import org.apache.spark.SparkContext

/**
 * Created by krish on 06-04-2020.
 * Demo Program on Spark Commands
 */
object SparkCommands {

  def main(args: Array[String]): Unit = {

    val sparkConfig = new SparkConfiguration()
    val sc: SparkContext = sparkConfig.localConfig("local", "SparkCommand")
    val data = sc.textFile(CommonUtils.inputFile("udata.txt"))
    //    data.foreach(println)
    //Create an RDD through Parallelized Collection
    val no = Array(1, 2, 3, 4, 5, 6, 7)
    val noData = sc.parallelize(no, 2)
    noData.foreach(println)
    //From Existing RDDs
    val newRDD = no.map(data => data * 2)
    newRDD.foreach(println)
    val count = data.count()
    val filterData = data.filter(line => line.contains("878887116"))
    filterData.foreach(println)
    println(data.filter(line => line.contains("878887116")).count())
    val firstRec = data.first()
    //    firstRec.foreach(println)
    //Read the first 5 item from the RDD
    data.take(3).foreach(println)
    println(data.partitions.length)
    data.cache()
    data.count()
    data.collect()
    val wc = data.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)
    wc.foreach(println)
  }
}
