package com.spark.devproj.sparksql

import com.spark.devproj.config.Common
import com.spark.devproj.constants.Constants
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object DiffWaysDataFrameCreation {

  def main(args: Array[String]): Unit = {
    // Spark Session Configuration

    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("Ways of DataFrame Creation")
      .getOrCreate()

    // 1. Create Spark DataFrame from RDD
    val colums = Seq("language", "users_count")
    val rawData = Seq(("Java", "20000"), ("Python", "30000"), ("Scala", "40000"))
    val sqlContext = spark.sqlContext
    val dataRDD = spark.sparkContext.parallelize(rawData)


    //    1.a) Using toDF() functions
    import spark.implicits._
    val dfFromRDD1 = dataRDD.toDF("language", "user_count")
    dfFromRDD1.show()

    //    1.b) Using Spark createDataFrame() from SparkSession
    //From RDD (USING createDataFrame)
    val dfFromRDD2 = spark.sqlContext.createDataFrame(dataRDD).toDF()
    //From RDD (USING createDataFrame) with columns
    val dfFromRDD3 = spark.sqlContext.createDataFrame(dataRDD).toDF(colums: _*)
    dfFromRDD2.show()
    dfFromRDD3.show()

    //    1.c) Using createDataFrame() with the Row type
    //From RDD (USING createDataFrame and Adding schema using StructType)
    //convert RDD[T] to RDD[Row]

    val schema = StructType(colums.map(fieldName => StructField(fieldName, StringType, nullable = true)))
    val rowRDD = dataRDD.map(attributes => Row(attributes._1, attributes._2))
    val dfFromRDD4 = spark.createDataFrame(rowRDD, schema)
    println("1.c) Using createDataFrame() with the Row type")
    dfFromRDD4.show()

    println("3. Creating Spark DataFrame from CSV")

    val csvDF = spark.read.csv(Common.inputFile("OrderData.csv"))
    val csvDF2 = spark.sqlContext.read.csv(Common.inputFile("1800.csv"))
    csvDF.show()
    csvDF2.show()

    println("5. Creating from JSON file")
    val jsonDF = spark.sqlContext.read.json(Common.inputFile("sample.json"))
    jsonDF.show()
  }
}
