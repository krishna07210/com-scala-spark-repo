package com.scala.devproj.trainingAdvScala.chapter_4

object StreamTest {

  def main (args : Array[String]): Unit ={
    val stream = 100 #:: 200 #:: 85 #:: Stream.empty
    println(stream)
    var stream2 = (1 to 10).toStream
    println(stream2)
    var firstElement = stream2.head
    println(firstElement)
    println(stream2.take(10))
    println(stream.map{_*2})
    val x = Stream(1,2,3,4)
    val x1 = 10 #:: 200 #:: Stream.empty
    println(x)
    println(x1)
    x1.foreach(println)
    stream.foreach(println)
  }
}
