package com.scala.devproj.overloading

object Welcome {

  //  def welcome(message: String = "Hello", name: String = "default"): String = message + "  " + name
  def welcome(message: String = "Hello", name: String = "default"): String = return message + "  " + name

  println(welcome())
  println(welcome("Hi", "Hari"))
  println(welcome(name = "Hari"))

}
