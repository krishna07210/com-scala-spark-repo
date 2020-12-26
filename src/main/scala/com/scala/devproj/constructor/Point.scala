package com.scala.devproj.constructor

class Point (val xc: Int  , val yc :Int) {
  var x :Int = xc
  var y :Int = yc

  def move (dx :Int, dy:Int): Unit ={
    x = x +dx
    y = y +dy
    println("Point x Location : "+x)
    println("Point y Location : "+y)
  }
}

object  PointObject {
  def main (args : Array[String]): Unit ={
    val pt = new Point(10,20)
    pt.move(10,10)
  }
}