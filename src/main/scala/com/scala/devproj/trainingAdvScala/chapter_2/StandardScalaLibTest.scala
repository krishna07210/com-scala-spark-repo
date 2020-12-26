package com.scala.devproj.trainingAdvScala.chapter_2

import org.scalatest.FunSuite

class StandardScalaLibTest extends FunSuite {

  test(testName = "should use option type when not present") {
    //given
    val result: Option[String] = Option.empty // use if instead of null but it will be null safe
    val result1: Option[String] = None //Is equal to above  Empty
    //when
    val stringResult = result.map(v => v.toUpperCase).getOrElse("default")
    //getOrElse  =>  NVL
    assert(stringResult == "default")
  }

  test(testName = "should use option type when present") {
    val result: Option[String] = Some("this is a value")
    println("Some is class of Option which is not empty")
    println(result)
    val stringResult = result.map(x => x.toUpperCase).getOrElse("default")
    println(stringResult)
    assert(stringResult == "THIS IS A VALUE")
  }

  test(testName = "should leverage tuple") {
    val a: (String, Int) = ("a-value", 10)
    val b: (String, Int, Int) = ("b-value", 10, 100)

    assert(a._1 == "a-value")
    assert(a._2 == 10)
    assert(b._1 == "b-value")
    assert(b._2 == 10)
    assert(b._3 == 100)

  }

  /**
   * ._1 , ._2 syntax of a tuple can be hard to maintain and misleading instead of using it
   *  better use case class
   */

  case class NamedTwoArgs(address: String, salary: Int)

  case class NanedThreeArgs(address: String, salary: Int, rate: Int)

  test(testName = "should use case class instead of a tuple") {
    val a = NamedTwoArgs("a-value", 10)
    val b = NanedThreeArgs("b-value", 10, 100)

    assert(a.address == "a-value")
    assert(a.salary == 10)
    assert(b.address == "b-value")
    assert(b.salary == 10)
    assert(b.rate == 100)
  }
}
