package com.scala.devproj.funsuite

import org.scalatest.FunSuite


class ValuesTest extends FunSuite {

  test(testName = "Should not allow to modify val") {
    val a = "something"
    //    a = "other"  // compiler error
    assert(a.equals("something"))
  }
  test(testName = "should allow to modify var") {
    var a = "something"
    a = "other"
    assert(a.equals("other"))
  }

  test(testName = "should defer init of lazy"){
    lazy val a = {println("init") ; 10}
    //when
    println("not initialized yet")
    //then
    assert(a.equals(10)) // init will happen before it
  }

  test(testName = "should use def for funtion"){
    def add (a:Int,b:Int) :Int = a+b
    //then
    assert(add(1,2).equals(3))
  }
}
