package com.scala.devproj.traits

trait Printable {
  var a :Int = 10
  def print()
}

trait Showable {
  def show()
}

trait Display{
  def display()
}

class MultiTrait extends Printable with Showable with Display {

  def print(): Unit = {
    println("Hello Printable Trait")
  }
  def show(): Unit = {
    println("Hello Showable Trait")
  }
  def display: Unit = {
    println("Hello Display Trait")
  }
}

object MainObject {
  def main(args: Array[String]): Unit = {
    val p = new MultiTrait()
    p.print()
    p.show()
    println(p.a)
    p.display()
  }
}