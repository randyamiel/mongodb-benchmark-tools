import com.wordnik.util.perf._
import com.wordnik.util.ProgressUtil

import com.mongodb._

import java.io.FileWriter

object TestBenchmark extends BaseBenchmark {
  def main(args: Array[String]) = {
    parseArgs(args)

    config("testType") match {
      case "sequential" => runSequential(config("limit").toInt)
      case "random" => runRandom(config("limit").toInt)
      case "update" => runUpdate(config("limit").toInt)
    }

    val str = ProfileScreenPrinter.toString
    if (config.contains("outputFile")) {
      // append to file 
      val fw = new FileWriter(config("outputFile"), true)
      fw.write(str + "\n")
      fw.close()
    }
  }

  override def deserialize(obj: DBObject) = {}
}