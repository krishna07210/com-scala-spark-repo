package com.spark.devproj.config

/**
 * Created by krish on 06-04-2020.
 */

import java.util.Properties

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object SparkConfigs {

  val config = new SparkConf()

  def localConfig(hostMaster: String, appName: String): SparkContext = {
    config.setMaster(hostMaster)
    config.setAppName(appName)
    val sc = new SparkContext(config)
    return sc
  }

  def getLocalSparkConf(appName: String): SparkConf = {
    val conf: SparkConf = new SparkConf()
    val props = new Properties
    props.put("spark.master", "local[3]")
    props.put("spark.app.name", appName)
    props.forEach((k, v) => conf.set(k.toString, v.toString))
    return conf
  }

}
