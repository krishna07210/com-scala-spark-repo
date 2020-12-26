package com.scala.devproj.regex

object FindFirst {
  /*
  The findFirstIn method finds the first occurrence of the pattern.
  To find all the occurrences use finadAllIn() method.
   */

  def main(args: Array[String]): Unit = {
    val st = "Scala is Functional Programming Language ! also Functional Program is easy"
    val p = "Functional".r
    println(p findFirstIn st)
    println((p findFirstIn st).mkString(","))
    /*The mkString method concatenates the resulting set.
    Pipe (|) symbol can be used to specify the OR search condition. */
    println((p findAllIn st).mkString(","))
  }
}
