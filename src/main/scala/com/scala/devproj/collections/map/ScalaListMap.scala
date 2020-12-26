package com.scala.devproj.collections

import scala.collection.mutable

object ScalaListMap {

  def main (args: Array[String]): Unit ={
    var listMap =  mutable.ListMap("H"->"Hari","L"->"Likith")
    var listMap2 = listMap + ("J" -> "Jeswitha")
//    println(listMap)
    listMap2.foreach(x=>println(x._1,x._2))
    //or
    listMap2.foreach{
      case(key, value) => println(key +"->"+value)
    }
    listMap2 -= "J"
    listMap2.foreach(x=>println(x._1,x._2))
  }



}
