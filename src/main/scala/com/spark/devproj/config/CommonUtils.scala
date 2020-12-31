package com.spark.devproj.config

import java.io.File
import scala.reflect.io.Directory
import com.spark.devproj.constants.Constants
import org.apache.spark.rdd.RDD


/**
 * Created by krish on 12-04-2020.
 * Object to return the File path
 */
object CommonUtils {
  // a regular expression which matches commas but not commas within double quotations
  val COMMA_DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"

  def getExamples2InputFile(fileName: String): String = {
    val filePath: String = Constants.example2Source + fileName
    return filePath
  }
  def inputFile(fileName: String): String = {
    val filePath: String = Constants.inputSource + fileName
    return filePath
  }

  def outputFile(fileName: String): String = {
    val filePath: String = Constants.outputSource + fileName
    val directory = new Directory(new File(filePath))
    directory.deleteRecursively()
    return filePath
  }

  def getExamples2OutputFile(fileName: String): String = {
    val filePath: String = Constants.example2Destination + fileName
    val directory = new Directory(new File(filePath))
    directory.deleteRecursively()
    return filePath
  }
}
