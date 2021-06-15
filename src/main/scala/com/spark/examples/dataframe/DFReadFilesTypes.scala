package com.spark.examples.dataframe

import com.spark.devproj.CaseClasses.OrderClass
import com.spark.devproj.config.CommonUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}

object DFReadFilesTypes {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("DFReadFilesTypes")
      .master("local[*]")
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)

    /*
    val df: DataFrame = spark.read
      .text(CommonUtils.getInputFilePath("sample_Orders.txt"))
    df.printSchema()
    df.show(3)

    def parseRowRecord(arr: Array[String]) = {
      OrderClass(arr(0).toString, arr(1).toString, arr(2).toString, arr(3).toString, arr(4).toString)
    }

    println("converting to columns by splitting older Version ")
    import spark.implicits._
    val df2 = df.map(f => {
      val elements = f.getString(0).split("|")
        parseRowRecord(elements)
    })
    df2.printSchema()
    df2.show(2)
*/

    val df: DataFrame = spark.read
      .format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .option("delimiter", "|")
      .option("path", CommonUtils.getInputFilePath("sample_Orders.txt"))
      .load()
    df.printSchema()
    df.show(3)


  }

}
