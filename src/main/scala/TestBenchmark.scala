import com.wordnik.util.perf._
import com.wordnik.util.ProgressUtil

import com.mongodb._

object TestBenchmark extends BaseBenchmark {
  def main(args: Array[String]) = {
    parseArgs(args)

    config("testType") match {
      case "sequential" => runSequential(config("limit").toInt)
      case "random" => runRandom(config("limit").toInt)
    }
    
    ProfileScreenPrinter.dump
  }
  
  override def deserialize(obj: DBObject) = {}
}