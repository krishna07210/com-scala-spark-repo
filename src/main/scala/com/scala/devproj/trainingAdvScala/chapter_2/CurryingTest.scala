package com.scala.devproj.trainingAdvScala.chapter_2

import org.scalatest.FunSuite

class CurryingTest extends FunSuite {

  def add(a: Int)(b: Int): Int = a + b

  //The function add a return b and b returns a=b

  test(testName = "should use currying") {
    val addOneTo = add(1) _
    val onePlusTen = addOneTo(10)
    println(onePlusTen)
    val onePlusTwo = addOneTo(2)
    println(onePlusTwo)
    assert(onePlusTen == 11)
    assert(onePlusTwo == 3)
  }


  def funcWithNamedParametersDefault(req1: Int, optional2: String = "default value"): String =
    s"req1 :$req1, optional2: $optional2"

  test(testName = "should invoke funtion with all params") {
    val res = funcWithNamedParametersDefault(req1 = 1, optional2 = "this")
    assert(res == "req1 :1, optional2: this")
  }

  test(testName = "should invoke funtion with default param") {
    val res = funcWithNamedParametersDefault(req1 = 1)
    assert(res == "req1 :1, optional2: default value")
  }
}
