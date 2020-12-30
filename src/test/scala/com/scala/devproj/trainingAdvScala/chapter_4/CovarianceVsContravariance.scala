package com.scala.devproj.trainingAdvScala.chapter_4

import org.scalatest.FunSuite
class CovarianceVsContravariance extends FunSuite {

  sealed abstract class Vehicle
  case class Car() extends Vehicle
  case class Motorcycle() extends Vehicle

  case class Parking[A](valu:A)
  case class PakringCovariance[+A](value:A)
  case class PakringContravariance[-A]()

  test(testName = "invariance"){
   // val p1 : Parking[Vehicle] = Parking[Car](new Car) // compile error
  }

  test(testName = "covariance"){
    val p1:PakringCovariance[Vehicle] = PakringCovariance[Car](new Car)
    //Car is a Vehicle so it can be assigned because +A
  }

  test(testName = "contravariance"){
    val p1 :PakringContravariance[Car] = PakringContravariance[Vehicle]()
    //Vehicel can be assigned to Car because of -A
  }
}
