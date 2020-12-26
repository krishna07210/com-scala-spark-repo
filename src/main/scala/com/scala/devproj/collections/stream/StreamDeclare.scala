package com.scala.devproj.collections.stream

object StreamDeclare {

  def main(args: Array[String]): Unit = {
    val stream = 1 #:: 2#:: 8#:: Stream.empty
    println(stream)
    /** output: Stream(1, ?) : In the above output, we can see that second
     *  element is not evaluated.
     *  The tail is not printed, because it hasn’t been computed yet.
     *  Streams are specified to lazy computation.
     */

    val stream2 = (1 to 100).toStream
    println(stream2)
    stream2.take(3).foreach(println)

  }
}
