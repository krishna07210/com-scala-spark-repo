package com.scala.devproj.collections
import scala.collection.mutable.Map

object ScalaMap {

  def main(args: Array[String]): Unit = {
    val map0  = Map("K" -> 1,"L"->2)
    map0.foreach(x=>print(x._1,x._2))
    println("----")
    val map1 : Map[String, String] = Map("K" -> "Krishna","L"->"Likith")
    map1.foreach(x=>print(x._1,x._2))
    val map2 : Map[Int,String] = Map()
    map2(2) = "LTI" // If it's immutable will get error
    map2(3) = "IBM"
    map2.foreach(x=>print(x._1,x._2))
    println("----")
    println(map2.get(3).toString())
    map2.put(5,"LTF")
    map2.foreach(x=>print(x._1,x._2))
    map2.remove(2)
    println(map2)

    map2.empty

    println(map2)
  }
}
