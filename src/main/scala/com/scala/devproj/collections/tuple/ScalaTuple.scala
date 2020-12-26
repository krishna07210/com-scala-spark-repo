package com.scala.devproj.collections

object ScalaTuple {
  def defTouple = {
    ("Hari", "Krishna", "Likith")
  }

  def main(args: Array[String]): Unit = {
    var touple = defTouple
    println(touple._1)
    println(touple._2)
    println(touple._3)
    var touple2 = (1,2,3,4)
    println(touple2._3)
  }
}
