package com.scala.devproj.trainingAdvScala.chapter_6

import org.scalatest.FunSuite

class HigherKindedTypes extends FunSuite {

  trait Monad[M[_]] { //[_] denote Higher order types
    def op[A, B](m: M[A], f: A => M[B]): M[B]

    def zero[A](a: A): M[A]
  }

  object ListMonad extends Monad[List] {
    def op[A, B](m: List[A], f: A => List[B]): List[B] = m.flatMap(f)

    def zero[A](a: A) = List[A](a)
  }

  test(testName = "should use higher kinded types") {
    val listMonad = ListMonad.zero(42)
    assert(listMonad == List(42))

    val result = ListMonad.op(listMonad, (i: Int) => List(i - 1, i, i + 1))
    assert(result == List(41, 42, 43))
  }

}

