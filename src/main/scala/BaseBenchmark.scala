import com.mongodb._

import com.wordnik.util.ProgressUtil
import com.wordnik.util.perf.Profile

import org.eatbacon.util.RandomDataUtil

import scala.collection.mutable.HashMap
import scala.collection.JavaConverters._

abstract class BaseBenchmark {
  var DB_CONNECTION: DB = null
  val config = HashMap[String, String](
    "showAsCsv" -> "true",
    "collection" -> "test1",
    "databaseName" -> "test",
    "databaseHost" -> "localhost:27017",
    "limit" -> "10000",
    "testType" -> "sequential",
    "validateData" -> "false")

  def parseArgs(args: Array[String]) = {
    var i = 0
    while (i < args.length) {
      args(i) match {
        case "-C" => config += "showAsCsv" -> "true"
        case "-c" => i += 1; config += "collection" -> args(i)
        case "-d" => i += 1; config += "databaseName" -> args(i)
        case "-u" => i += 1; config += "username" -> args(i)
        case "-p" => i += 1; config += "password" -> args(i)
        case "-h" => i += 1; config += "databaseHost" -> args(i)
        case "-S" => i += 1; config += "totalRecords" -> args(i)
        case "-l" => i += 1; config += "limit" -> args(i)
        case "-t" => i += 1; config += "testType" -> args(i)
        case "-o" => i += 1; config += "outputFile" -> args(i)
        case "-v" => config += "validateData" -> "true"
        case a: String => println("invalid argument " + a); exit(0)
      }
      i += 1
    }
    config("validateData") match {
      case "true" => validateData(config("totalRecords").toInt)
      case _ =>
    }
    config.getOrElse("totalRecords", {
      getDb().getCollection(getCollectionName()).find().count() match {
        case c: Int => config += "totalRecords" -> c.toString
        case _ => config += "totalRecords" -> "0"
      }
      println("set totalRecords from DB to " + config("totalRecords"))
    })
  }

  def getCollectionName() = config("collection")

  def getDb(): DB = {
    if (DB_CONNECTION == null) {
      val m = new Mongo(config("databaseHost"))
      DB_CONNECTION = m.getDB(config("databaseName"))
      config.contains("username") match {
        case true => DB_CONNECTION.authenticate(config("username"), config.getOrElse("password", "").toCharArray)
        case false =>
      }
    }
    DB_CONNECTION
  }

  def deserialize(obj: DBObject)

  def runSequential(limit: Int, maxRecords: Int) = {
    Profile("sequential full-object", {
      val pu = new ProgressUtil
      var charsRead = 0
      val cur = getDb().getCollection(getCollectionName()).find()
      var count = 0
      var done = false
      while (cur.hasNext() && !done) {
        count += 1
        pu.update("sequential full objects", count, limit)
        val obj = cur.next()
        charsRead += obj.toString().length()
        deserialize(obj)
        if (count >= limit) done = true
      }
      pu.finish("read sequential full-object", count)
      count
    })

    Profile("sequential one-level", {
      val pu = new ProgressUtil
      var charsRead = 0
      val cur = getDb().getCollection(getCollectionName()).find(null, new BasicDBObject("users.activity", 0))
      var count = 0
      var done = false
      while (cur.hasNext() && !done) {
        count += 1
        pu.update("sequential one-level", count, limit)
        val obj = cur.next()
        charsRead += obj.toString().length()
        deserialize(obj)
        if (count >= limit) done = true
      }
      pu.finish("read sequential one-level", count)
      count
    })

    Profile("sequential top-level", {
      val pu = new ProgressUtil
      var charsRead = 0
      val cur = getDb().getCollection(getCollectionName()).find(null, new BasicDBObject("users", 0))
      var count = 0
      var done = false
      while (cur.hasNext() && !done) {
        count += 1
        pu.update("sequential top-level", count, limit)
        val obj = cur.next()
        charsRead += obj.toString().length()
        deserialize(obj)
        if (count >= limit) done = true
      }
      pu.finish("read sequential top-level", count)
      count
    })
  }

  def runUpdate(limit: Int, maxRecords: Int) = {
    Profile("random full-object", {
      val pu = new ProgressUtil
      var charsRead = 0
      val db = getDb()
      val coll = db.getCollection(getCollectionName())
      var count = 0
      var done = false
      while (!done) {
        count += 1
        pu.update("update objects", count, limit)
        val id = RandomDataUtil.getRandomInt(0, maxRecords - 1)
        coll.findOne(new BasicDBObject("_id", id.toLong)) match {
          case dbo: DBObject => {
            dbo.put("strval", RandomDataUtil.getRandomAlphaString(24))
            dbo.put("long_val", RandomDataUtil.getRandomLong())
            coll.save(dbo)
            db.getLastError
          }
          case _ =>
        }
        if (count >= limit) done = true
      }
      pu.finish("update random", count)
      count
    })
  }

  def runRandom(limit: Int, maxRecords: Int) = {
    Profile("random full-object", {
      val pu = new ProgressUtil
      var charsRead = 0
      val coll = getDb().getCollection(getCollectionName())
      var count = 0
      var done = false
      while (!done) {
        count += 1
        pu.update("random full objects", count, limit)
        val id = RandomDataUtil.getRandomInt(0, maxRecords - 1)
        val obj = coll.findOne(new BasicDBObject("_id", id.toLong))
        charsRead += obj.toString().length()
        deserialize(obj)
        if (count >= limit) done = true
      }
      pu.finish("read random full-object", count)
      count
    })

    Profile("random one-level", {
      val pu = new ProgressUtil
      var charsRead = 0
      val coll = getDb().getCollection(getCollectionName())
      var count = 0
      var done = false
      while (!done) {
        count += 1
        pu.update("random one-level", count, limit)
        val id = RandomDataUtil.getRandomInt(0, maxRecords - 1)
        val obj = coll.findOne(new BasicDBObject("_id", id.toLong), new BasicDBObject("users.activity", 0))
        charsRead += obj.toString().length()
        deserialize(obj)
        if (count >= limit) done = true
      }
      pu.finish("read random one-level", count)
      count
    })

    Profile("random top-level", {
      val pu = new ProgressUtil
      var charsRead = 0
      val coll = getDb().getCollection(getCollectionName())
      var count = 0
      var done = false
      while (!done) {
        count += 1
        pu.update("random top-level", count, limit)
        val id = RandomDataUtil.getRandomInt(0, maxRecords - 1)
        val obj = coll.findOne(new BasicDBObject("_id", id.toLong), new BasicDBObject("users", 0))
        charsRead += obj.toString().length()
        deserialize(obj)
        if (count >= limit) done = true
      }
      pu.finish("read random top-level", count)
      count
    })
  }

  def validateData(limit: Int) {
    val db = getDb()
    val coll = db.getCollection(getCollectionName())

    if (coll.count() == limit) {
      return ;
    }

    println("Warning!  Creating data");
    coll.drop()

    val pu = new ProgressUtil
    (0 to limit).foreach(i => {
      val dbo = new BasicDBObject()
      dbo.put("_id", i.toLong)
      dbo.put("strval", RandomDataUtil.getRandomAlphaString(24))
      dbo.put("long_val", RandomDataUtil.getRandomLong())

      val level1 = new BasicDBList()
      (0 to 5).foreach(j => {
        val level1Object = new BasicDBObject(
          Map("first_name" -> RandomDataUtil.getRandomAlphaString(5),
            "last_name" -> RandomDataUtil.getRandomAlphaString(8),
            "email" -> RandomDataUtil.getRandomEmail(),
            "ip_addr" -> RandomDataUtil.getRandomIpAddress()).asJava)

        val level2 = new BasicDBList()
        (0 to 15).foreach(k => {
          val level2Object = new BasicDBObject(
            Map("login_count" -> RandomDataUtil.getRandomLong(),
              "referrer" -> RandomDataUtil.getRandomURL()).asJava)
          level2.add(level2Object)
        })
        level1Object.put("activity", level2)
        level1.add(level1Object)
      })
      dbo.put("users", level1)
      pu.update("records created", i, limit)
      coll.save(dbo)
    })
    pu.finish("record creation", limit)
  }
}