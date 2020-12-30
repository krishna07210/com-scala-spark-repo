package com.scala.devproj.trainingAdvScala.chapter_2

import org.scalatest.FunSuite

class FunctionCallByNameVsValue extends FunSuite {

  def funWithSideEffect(): Int = {
    println("calling something")
    1 //return value
  }

  def callByValue(x: Int) = {
    println("x1=" + x)
    println("x2=" + x)
  }

  def callByName(x: => Int): Unit = {
    println("x1=" + x) // x is elvaluated at this movement
    println("x2=" + x) // x is elvaluated at this movement
  }

  test(testName = "call by value") {
    callByValue(funWithSideEffect())
  }

  //If you want to defer the execution of the funtion use callbyName
  test(testName = "call by Name") {
    callByName(funWithSideEffect())
  }
}
