package com.scala.devproj.traits

trait Printable1 {
  def print ()
}

class TraitSingle extends Printable1  {
  def print() { println("Hello")}
}

object MainObject1 {
  def main(args: Array[String]): Unit = {
    var a = new TraitSingle()
    a.print()
  }
}