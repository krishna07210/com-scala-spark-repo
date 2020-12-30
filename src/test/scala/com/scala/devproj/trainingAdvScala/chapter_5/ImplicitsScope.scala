package com.scala.devproj.trainingAdvScala.chapter_5

import org.scalatest.FunSuite

class ImplicitsScope extends FunSuite {
  test(testName = "current scope") {
    implicit val n: Int = 5
//    implicit val n2: Int = 10  // ambiguous implicit values both n2 of type Int and val n of type Int
    implicit val n2 : Long =10
    def add(x: Int)(implicit y: Int): Int = x + y

    println(add (5)) //takes n from the current scope
  }

//  test(testName = "explicit import"){
//    import scala.collection.JavaConversions.mapAsScalaMap
//    def env : util.Map[String,String] = System.getenv()
//  }

  test(testName = "wildcard imports"){
    def sum[T : Integral](list: List[T]) : T = {
      val integral = implicitly[Integral[T]]
      import  integral._
      list.foldLeft(integral.zero)(_ + _)
    }
  }
}
