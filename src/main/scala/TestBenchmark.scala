import com.wordnik.util.perf.Profile
import com.wordnik.util.ProgressUtil

import com.mongodb._

object TestBenchmark extends BaseBenchmark {
  def main(args: Array[String]) = {
    parseArgs(args)

    config("testType") match {
      case "sequential" => runSequential(config("limit").toInt)
      case "random" => runRandom(config("limit").toInt)
    }
  }
  
  override def deserialize(obj: DBObject) = {}
}