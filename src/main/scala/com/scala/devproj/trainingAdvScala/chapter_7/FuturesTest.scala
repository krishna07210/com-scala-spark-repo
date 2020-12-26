package com.scala.devproj.trainingAdvScala.chapter_7

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

import org.scalatest.FunSuite

class FuturesTest extends FunSuite {

  def blockingAction(): String = {
    Thread.sleep(1000)
    "result"
  }

  test(testName = "should handle success/failure of future") {
    val f: Future[String] = Future {
      blockingAction()
    }

    f onComplete {
      case Success(value) => println(value)
      case Failure(t) => println("An error has occured : " + t.getMessage)
    }
    println("after")
    Thread.sleep(2000)
  }

  test(testName = "should use monadic async callback on future") {
    val f: Future[String] = Future {
      blockingAction()
    }.map(v => v.toUpperCase())

    assert(Await.ready(f, Duration.Inf).value.get.get == "RESULT")
    //Inf - Infinite Time waiting
  }
}
