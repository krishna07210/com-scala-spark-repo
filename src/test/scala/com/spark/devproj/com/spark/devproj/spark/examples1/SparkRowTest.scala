package com.spark.devproj.com.spark.devproj.spark.examples1

import java.sql.Date

import com.spark.devproj.sparkDataframeAPIs.Rows.RowDemo.toDateDF
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuite}

case class myRow (ID:String,EventDate: Date)

class SparkRowTest extends FunSuite with BeforeAndAfterAll {

  Logger.getLogger("org").setLevel(Level.ERROR)
  @transient var myDF: DataFrame = _
  @transient var spark: SparkSession = _

  override def beforeAll(): Unit = {
    spark = SparkSession.builder()
      .appName("Demo Row Test")
      .master("local[3]")
      .getOrCreate()

    val mySchema = StructType(List(
      StructField("ID", StringType),
      StructField("EventDate", StringType)))

    val myRows = List(Row("123", "04/05/2020"), Row("124", "4/5/2020"), Row("125", "04/5/2020"), Row("125", "4/05/2020"))
    val myRDD = spark.sparkContext.parallelize(myRows, 2)
    myDF = spark.createDataFrame(myRDD, mySchema)
  }

  override def afterAll(): Unit = {
    spark.stop()
  }

  test("Test Data Types") {
    val rowList = toDateDF(myDF, "M/d/y", "EventDate").collectAsList()
    rowList.forEach(r =>
      assert(r.get(1).isInstanceOf[Date], "Second column should be Date")
    )
  }
  test("Test Data Value") {
    val spark2 = spark
    import spark2.implicits._
    val rowList = toDateDF(myDF, "M/d/y", "EventDate").as[myRow].collectAsList()
    rowList.forEach(r =>
      assert(r.EventDate.isInstanceOf[Date], "Second column should be Date")
    )
  }
}
