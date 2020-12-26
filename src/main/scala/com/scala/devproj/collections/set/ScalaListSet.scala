package com.scala.devproj.collections

import scala.collection.immutable.ListSet

object ScalaListSet {

  def main(args: Array[String]): Unit = {

    var listSet = ListSet.empty[Int]
    listSet += 10
    listSet += (20,30,40)
    listSet += 50
    listSet.foreach(println)
    var listSet2= new ListSet[String]
    listSet2 += ("Hari","krishna")
    listSet2.foreach(println)
  }
}
