package com.scala.devproj.trainingAdvScala.chapter_3

import org.scalatest.FunSuite

class PatternMatchingTest extends FunSuite {

  test(testName = "Should use simple pattern matching") {
    val x: Int = 2
    val res = x match {
      case 0 => "Zero"
      case 1 => "One"
      case 2 => "Two"
      case _ => "other"
    }
    assert(res == "Two")
  }

  abstract class Message

  case class AMessage(text: String) extends Message

  case class BMessage(price: Int) extends Message

  def max(a: Int, b: Int): Int = if (a > b) a else b

  private def matchMessage(msg: Message): Any = msg match {
    case AMessage(text) => text.toUpperCase
    case BMessage(price) => price * 10
  }

  test(testName = "should use pattern matching with case class") {
    val a: Message = AMessage("some text")
    val b: Message = BMessage(100)
    val resA = matchMessage(a)
    val resB = matchMessage(b)
    assert(resA == "SOME TEXT")
    assert(resB == 1000)
  }
  test(testName = "should use scala conditional") {
    val res = max(1, 20)
    assert(res == 20)
  }
}
