package com.spark.devproj.sparkSQL.joins

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SparkSession, functions}

object UkMakerSpaces {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sparkSession = SparkSession
      .builder()
      .config(SparkConfigs.getLocalSparkConf("UkMakerSpaces"))
      .getOrCreate()

    println("############  MakerSpace ############")
    val makerSpace = sparkSession.read
      .option("header", "true")
      .csv(CommonUtils.getInputFilePath("uk-makerspaces-identifiable-data.csv"))

    System.out.println("=== Print 20 records of makerspace table ===")
    makerSpace.select("Name of makerspace", "Postcode").show()

    println("########### PostCode  ###############3")
    val postCode = sparkSession.read
      .option("header", "true")
      .csv(CommonUtils.getInputFilePath("uk-postcode.csv"))
      .withColumn("Postcode",
        functions.concat_ws("", functions.col("Postcode"), functions.lit(" ")))

    System.out.println("=== Print 20 records of postcode table ===")
    postCode.show()

    val joined = makerSpace.join(postCode, makerSpace.col("Postcode").startsWith(postCode.col("Postcode")), "left_outer")

    System.out.println("=== Group by Region ===")
    joined.groupBy("Region").count().show(200)
    sparkSession.stop()
  }
}
