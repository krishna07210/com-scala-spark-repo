package com.scala.devproj.traits

trait Print {
  def print()
}

abstract class PrintA4 {
  def printA4()
}
/**
Error:(11, 38) class PrintA4 needs to be a trait to be mixed in
class MixinsOrder extends Print with PrintA4 {

 Scala Trait Mixins
In scala, trait mixins means you can extend any number of traits with a class or abstract class. You can extend only traits or combination of traits and class or traits and abstract class.

It is necessary to maintain order of mixins otherwise compiler throws an error.
 */

//  class MixinsOrder extends Print with PrintA4 {
class MixinsOrder extends PrintA4 with Print{
  override def print() {
    println("Hello Print")
  }

  override def printA4(): Unit = {
    println("Hello Abstract Class")
  }
}

object MixinsOrderObject {
  def main(args: Array[String]): Unit = {
    val p = new MixinsOrder()
    p.print()
    p.printA4()
  }
}