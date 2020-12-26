package com.scala.devproj.collections

import scala.collection.mutable.ArrayBuffer

trait Pet{
  val name :String
}

class Cat (val name :String) extends Pet
class Dog (val name :String) extends Pet

object ScalaArrayBuffer {

  def main(args: Array[String]): Unit = {

    var name = ArrayBuffer[String]()
    // adding one element
    name += "GeeksForGeeks"

    // add two or more elements
    name += ("gfg", "chandan")
    // adding one or more element using append method
    name.append("S-series", "Ritesh","Vamhsi")
    println(name)
    println(name(2))

    /**
     * The remove method is used to delete one element
     * by its position in the ArrayBuffer, or a series of elements beginning at a starting position.
     */
    name -= "GeeksForGeeks"
    println(name)

    name.remove(4)
    println(name)

    /*Subtyping */
    val dog  = new Dog("Harry")
    val cat = new Cat("Sally")
    val animals = ArrayBuffer.empty[Pet]
    animals.append(dog)
    animals.append(cat)
    animals.foreach(pet=> println(pet.name))

  }
}
