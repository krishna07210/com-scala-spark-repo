package com.scala.devproj.collections.stream

object StreamMethods {
  def main(args: Array[String]): Unit = {
    val m1 = Stream(3,6,7,4)
    val res1 = m1.head
    println(res1)

    val m2 = Stream(1, 2, 3, 4, 5, 7)
    val res2 = m2.takeRight(2)
    println(res2)
    println(":: takeWhile ::")
    val res3 = m2.takeWhile(_%2 ==1)
    println(res3)
    res3.foreach(println)
    println("takeWhile() stop evaluation immediately after condition is not met.")
    println(":: Filter ::")
    val res4 = m2.filter(_%2 ==1)
    res4.foreach(println)
    println("filter have to evaluate the whole Stream.")
  }
}
