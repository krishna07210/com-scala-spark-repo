package com.scala.devproj.trainingAdvScala.chapter_5

import javafx.scene.chart.PieChart.Data
import org.scalatest.FunSuite

class ImplicitParametersTest extends FunSuite {

  case class Data(str: String)

  class Transaction {
    def rollback(): Unit = println("rollback")

    def Data(str: String): String = "This is Data"

    def commit(): Unit = println("commit")
  }

  def withTransaction(f: Transaction => Unit): Unit = {
    val trx = new Transaction
    try {
      f(trx)
      trx.commit()
    } catch {
      case ex: Throwable => trx.rollback
        throw ex
    }
  }

  def op1(data: Any)(implicit transaction: Transaction): Unit = {
    println("op1")
  }

  def op2(data: Any)(implicit transaction: Transaction): Unit = {
    println("op2")
  }

  def op3(data: Any)(implicit transaction: Transaction): Unit = {
    println("op3")
  }

  test(testName = "should use implicit parameter") {
    val data = Data("a")
    //without implicit parameter
    withTransaction { trx =>
      op2(data)(trx)
      op1(data)(trx)
      op3(data)(trx)
    }
    withTransaction { implicit trx =>
      op2(data)
      op1(data)
      op3(data)
    }
  }
}
