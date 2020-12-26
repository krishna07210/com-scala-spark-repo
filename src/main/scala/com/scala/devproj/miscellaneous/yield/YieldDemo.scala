package com.scala.devproj.miscellaneous.`yield`

object YieldDemo {

  def main(args: Array[String]) {
    val a = Array(8, 3, 1, 6, 4, 5)
    val print = for (e <- a if e < 4) yield e
    for(i<-print) println(i)
  }

}
