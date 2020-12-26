package com.spark.devproj.config

/**
 * Created by krish on 06-04-2020.
 */

import org.apache.spark.{SparkConf, SparkContext}

class SparkConfiguration {

  val config = new SparkConf()

  def localConfig(hostMaster: String, appName: String): SparkContext = {
    config.setMaster(hostMaster)
    config.setAppName(appName)
    val sc = new SparkContext(config)
    return sc
  }
}
