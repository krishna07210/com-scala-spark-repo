package com.spark.devproj.spark.examples1

import com.spark.devproj.config.CommonUtils.inputFile
import org.apache.log4j.Logger
import org.apache.spark.{SparkConf, SparkContext}

//case class SurveyRecord1(Age: Int, Gender: String, Country: String, state: String)

object HelloRDD extends Serializable {
  @transient lazy val logger: Logger = Logger.getLogger(HelloSpark.getClass.getName);

  def main(args: Array[String]): Unit = {

    val sparkAppConfig = new SparkConf().setAppName("HelloRDD").setMaster("local[3]")
    val sparkContext = new SparkContext(sparkAppConfig)

    val linesRDD = sparkContext.textFile(inputFile("survey_sample.csv"))
    val partitionedRDD = linesRDD.repartition(2)

    val colsRDD = partitionedRDD.map(lines => lines.split(",").map(_.trim))
    val selectRDD = colsRDD.map(cols => {
      SurveyRecord(cols(1).toInt, cols(2), cols(3), cols(4))
    })
    val filteredRDD = selectRDD.filter(row => row.Age < 40)
    val kvRDD = filteredRDD.map(row => (row.Country, 1))
    val countRDD = kvRDD.reduceByKey((v1, v2) => v1 + v2)
    logger.info(countRDD.collect().mkString(","))
    sparkContext.stop()
  }
}
