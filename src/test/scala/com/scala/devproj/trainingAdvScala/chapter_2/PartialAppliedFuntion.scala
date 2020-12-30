package com.scala.devproj.trainingAdvScala.chapter_2

import org.scalatest.FunSuite

class PartialAppliedFuntion extends FunSuite {
  // Partial Functions & Partially Applied Functions

  def isInRange(left: Int, n: Int, right: Int): Boolean = {
    if (left < n && n < right) true else false
  }

  test(testName = "create partial applied funtion") {
    def is5InRange: (Int, Int) => Boolean = isInRange(_: Int, 5, _: Int)

    val res = is5InRange(0, 8)
    val res2 = is5InRange(6, 8)
    assert(res)
    assert(!res2)
  }
  test(testName = "create partial applied function and reuse multiple time") {
    def between0and10: Int => Boolean = isInRange(left = 0, _: Int, right = 10)

    assert(between0and10(5))
    assert(!between0and10(100))
  }
}
