package com.scala.devproj.traits

class ExtendTraitObjCreate extends PrintA4 {
  def print() { // Trait print
    println("print sheet")
  }

  def printA4() { // Abstract class printA4
    println("Print A4 Sheet")
  }
}

object ExtendTraitObjMain {
  def main(args: Array[String]): Unit = {
    val p = new ExtendTraitObjCreate() with Print
    p.print()
    p.printA4()
  }
}