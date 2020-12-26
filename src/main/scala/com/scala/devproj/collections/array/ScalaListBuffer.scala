package com.scala.devproj.collections.array

import scala.collection.mutable.ListBuffer

object ScalaListBuffer {
  def main(args: Array[String]): Unit = {
    var listBuff = ListBuffer[String]()
    listBuff.append("Hari","Krishna","Likith")

    listBuff.foreach(x=>println(x))


  }
}
