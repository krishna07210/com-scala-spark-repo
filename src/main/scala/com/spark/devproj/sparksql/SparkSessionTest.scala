package com.spark.devproj.sparksql

import org.apache.spark.sql.{Row, SparkSession}

object SparkSessionTest {
  def main(args: Array[String]): Unit = {

    val sparkSession1 = SparkSession.builder()
      .master("local[1]")
      .appName("SparkSession1Example")
      .getOrCreate()

    println("First SparkContext")
    println("App Name : " + sparkSession1.sparkContext.appName)
    println("Master : " + sparkSession1.sparkContext.master)

    val sparkSession2 = SparkSession.builder()
      .master("local[1]")
      .appName("SparkSession2Example")
      .getOrCreate()

    println("Second SparkContext")
    println("App Name : " + sparkSession2.sparkContext.appName)
    println("Master : " + sparkSession2.sparkContext.master)

    val sparkContext = sparkSession1.sparkContext

    case class Employee(Name: String, Age: Int, Sal: Int)
    val dataRDD = sparkContext.parallelize(Seq(("Hari", 35), ("Krishna", 40), ("Likith", 5)))
    //    println("Access Data ::")
    //    dataRDD.sortByKey().foreach(println)

    val sqlContext = sparkSession1.sqlContext

    val dataDFwithoutSchema = sqlContext.createDataFrame(dataRDD).toDF()
    dataDFwithoutSchema.show()
    val dataDFwithSchema = sqlContext.createDataFrame(dataRDD).toDF("Name", "Age")
    dataDFwithSchema.show()

    val employeeRDD = sparkContext.parallelize(Seq(Employee("Hari", 35, 9000), Employee("Krishna", 32, 1000), Employee
    ("Likith", 33, 9000)))


  }

}
