package com.scala.devproj.trainingAdvScala.chapter_7

import org.scalatest.FunSuite

class ParallelCollectionsTest extends FunSuite {
  test(testName = "Should use parallel collections from multiple threads leverage") {
    val res = List(1, 2, 3).par.filter {
      x => println(Thread.currentThread); x > 0
    }
    assert(res == List(1, 2, 3))
  }

  test(testName = "should use normal collections for low overhead operation") {
    val start = System.currentTimeMillis()
    val res = (1 to 1000000).filter { x => x > 500 }.toList
    assert(res.nonEmpty)
    println(s"normal (low overhead) collection time : ${System.currentTimeMillis() - start}")
  }

  test(testName = "should use parallel collections for big overhead operation") {
    val start = System.currentTimeMillis()
    val res = (1 to 1000000).par.filter { x => x > 500 }.toList
    assert(res.nonEmpty)
    println(s"parallel (big overhead) collection time : ${System.currentTimeMillis() - start}")

    /** normal (low overhead) collection time : 90
     * parallel (low overhead) collection time : 243
     * Note: Here the time taken for Parallel is higher because it take time to divide the task into
     * multiple CPUs and Threads
     */
  }
  test(testName = "should use parallel collections for big overhead operations") {
    val start = System.currentTimeMillis()
    val res = (1 to 1000000).par.filter {
      Thread.sleep(1000); x => x > 500
    }.toList
    assert(res.nonEmpty)
    println(s"parallel (big overhead) collection time : ${System.currentTimeMillis() - start}")

  }

}
