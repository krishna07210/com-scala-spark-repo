package com.scala.devproj.learnModule1

/**
  * Created by krish on 04-04-2020.
  */
object LearningScala0 {

  def main(args: Array[String]): Unit = {
    val hello: String = "Hari"
    //hello = hello + "Krishna"   -- IMMUTABLE
    println(hello)

    var hellov: String = "Hari"
    hellov = hellov + "Krishna"
    println(hellov)

    val portal = "GeeksforGeeks".r
    val CS = "GeeksforGeeks is a CS portal."

    println(portal findFirstIn CS)

    val isGreaterThen = 5 > 2
    val isLessThen = 3 < 2
    val expression = isGreaterThen && isLessThen
    println(isGreaterThen)
    println(isLessThen)
    println(expression)

    val pival: Double = 3.14
    println(pival)
    println(f"Pi value $pival%.3f")

    val number = 5
    number match {
      case 1 => println("One")
      case 2 => println("Two")
      case 3 => println("Three")
      case _ => println("Something else")
    }

    val num1 = if (1 > 2) {
      "If"
    } else {
      "Else"
    }
    println(num1)

    for (x <- 1 to 4) {
      val square = x * x
      println(square)
    }

    println({
      val x = 10;
      x + 20
    })

    println("---------------------------------")

    def square(x: Int): Int = {
      x * x
    }

    println("Square - " + square(3))

    def transformInt(x: Int, f: Int => Int): Int = {
      f(x)
    }

    println(transformInt(4, square))
    println(transformInt(2, x => x * 2))

    //tuple

    val captainStuff = ("Hari", "Krishna", "Likith")

    println(captainStuff._1)
    println(captainStuff._2)
    println(captainStuff._3)

    val captainStuffMixed = ("Hari", "Krishna", "Likith", 1234)

    println(captainStuffMixed._4)

    val mapInput = "Name" -> "Hari"
    println(mapInput._1)

    val nameList = List("Hari", "Krishna", "Likith")

    println(nameList(0))
    println("Head - " + nameList.head)
    println("Tail - " + nameList.tail)
    println("Tail - Head - " + nameList.tail.head)
    println("Tail - Head - Tail " + nameList.tail.head.tail)

    for (name <- nameList) {
      println(name)
    }

    val bakwardNames = nameList.map((name: String) => {
      name.reverse
    })
    for (name <- bakwardNames) {
      println(name)
    }

    val numberList = List(1, 2, 3, 4, 5)
    val sum = numberList.reduce((x: Int, y: Int) => x + y)
    println(sum)

    val iHateFive = numberList.filter((x: Int) => x != 5)
    println(iHateFive)
    val iHateThree = numberList.filter(_ != 3)
    println(iHateThree)

    val moreNumbers = List(6, 7, 8, 9, 5)
    val lotsOfNumbers = numberList ++ moreNumbers
    println(lotsOfNumbers)

    println("reversed" + numberList.reverse)
    println("sorted" + numberList.sorted)
    println("distinct " + lotsOfNumbers.distinct)
    println("has Three - " + numberList.contains(3))

    val namesMap = Map("Name" -> "Hari", "Age" -> "32", "Address" -> "HYD")
    println(namesMap("Name"))

    val archership = util.Try(namesMap("Archer")) getOrElse "Unknown"
    println(archership)
  }


}
