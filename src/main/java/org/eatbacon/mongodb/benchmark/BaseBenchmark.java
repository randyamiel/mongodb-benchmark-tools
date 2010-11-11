package org.eatbacon.mongodb.benchmark;

import org.eatbacon.util.RandomDataUtil;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public abstract class BaseBenchmark {
	protected static String DATABASE_NAME = null;
	protected static String DATABASE_USERNAME = null;
	protected static String DATABASE_PASSWORD = null;
	protected static String DATABASE_HOST = null;
	protected static String DATABASE_COLLECTION = null;
	protected static DB DB_CONNECTION = null;

	protected static boolean SHOW_AS_CSV = false;
	protected static int RECORDS_TO_FETCH = 250000;
	
	protected static int LIMIT = Integer.MAX_VALUE;

	public BaseBenchmark(){
		setup();
	}

	protected static boolean parseArgs(String[] args) {
		for (int i = 0; i < args.length; i++) {
			switch (args[i].charAt(1)) {
			case 'C':
				SHOW_AS_CSV = true;
				break;
			case 'c':
				DATABASE_COLLECTION = args[++i];
				break;
			case 'd':
				DATABASE_NAME = args[++i];
				break;
			case 'u':
				DATABASE_USERNAME = args[++i];
				break;
			case 'p':
				DATABASE_PASSWORD = args[++i];
				break;
			case 'h':
				DATABASE_HOST = args[++i];
				break;
			case 'S':
				RECORDS_TO_FETCH = Integer.parseInt(args[++i]);
				break;
			case 'l':
				LIMIT = Integer.parseInt(args[++i]);
				break;
			default:
				return false;
			}
		}
		return true;
	}

	protected static void usage(){
		System.out.println("usage:");
		System.out.println(" -C : write results as CSV");
		System.out.println(" -c : collection name");
		System.out.println(" -d : database name");
		System.out.println(" -h : hostname");
		System.out.println(" -S : total records to create in collection");
		System.out.println(" -l : max records to fetch in profile");
		System.out.println(" [-u : username]");
		System.out.println(" [-p : password]");
	}
	
	protected void createData() throws Exception {
		DB db = getDb();
		DBCollection coll = db.getCollection(getCollection());

		if(coll.count() == RECORDS_TO_FETCH){
			return;
		}

		System.out.println("Warning!  Creating data");
		coll.drop();

		for(int i = 0; i < RECORDS_TO_FETCH; i++){
			BasicDBObject dbo = new BasicDBObject();
			dbo.put("_id", new Long(i));
			dbo.put("strval", RandomDataUtil.getRandomAlphaString(24));
			dbo.put("long_val", RandomDataUtil.getRandomLong());
			
			BasicDBList level1 = new BasicDBList();
			for(int j = 0; j < 5; j++){
				BasicDBObject level1Object = new BasicDBObject();
				level1Object.put("first_name", RandomDataUtil.getRandomAlphaString(5));
				level1Object.put("last_name", RandomDataUtil.getRandomAlphaString(8));
				level1Object.put("email", RandomDataUtil.getRandomEmail());
				level1Object.put("ip_addr", RandomDataUtil.getRandomIpAddress());

				BasicDBList level2 = new BasicDBList();
				for(int k = 0; k < 15; k++){
					BasicDBObject level2Object = new BasicDBObject();
					level2Object.put("login_count", RandomDataUtil.getRandomLong());
					level2Object.put("referrer", RandomDataUtil.getRandomURL());
					level2.add(level2Object);
				}
				level1Object.put("activity", level2);
				level1.add(level1Object);
			}
			dbo.put("users", level1);
			
			coll.save(dbo);
		}
	}
	
	protected void setup(){
		try{
			createData();
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	protected String getCollection(){
		return DATABASE_COLLECTION;
	}

	protected DB getDb() throws Exception {
		if(DB_CONNECTION == null){
			Mongo m = new Mongo(DATABASE_HOST);
			if(DATABASE_USERNAME != null){
				DB_CONNECTION = m.getDB(DATABASE_NAME);
				DB_CONNECTION.authenticate(DATABASE_USERNAME, DATABASE_PASSWORD.toCharArray());
			}
			else{
				DB_CONNECTION = m.getDB(DATABASE_NAME);
			}
		}
		return DB_CONNECTION;
	}
}
