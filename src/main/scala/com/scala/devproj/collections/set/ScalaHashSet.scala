package com.scala.devproj.collections

import scala.collection.immutable.HashSet

/**
 * HashSet is sealed class. It extends immutable Set and AbstractSet trait.
 * Hash code is used to store elements. It neither sorts the elements nor maintains insertion order .
 * The Set interface implemented by the HashSet class, backed by a hash table .
 * In Scala, A concrete implementation of Set semantics is known HashSet.
 *
 */

object ScalaHashSet {

  def main(args: Array[String]): Unit = {
    var hashset = HashSet(4, 2, 8, 0, 6, 3, 45)
    hashset.foreach(x=>println(x))

    //Initializing an empty HashSet :
    var emptyHasSet : HashSet[String] = HashSet.empty[String]
    println(s"Empty HashSet = $emptyHasSet")
  }

}
