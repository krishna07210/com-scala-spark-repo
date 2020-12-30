package com.scala.devproj.trainingAdvScala.chapter_4

import org.scalatest.FunSuite
class LazyStreamTest extends  FunSuite {

  test(testName = "should create lazy Stream") {
  val stream = (1 to 1000000000).toStream
  val res = stream.take(3)
   res.foreach(println)
    assert(res.toList == List(1,2,3))
}

  def sumUp (s : Seq[Int]) : Int = {s.sum}
test(testName = "List Vs Seq") {
  assert(sumUp(List(1,2,3)) == 6)
  assert(sumUp(Vector(1,2,3))==6)
  assert(sumUp(Seq(1,2,3))==6)
}
}
