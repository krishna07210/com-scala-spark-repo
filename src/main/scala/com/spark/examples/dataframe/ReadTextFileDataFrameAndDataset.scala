package com.spark.examples.dataframe

import com.spark.devproj.config.CommonUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
 * 2. Spark read text file into DataFrame and Dataset
 * Using spark.read.text() and spark.read.textFile() We can read a single text file,
 * multiple files and all files from a directory into Spark DataFrame and Dataset.
 */

object ReadTextFileDataFrameAndDataset {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[2]")
      .appName("ReadTextFileDataFrameAndDataset")
      .getOrCreate()

    Logger.getLogger("org").setLevel(Level.ERROR)

    /**
     * 2.1 text() – Read text file into DataFrame
     * spark.read.text() method is used to read a text file into DataFrame. like in RDD,
     * we can also use this method to read multiple files at a time,
     * reading patterns matching files and finally reading all files from a directory.
     */
    println("***** DataFrame ****")
    val df: DataFrame = spark.read.text(CommonUtils.getInputFilePath("number.txt"))
    df.printSchema()
    df.show()

    //converting to columns by splitting
    import spark.implicits._
    val df2 = df.map(f => {
      val elements = f.getString(0).split(",")
      (elements(0), elements(1))
    })
    df2.printSchema()
    df2.show()

    /**
     * 2.2 textFile() – Read text file into Dataset
     * spark.read.textFile() method returns a Dataset[String], like text(),
     * we can also use this method to read multiple files at a time, reading
     * patterns matching files and finally reading all files from a directory
     * into Dataset.
     */

      println("***** DataSet ****")
    val ds :Dataset[String] = spark.read.textFile(CommonUtils.getInputFilePath("number.txt"))
    ds.printSchema()
    ds.show()

    import spark.implicits._
    val ds2 = ds.map( f=>{
      val elements = f.split(",")
      (elements(0),elements(1))
    })
    ds2.printSchema()
    ds2.show()
  }
}
