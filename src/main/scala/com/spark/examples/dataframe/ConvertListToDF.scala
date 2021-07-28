package com.spark.examples.dataframe

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object ConvertListToDF {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]")
      .appName("ConvertListToDF ")
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)
    import spark.sqlContext.implicits._

    val x = (1 to 12).toList
    x.foreach(println)
    val numberDF = spark.sparkContext.parallelize(x).toDF()
    val l = List(("a", "b", "c", "d"))
    l.foreach(println)
    val list = spark.sparkContext.parallelize(List(("a1", "b1", "c1", "d1"), ("a2", "b2", "c2", "d2"))).toDF
    numberDF.show()
    list.show()

    val lstData = List(List("Hari", 34), List("Mounika", 26))
    lstData.foreach(println)
    val mapList = lstData.map { case List(a: String, b: Int) => (a, b) }
    val listToDF = spark.sparkContext.parallelize(mapList).toDF("Name", "Age")
    listToDF.show()
    val data = List(("Value1", "Cvalue1", 123, 2254, 22),("Value1", "Cvalue2", 124, 2255, 23));
    val df = spark.sparkContext.parallelize(data).toDF("Col1", "Col2", "Expend1", "Expend2","Expend3");
    val cols = Array("Expend1", "Expend2", "Expend3")


  }
}
