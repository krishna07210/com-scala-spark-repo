package com.scala.devproj.trainingAdvScala.chapter_6

import org.scalatest.FunSuite

class ExistentialTypes extends FunSuite {

  sealed trait Existential {
    type Inner
    val value: Inner
  }

  final case class PrepareExistential[A](value: A) extends Existential {
    type Inner = A
  }

  //forSome is for existential types

  def printLength(x: Array[T] forSome {type T <: CharSequence}): Unit =
    x.foreach(y => println(y.length))

  test(testName = "should user existential") {
    PrepareExistential("SomeText"): Existential
    PrepareExistential(1: Int): Existential
  }

  test(testName = "should use existential scala syntax") {
    printLength(Array("a", "bb"))
  }
}
