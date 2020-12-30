package com.scala.devproj.trainingAdvScala.chapter_3

import org.scalatest.FunSuite
import scala.util.{Failure, Success, Try}

class IterationsAndExceptions extends FunSuite {

  test(testName = "Should Iterate over collection with foreach") {
    val list = List(1, 2, 3)
    list.foreach(println(_))
  }
  test(testName = "should iterate over collection with for-comprehension") {
    val list = List(1, 2, 3)
    for (v <- list) println(v)
  }

  def actionThatThrows(): Unit = throw new Exception("some message")

  test(testName = "should use simple try catch block") {
    try {
      actionThatThrows()
    } catch {
      case e: Exception => e.printStackTrace()
      case _: Throwable => println("other exception")
    }
  }
  test(testName = "should use Try in Functional style") {
    val action = Try(actionThatThrows())
    action match {
      case Success(_) => println("Success")
      case Failure(e) => println("Failed : " + e.getMessage)
    }

  }
}
