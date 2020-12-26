package com.scala.devproj.collections.stream

import scala.collection.immutable.Stream.cons

object StreamCons {
  /** We can also create a Stream by using Stream.cons .
   * A package import scala.collection.immutable.Stream.cons is used for creating stream.
   */
  def main(args: Array[String]): Unit = {
    val stream1 : Stream[Int] = cons(1, Stream.empty)
    val stream2 : Stream[Int] = cons(1,cons(2,cons(3,Stream.empty)))
    val stream3 = 1 #:: 2 #:: 8 #:: Stream.empty
    println(stream1)
    println(s"Elements of stream2 = ${stream2}")
    stream1.take(2).print
    print("Take first 2 numbers from stream = ")
    stream2.take(2).print
    print("\nTake first 10 numbers from stream3 = ")
    stream3.take(10).print
  }

}
