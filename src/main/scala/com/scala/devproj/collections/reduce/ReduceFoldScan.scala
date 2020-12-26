package com.scala.devproj.collections

object ReduceFoldScan {
  def main(args: Array[String]): Unit = {
    var seq_elements :Seq[Int] = Seq(1,2,3,4,5)
    val reduce1 :Double = seq_elements.reduce((x,y)=>x+y)
    println(reduce1)
    val minRed = seq_elements.reduce(_ min _)
    println(minRed)

    var seq_element2 : List[Int] = List(1,2,3,4,5)
    val scan1 : Double= seq_element2.reduce((x,y) => (x+y))
    println(scan1)

    // initialize a sequence of elements
//    val seq_elements3: Seq[Double] = Seq(3.5, 5.0, 1.5)
    val seq_elements3: List[Double] = List(3.5, 5.0, 1.5)
    println(s"Elements = $seq_elements3")
    val sum : Double = seq_elements3.fold(0.0)((x,y)=>x+y)
    println(sum)

    val seq_elements4: Seq[Double] = Seq(3.5, 5.0, 1.5)
    val iterations :Seq[Double] = seq_elements4.scan(0.0)((x,y)=>(x+y))
println(iterations)
  }
}
