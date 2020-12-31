package com.spark.devproj.sparkRDD.dataAnalysis.examples2

import com.spark.devproj.config.CommonUtils
import com.spark.devproj.sparkDataframe.miscellaneous.HelloSparkDataframe
import com.spark.devproj.sparkDatasets.miscellaneous.SurveyRecord
import org.apache.log4j.Logger
import org.apache.spark.{SparkConf, SparkContext}

object HelloRDD extends Serializable {
  @transient lazy val logger: Logger = Logger.getLogger(HelloSparkDataframe.getClass.getName);

  def main(args: Array[String]): Unit = {

    val sparkAppConfig = new SparkConf().setAppName("HelloRDD").setMaster("local[3]")
    val sparkContext = new SparkContext(sparkAppConfig)

    val linesRDD = sparkContext.textFile(CommonUtils.getInputFilePath("survey_sample.csv"))
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
