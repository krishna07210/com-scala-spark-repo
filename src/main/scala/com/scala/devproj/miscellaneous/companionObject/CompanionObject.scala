package com.scala.devproj.miscellaneous.companionObject

/**
 * A companion object in Scala is an object that’s declared in the same file as a class,
 * and has the same name as the class
 */

class Person {
  println("This Companion Class using -" + Person.message)
  var name1 = ""
  var name: Option[String] = None
  var age: Option[Int] = None

  override def toString() = s"$name,$age"
}

object Person {
  private val message: String = "Companion Object private message Field"

  // a one-arg constructor
  def apply(name: Option[String]): Person = {
    var p = new Person()
    p.name = name
    p
  }
  // a two-arg constructor
  def apply(name: Option[String], age: Option[Int]): Person = {
    var p = new Person()
    p.name = name
    p.age = age
    p
  }
}


object CompanionObject {
  def main(args: Array[String]): Unit = {
    val compTest = Person(Some("Hari"))
    val p1 = Person(None)
    /* during the compilation process the compiler turns that code into this code:
       val p1 = Person.apply("Krishna")
    */
    val p3 = Person(Some("Wilma"), Some(33))
    val p4 = Person(Some("Wilma"), None)
  }
}
