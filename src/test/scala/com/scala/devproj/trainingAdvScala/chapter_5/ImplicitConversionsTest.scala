package com.scala.devproj.trainingAdvScala.chapter_5

import org.scalatest.FunSuite

class ImplicitConversionsTest extends FunSuite {

  import scala.language.implicitConversions

  case class TaxRate(rate: BigDecimal)

  implicit var sales_tax: TaxRate = TaxRate(0.075)

  def withTax(price: BigDecimal)(implicit tax: TaxRate): BigDecimal = price * (tax.rate + 1)

  test(testName = "should use implicit conversion") {
    //    val res = withTax(15.00) //implicit TaxRate used
    val res = withTax(15.00)(TaxRate(0.075))
    println(res)

    assert(res == 16.1250)

    def withTax2(price: BigDecimal)(tax: TaxRate =TaxRate(2)): BigDecimal = price * (tax.rate + 1)

    val res2 = withTax2(15.00)(TaxRate(0.075))
   // val res3 = withTax2(15.00) //Gives compiler error since TaxRate is not implicit is passed
    println(res2)

  }
}
