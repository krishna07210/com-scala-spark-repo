package com.scala.devproj.trainingAdvScala.chapter_4
import org.scalatest.FunSuite
class MonadicOperationsList extends FunSuite  {
test(testName = "should map"){
  val list = List(1,2,3)
  val res =list.map(element => element *10)
  assert(res ==List(10,20,30))
}

  test(testName = "should FlatMap"){
    val list : List[List[Int]] = List(List(1,1,1),List(2,2,2),List(3,3,3))
val res = list.flatMap(identity)
  assert(res == List(1,1,1,2,2,2,3,3,3))
  }

  test(testName = "should fold"){
    val list : List[Int] = List(1,2,3)
    val res =list.fold(0){ //starts from 0
      (accumulator,value) => accumulator+value
    }
    assert(res==6)
    val res2 =list.fold(2){ //starts from 0
          //0,1 - 1
          //1,2 - 3
          //3,3 - 6
      (accumulator,value) => accumulator+value
    }
    println(res2)
  }

  test(testName = "should reduce"){
    val list : List[Int] = List(1,2,3,4)
    //1,2 - 2
    //2,3 - 3
    //3,4 - 4
    val res =list.reduce((x,y)=>x max y) //find max
    assert(res==4)
  }
}
