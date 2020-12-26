package com.scala.devproj.regex

object replaceFirst {

  def main(args: Array[String]): Unit = {

    val str = "Hello Mr. Person how are you doing.. Bye Person"
    val p = "Person".r
    println(p replaceFirstIn(str,"Hari"))
    println(p replaceAllIn (str,"Hari"))
  }
}
