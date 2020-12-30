package com.scala.devproj.trainingAdvScala.chapter_7

import java.util.concurrent.Executors

import org.scalatest.FunSuite

import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

class FuturesTestCustomThreadPool extends FunSuite {

  def blockingAction(): String = {
    Thread.sleep(1000)
    "result"
  }

  test(testName = "should handle success/failure of future") {
    implicit val ec: ExecutionContextExecutor =
      ExecutionContext.fromExecutor(Executors.newFixedThreadPool(4))
  }
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
