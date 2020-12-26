package com.scala.devproj.collections

/**
 * Scala Set
 * It is used to store unique elements in the set. It does not maintain any order for storing elements.
 * You can apply various operations on them. It is defined in the Scala.collection.immutable package.
 */
object ScalaSet {
  def main(args: Array[String]): Unit = {
    val set1 = Set()
    val set2 = Set("Hari","Krishna","Vamshi")
    println(set1)
    println(set2)
    println(set2.foreach(println))
  }
}
