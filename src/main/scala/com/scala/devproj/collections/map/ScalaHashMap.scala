package com.scala.devproj.collections

import scala.collection.mutable.HashMap

object ScalaHashMap {
def main (args: Array[String]): Unit ={
  var hashMap =  HashMap("H"->"Hari","L"->"Likith")
  var hashMap2 = hashMap + ("J" -> "Jeswitha")
  println(hashMap)
  hashMap2("K") = "Kittu"

  hashMap2.foreach(x=>println(x._1,x._2))
  //or
  hashMap2.foreach{
    case(key, value) => println(key +"->"+value)
  }
  hashMap2 -= "J"
  hashMap2.foreach(x=>println(x._1,x._2))
}

}
