package com.spark.devproj.sparkRDD.dataAnalysis.examples1

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator

import scala.collection.mutable.ArrayBuffer

/**
 * Author   : Krish - htharibo
 * Date     : 4/23/2020
 * Details  : Degree of Separation from one Node to another using Breadth First Search Algorithm
 *
 */

object DegreeOfSepration {

  // Some custom data types
  type BFSData = (Array[Int], Int, String) //BFSData contains an array of hero ID connections, the distance, and color.
  type BFSNode = (Int, BFSData) // A BFSNode has a heroID and the BFSData associated with it.
  val startCharacterID: Int = 5306 //SpiderMan
  val targetCharacterID: Int = 14 //ADAM 3,031 (who?)
  val grayColor = "GRAY"
  val whiteColor = "WHITE"
  val blackColor = "BLACK"
  var hitCounter: Option[LongAccumulator] = None //We make our accumulator a "global" Option so we can reference it in a mapper later.

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = SparkConfigs.localConfig("local", "DegreeOfSepration")
    hitCounter = Some(sc.longAccumulator("Hit Counter"))
    var iterationRDD = createRDDinBFSFormat(sc)
    //iterationRDD.foreach(println)
    var iteration: Int = 0
    for (iteration <- 1 to 10) {
      println("Running BFS Iteration# " + iteration)

      // Create new vertices as needed to darken or reduce distances in the
      // reduce stage. If we encounter the node we're looking for as a GRAY
      // node, increment our accumulator to signal that we're done.
      val mapped = iterationRDD.flatMap(bfsMap)

      // Note that mapped.count() action here forces the RDD to be evaluated, and
      // that's the only reason our accumulator is actually updated.
      println("Processing " + mapped.count() + " values.")

      if (hitCounter.isDefined) {
        val hitCount = hitCounter.get.value
        if (hitCount > 0) {
          println("Hit the target character! From " + hitCount +
            " different direction(s).")
          return
        }
      }

      // Reducer combines data for each character ID, preserving the darkest
      // color and shortest path.
      iterationRDD = mapped.reduceByKey(bfsReduce)
    }

  }

  def createRDDinBFSFormat(sc: SparkContext): RDD[BFSNode] = {
    val inputfile = sc.textFile(CommonUtils.getInputFilePath("Marvel-graph.txt"))
    return inputfile.map(convertLineToBFS)
  }

  def convertLineToBFS(line: String): BFSNode = {
    val fields = line.split("\\s+")
    val heroID = fields(0).toInt
    val connections: ArrayBuffer[Int] = ArrayBuffer()
    for (connection <- 1 to fields.length - 1) {
      connections += fields(connection).toInt
    }
    val color: String = if (heroID == startCharacterID) grayColor else whiteColor //Default color
    val distance: Int = if (heroID == startCharacterID) 0 else 9999
    return (heroID, (connections.toArray, distance, color))
  }

  def bfsMap(node: BFSNode): Array[BFSNode] = {

    val characterId = node._1
    val data: BFSData = node._2

    val connections = data._1
    val distance = data._2
    var color = data._3

    var results: ArrayBuffer[BFSNode] = ArrayBuffer()
    if (color == grayColor) { // Gray nodes are flagged for expansion, and create new gray nodes for each connection
      for (connection <- connections) {
        val newCharacterID = connection
        val newdistance = distance + 1
        val newColor = grayColor
        if (targetCharacterID == newCharacterID) if (hitCounter.isDefined) hitCounter.get.add(1)
        val newEntry: BFSNode = (newCharacterID, (Array(), newdistance, newColor))
        results += newEntry
      }
      color = blackColor
    }
    val thisEntry: BFSNode = (characterId, (connections, distance, color))
    results += thisEntry
    return results.toArray
  }

  /** Combine nodes for the same heroID, preserving the shortest length and darkest color. */
  def bfsReduce(data1: BFSData, data2: BFSData): BFSData = {

    // Extract data that we are combining
    val edges1: Array[Int] = data1._1
    val edges2: Array[Int] = data2._1
    val distance1: Int = data1._2
    val distance2: Int = data2._2
    val color1: String = data1._3
    val color2: String = data2._3

    // Default node values
    var distance: Int = 9999
    var color: String = whiteColor
    var edges: ArrayBuffer[Int] = ArrayBuffer()

    // See if one is the original node with its connections. If so preserve them.
    if (edges1.length > 0) {
      edges ++= edges1
    }
    if (edges2.length > 0) {
      edges ++= edges2
    }

    // Preserve minimum distance
    if (distance1 < distance) {
      distance = distance1
    }
    if (distance2 < distance) {
      distance = distance2
    }

    // Preserve darkest color
    if (color1 == whiteColor && (color2 == grayColor || color2 == blackColor)) color = color2
    if (color1 == grayColor && color2 == blackColor) color = color2
    if (color2 == whiteColor && (color1 == grayColor || color1 == blackColor)) color = color1
    if (color2 == grayColor && color1 == blackColor) color = color1
    if (color1 == grayColor && color2 == grayColor) color = color1
    if (color1 == blackColor && color2 == blackColor) color = color1

    return (edges.toArray, distance, color)
  }


}
