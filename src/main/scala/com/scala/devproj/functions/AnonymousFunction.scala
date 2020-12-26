package com.scala.devproj.functions

object AnonymousFunction {

  def namedfunc(c:Int)  = {println(c)}

  def getOps (c:Int) = (i : Int ) =>{
    val double = (x:Int) => x*2
    val triple = (x:Int) => x*3
    if (c <0) double(i)  else triple(i)
  }

  def main (args : Array[String]){
       getOps(2)
     getOps(-1)
    namedfunc(2)
  }
}
