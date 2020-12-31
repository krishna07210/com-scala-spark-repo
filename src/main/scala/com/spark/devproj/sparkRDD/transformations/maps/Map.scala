package com.spark.devproj.sparkRDD.transformations.maps

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Author   : Krish - htharibo
 * Date     : 4/27/2020
 * Details  :
 *
 */
object Map {

  def main(args: Array[String]): Unit = {

    val sc: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("Map"))

    val mountEverest = sc.textFile(CommonUtils.getInputFilePath("MountEverest.txt"))

    //1. Split the Mount Everest text into individual words
    val words = mountEverest.map(x => x.split(" "))
    words.collect().foreach(x => println(x.toString()))
    //2.  get the length or size of collection.
    val size = mountEverest.map(x => x.split(" ").length).collect()
    println(s"size = $size")
    //3. get total number of characters in the file

  }
}
