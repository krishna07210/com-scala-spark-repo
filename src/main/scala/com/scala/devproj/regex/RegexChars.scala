package com.scala.devproj.regex

import scala.util.matching.Regex

object RegexChars {
  def main(args: Array[String]): Unit = {

    val str :String = "Hello every one, this is Harikrishna Hope all are doing well"
    val p1 = new Regex("\\D")
    println(p1 findFirstIn   str)

  }
}
