package com.spark.devproj.spark.training1.examples

import java.util.Properties

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import scala.io.Source
import scala.util.Properties

object HelloSpark extends Serializable {

  @transient lazy val logger: Logger = Logger.getLogger(HelloSpark.getClass.getName);

  def main(args: Array[String]): Unit = {
    logger.info("Starting Hello Spark")
    val spark = SparkSession.builder()
      .appName("HelloSpark")
      .master("local[3]")
      .getOrCreate();

    //Processing the Data

    logger.info("Finished Hello Spark");
    spark.stop();
  }

  def getSparkAppConf: SparkConf = {
    val sparkAppConf = new SparkConf
    //Set all Spark Configs
    val props = new Properties
    //    props.load(Source.fromFile("spark.conf").bufferedReader())
    //    props.forEach((k, v) => sparkAppConf.set(k.toString, v.toString))
    //    This is a fix for Scala 2.11
    import scala.collection.JavaConverters._
    props.asScala.foreach(kv => sparkAppConf.set(kv._1, kv._2))
    sparkAppConf
  }

}
