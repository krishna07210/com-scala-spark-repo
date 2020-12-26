package com.scala.devproj.trainingAdvScala.chapter_4
import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer
class CollectionsTest extends FunSuite {

  test(testName = "should add items to immutable list"){
    val list : List[String] = List()
    val newlist = list :+"first"
    assert(list==List()) //old list was not changed becuasue it is immutable
    assert(newlist==List("first"))
  }
  test(testName = "should add items to mutable list"){
    val list = new  ListBuffer[String]()
    list += "1"
    list +="2"
    list +="3"
    assert(list.toList == List("1","2","3"))
  }
  test(testName = "should create a immutable map") {
    val states  =Map("ALL"->"Alabama" ,"AK" -> "Alaska")
    for ((k,v)<-states) println("key : "+k+ " value : "+v)
    val b = states + ("NY" -> "New York")
    assert(states== Map("ALL"->"Alabama" ,"AK" -> "Alaska"))
    assert(b== Map("ALL"->"Alabama" ,"AK" -> "Alaska","NY" -> "New York"))
  }

  test(testName = "should create a mutable map"){
    val states = scala.collection.mutable.Map[String,String]()
    states+= ("ALL"->"Alabama")
    states+= ("CO"->"Colorado" , "NY" -> "New York")
    assert(states ==Map("ALL"->"Alabama","CO"->"Colorado" , "NY" -> "New York"))
    states-="CO"
    for((k,v)<-states)println("states key : "+k+ " value : "+v)
  }

}
