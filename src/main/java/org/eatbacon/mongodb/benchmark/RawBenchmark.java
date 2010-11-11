package org.eatbacon.mongodb.benchmark;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eatbacon.util.Timer;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class RawBenchmark extends BaseBenchmark {
	long charsRead = 0;

	public static void main(String ... args){
		if(!parseArgs(args)){
			usage();
			return;
		}

		RawBenchmark b = new RawBenchmark();
		try {
			b.run();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run() throws Exception {
		Timer timer = null;
		long count = 0;
		
		timer = Timer.start();
		count = fetchFullObject();
		timer.finish();
		report(this.getClass().getSimpleName(), "full", count, timer.getRate(count), getThroughput(charsRead, timer.getElapsedTimeMillis()));

		timer.reset();
		count = fetchOneLevelObject();
		timer.finish();
		report(this.getClass().getSimpleName(), "one-level", count, timer.getRate(count), getThroughput(charsRead, timer.getElapsedTimeMillis()));

		timer.reset();
		count = fetchTopLevelObject();
		timer.finish();
		report(this.getClass().getSimpleName(), "top-level", count, timer.getRate(count), getThroughput(charsRead, timer.getElapsedTimeMillis()));
	}

	static NumberFormat NUMBER_FORMAT = new DecimalFormat("##.##");
	private void report(String classname, String testName, long totalObjects, double rate, double throughput) {
		
		if(SHOW_AS_CSV){
			System.out.println(classname + "," + testName + "," + totalObjects + "," + NUMBER_FORMAT.format(rate) + "," + NUMBER_FORMAT.format(throughput));
		}
		else{
			System.out.println(classname + ":" + testName + ", objects: " + totalObjects + ", rate: " + NUMBER_FORMAT.format(rate) + ", throughput: " + NUMBER_FORMAT.format(throughput) + " mb/sec");
		}
	}

	public long fetchFullObject() throws Exception {
		charsRead = 0;
		DBCursor cur = super.getDb().getCollection(getCollection()).find();
		long count = 0;
		while(cur.hasNext()){
			++count;
			DBObject obj = cur.next();
			charsRead += obj.toString().length();
			deserialize(obj);
			if(count >= LIMIT){
				break;
			}
		}
		return count;
	}

	public long fetchOneLevelObject() throws Exception {
		charsRead = 0;
		DBCursor cur = super.getDb().getCollection(getCollection()).find(null, new BasicDBObject("users.activity", 0));
		long count = 0;
		while(cur.hasNext()){
			++count;
			DBObject obj = cur.next();
			charsRead += obj.toString().length();
			deserialize(obj);
			if(count >= LIMIT){
				break;
			}
		}
		return count;
	}

	public long fetchTopLevelObject() throws Exception {
		charsRead = 0;
		DBCursor cur = super.getDb().getCollection(getCollection()).find(null, new BasicDBObject("users", 0));
		long count = 0;
		while(cur.hasNext()){
			++count;
			DBObject obj = cur.next();
			charsRead += obj.toString().length();
			deserialize(obj);
			if(count >= LIMIT){
				break;
			}
		}
		return count;
	}

	static double getThroughput(long charsRead, long elapsed){
		double brate = (double)charsRead / (elapsed / 1000.0) / ((double)1048576L);
		return brate;
	}
	
	//	does nothing in raw
	public void deserialize(DBObject obj){
		
	}
}