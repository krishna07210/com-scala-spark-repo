package com.scala.devproj.miscellaneous

object SplitFunction {

  def main(args: Array[String]): Unit = {
    val line = "A,B,\"-1\",C,D"
    val newLine = line.split(",")(2).replaceAll("\"","")
    println(newLine)
  }

}

