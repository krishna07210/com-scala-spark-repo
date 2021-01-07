package com.spark.devproj.sparkDataframeAPIs.Transformations

import com.spark.devproj.sparkDataframeAPIs.udfs.UDFDemo
import com.spark.devproj.config.SparkConfigs
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.IntegerType

object MiscellaneousTransformations {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName);
    val spark = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("MiscellaneousTransformations"))
      .getOrCreate()

    val dataList = List(
      ("Ravi", "28", "1", "2002"),
      ("Abdul", "23", "5", "81"), // 1981
      ("John", "12", "12", "6"), // 2006
      ("Rosy", "7", "8", "63"), // 1963
      ("Abdul", "23", "5", "81")
    )

    val rawDF = spark.createDataFrame(dataList)
      .toDF("name", "day", "month", "year").repartition(3)
    rawDF.printSchema()

    import org.apache.spark.sql.functions.monotonically_increasing_id
    import org.apache.spark.sql.functions._

    //case when year < 21 then cast(year as int) + 2000
    //when year < 100 then cast(year as int) + 1900

    // 1. Way of Casting
    //    val finalDF = rawDF.withColumn("id", monotonically_increasing_id)
    //      .withColumn("year", expr(
    //        """
    //          |case when year <21 then cast(year as int) +2000
    //          |when year <100 then cast(year as int) +1900
    //          |else year
    //          |end
    //          |""".stripMargin))
    //    finalDF.show()

    // 2. way of casting
    //    val finalDF = rawDF.withColumn("id", monotonically_increasing_id)
    //      .withColumn("year", expr(
    //        """
    //          |case when year <21 then cast(year as int) +2000
    //          |when year <100 then cast(year as int) +1900
    //          |else year
    //          |end
    //          |""".stripMargin).cast(IntegerType))
    // 3. way of casting
    val finalDF = rawDF.withColumn("id", monotonically_increasing_id)
      .withColumn("day", col("day").cast(IntegerType))
      .withColumn("month", col("month").cast(IntegerType))
      .withColumn("year", col("year").cast(IntegerType))
      /*
      .withColumn("year", expr(
        """
          |case when year <21 then cast(year as int) +2000
          |when year <100 then cast(year as int) +1900
          |else year
          |end
          |""".stripMargin))
          */
      .withColumn("year",
        when(col("year") < 21, col("year") + 2000)
          when(col("year") < 100, col("year") + 1900)
          otherwise (col("year")))
      .withColumn("dob", to_date(expr("concat(day,'/',month,'/',year)"), "d/M/y"))
      .drop("day", "month", "year")
      .dropDuplicates("name", "dob")
      .sort(expr("dob desc"))
    finalDF.show()

    //    finalDF.show()
  }
}
