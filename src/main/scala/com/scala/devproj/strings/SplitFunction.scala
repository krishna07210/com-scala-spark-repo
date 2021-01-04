package com.scala.devproj.strings

object SplitFunction {
  def main(args: Array[String]): Unit = {
    val str: String = "468-567-7388"
    val str1: String = "A,B,C,D,E,F,G"
    val strArray1: Array[String] = str.split("8", 2)
    println("Number of substrings:"+ strArray1.length)
//    strArray1.foreach(println)
    val strArray2: Array[String] = str1.split(",", -1)
    strArray2.foreach(println)
  }
}
