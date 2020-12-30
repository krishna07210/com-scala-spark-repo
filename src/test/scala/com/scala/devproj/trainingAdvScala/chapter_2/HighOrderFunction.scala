package com.scala.devproj.trainingAdvScala.chapter_2

import org.scalatest.FunSuite

class HighOrderFunction extends FunSuite {
  def operationUsingProvider(v1: Int, provider: Int => String): String =
    provider(v1)

  def operationReturningFunction(v1: Long): (Int, Int) => Long = (a, b) => v1 + a + b

  test(testName = "Should user Function that takes Funtion as a Parameter") {
    val res = operationUsingProvider(
      v1 = 100, v => v.toString.substring(0, 1))
    assert(res == "1")
  }

  test(testName = "should return function") {
    val f = operationReturningFunction(v1 = 100)
    val res = f(1, 2)
    assert(res == 103L)
  }
}
