package com.spark.devproj.sparkRDD.setOperations

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

/**
 * "in/nasa_19950701.tsv" file contains 10000 log lines from one of NASA's apache server for July 1st, 1995.
 * "in/nasa_19950801.tsv" file contains 10000 log lines for August 1st, 1995
 * Create a Spark program to generate a new RDD which contains the log lines from
 * both July 1st and August 1st,
 * take a 0.1 sample of those log lines and save it to "out/sample_nasa_logs.tsv" file.
 *
 * Keep in mind, that the original log files contains the following header lines.
 * host	logname	time	method	url	response	bytes
 *
 * Make sure the head lines are removed in the resulting RDD.
 */
object UnionLogProblem {

  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("UnionLogProblem"))
    val julyLogs = sparkContext.textFile(CommonUtils.getInputFilePath("nasa_19950701.tsv"))
    val augustLogs = sparkContext.textFile(CommonUtils.getInputFilePath("nasa_19950801.tsv"))
    val aggregatedLogs = julyLogs.union(augustLogs).filter(line => isNotHeader(line))
    aggregatedLogs.sample(withReplacement = true, fraction = 0.1)
      .foreach(println)

    sparkContext.stop()
  }

  def isNotHeader(line: String): Boolean = !(line.startsWith("host") && line.contains("bytes"))
}
