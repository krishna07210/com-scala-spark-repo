package com.scala.devproj.traits

trait Iterator[A] {
  def hasNext: Boolean

  def next(): A
}

class IntIterator(to: Int) extends Iterator[Int] {
  private var current = 0

  override def hasNext: Boolean = current < to

  override def next(): Int = {
    if (hasNext) {
      val t = current
      current += 1
      t
    } else {
      0
    }
  }
}

object Override {
  def main(args: Array[String]): Unit = {
    var iterator = new IntIterator(10)
    iterator.next()
    iterator.next()
  }
}