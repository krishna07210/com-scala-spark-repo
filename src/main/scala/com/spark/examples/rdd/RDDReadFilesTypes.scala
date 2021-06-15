package com.spark.examples.rdd

import com.spark.devproj.config.CommonUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RDDReadFilesTypes {
  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("readFilesTypes")
      .getOrCreate()

    sparkSession.sparkContext.setLogLevel("ERROR")

    println("##spark read text files from a directory into RDD")
    val rddFromFile = sparkSession.sparkContext.textFile(CommonUtils.getInputFilePath("1800.csv"));
    println(rddFromFile.getNumPartitions);
    println(rddFromFile.getClass)
    rddFromFile.take(5).foreach(println)

    println("##Get data Using collect")
    rddFromFile.take(5).foreach(f => {
      println(f)
    })

    println("##read multiple text files into a RDD")
    val rdd4 = sparkSession.sparkContext.textFile(CommonUtils.getInputFilePath("1800.csv") + "," +
      CommonUtils.getInputFilePath("1800_1.csv"))
    rdd4.take(10).foreach(f => {
      println(f)
    })

    println("##read text files base on wildcard character")
    val rdd3 = sparkSession.sparkContext.textFile(CommonUtils.getInputFilePath("1800*.csv"))
    rdd3.take(3).foreach(f => {
      println(f)
    })

    //    println("##read all text files from a directory to single RDD")
    //    val rdd2 = sparkSession.sparkContext.textFile(CommonUtils.getInputFilePath("*"))
    //    rdd2.take(5).foreach(f=>{
    //      println(f)
    //    })


    println("##read whole text files")
    val rddWhole: RDD[(String, String)] = sparkSession.sparkContext.wholeTextFiles(CommonUtils.getInputFilePath("1800*.csv"));
    rddWhole.foreach(f => {
      println("File Path : " + f._1)
      //      println(f._1 + "=>" + f._2)
    })
  }
}
