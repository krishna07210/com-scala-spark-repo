name := "com-scala-spark-repo"

version := "0.1"

scalaVersion := "2.11.1"


//%% asks Sbt to add the current scala version to the artifact
//You can spark-core_2.11 with % to get the issue solved.

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
//libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.6.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.2"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.2"

// https://mvnrepository.com/artifact/org.scalatest/scalatest-funsuite
libraryDependencies += "org.scalatest" %% "scalatest-funsuite" % "3.0.0-SNAP13"


artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "-" + module.revision + "." + artifact.extension
}