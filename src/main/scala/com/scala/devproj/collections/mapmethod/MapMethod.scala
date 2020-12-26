package com.scala.devproj.collections

object MapMethod {
  def main(args: Array[String]): Unit = {

    def square(v: Int): Int = {
      v * v
    }

    val collection = List(1, 2, 3, 4, 5)
    val sqare1 = collection.map(x => x * x)
    println(sqare1)
    val sqare2 = collection.map(square)
    println(sqare2)

  }
}
