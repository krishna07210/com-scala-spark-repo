package com.spark.devproj.sparkRDD.setOperations

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * "in/nasa_19950701.tsv" file contains 10000 log lines from one of NASA's apache server
 * for July 1st, 1995.
 * "in/nasa_19950801.tsv" file contains 10000 log lines for August 1st, 1995
 * Create a Spark program to generate a new RDD which contains the hosts which are accessed
 * on BOTH days. Save the resulting RDD to "out/nasa_logs_same_hosts.csv" file.
 *
 * Example output:
 *        vagrant.vf.mmc.com
 * www-a1.proxy.aol.com
 * .....
 *
 * Keep in mind, that the original log files contains the following header lines.
 * host	logname	time	method	url	response	bytes
 *
 * Make sure the head lines are removed in the resulting RDD.
 */

object SameHostsProblem {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("SameHostsProblem"))
    val julyLogs: RDD[String] = sparkContext
      .textFile(CommonUtils.getInputFilePath("nasa_19950701.tsv"))
      .map(line => line.split("\t")(0))
    val augustLogs: RDD[String] = sparkContext
      .textFile(CommonUtils.getInputFilePath("nasa_19950801.tsv"))
      .map(line => line.split("\t")(0))
    val commonHosts = julyLogs.intersection(augustLogs).filter(host=>host!="host")

    commonHosts.foreach(println)
    sparkContext.stop()
  }
}
