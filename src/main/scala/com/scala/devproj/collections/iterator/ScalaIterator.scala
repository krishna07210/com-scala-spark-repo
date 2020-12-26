package com.scala.devproj.collections

object ScalaIterator {

  def main(args: Array[String]): Unit = {

    var seq : Seq[Int] = Seq(3,4,5,6)
    val i = seq.iterator
    while (i.hasNext)  println(i.next() +" ")
    println("foreach")
    var seq2 : Seq[String] = Seq("Hari","krishna")
    val k = seq2.iterator
    k foreach println

    var j = Iterator(9,8,7,6,5)
    j foreach println

  }
}
