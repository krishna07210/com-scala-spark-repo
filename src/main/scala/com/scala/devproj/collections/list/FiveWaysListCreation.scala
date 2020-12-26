package com.scala.devproj.collections.list

object FiveWaysListCreation {

  def main(args: Array[String]): Unit = {
    println("Creation Type : 1")
    val list1 = 1 :: 2 :: 3 :: 4 :: Nil
    list1.foreach(println)
    println("Creation Type : 2")
    val list2: List[Int] = List(1, 2, 3, 4)
    for (elem <- list2) {
      println(elem)
    }

    println("Creation Type : 2")
    val list3 = List[Number](1, 2.0, 33d, 0x1)
    list3.foreach(println)
    println("Creation Range  : 3")
    val list4 = List.range(0, 12, 2)
    for (elem <- list4) {
      println(elem)
    }
    println("Creation Fill : 4")
    val list5 = List.fill(3)("foo")
    list5.foreach(println)

    println("Creation 5 :: tabulate ")

    /** The tabulate method creates a new List whose elements are
     * created according to the function you supply. The
     * book Programming in Scala shows how to create a List using a
     * simple "squares" function with the tabulate method:
     */
    val list5_1 = List.tabulate(5)(n=>n*n)
    list5_1.foreach(println)

  }
}
