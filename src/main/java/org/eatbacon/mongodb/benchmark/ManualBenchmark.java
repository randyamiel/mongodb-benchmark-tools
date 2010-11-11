package org.eatbacon.mongodb.benchmark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eatbacon.mongodb.benchmark.pojo.TestObject;
import org.eatbacon.mongodb.benchmark.pojo.User;
import org.eatbacon.mongodb.benchmark.pojo.UserActivity;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ManualBenchmark extends RawBenchmark {
	public static void main(String ... args){
		if(!parseArgs(args)){
			usage();
			return;
		}

		ManualBenchmark b = new ManualBenchmark();
		try {
			b.run();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void deserialize(DBObject obj){
		try{
			TestObject o = new TestObject();
			BasicDBObject dbo = (BasicDBObject)obj;
			o.setStringVal(dbo.getString("strval"));
			o.setLongVal(dbo.getLong("long_val"));
			BasicDBList level1 = (BasicDBList)dbo.get("users");

			if(level1 != null){
				List<User> users = new ArrayList<User>();
				for(Iterator<?> x = level1.iterator(); x.hasNext();){
					User user = new User();
					BasicDBObject level1Object = (BasicDBObject) x.next();
					user.setFirstName(level1Object.getString("first_name"));
					user.setLastName(level1Object.getString("last_name"));
					user.setEmail(level1Object.getString("email"));
					user.setIpAddress(level1Object.getString("ip_addr"));
					
					BasicDBList level2 = (BasicDBList)dbo.get("activity");
					if(level2 != null){
						List<UserActivity> activities = new ArrayList<UserActivity>();
						for(Iterator<?> y = level2.iterator(); y.hasNext();){
							UserActivity activity = new UserActivity();
							BasicDBObject level2Object = (BasicDBObject) y.next();
		
							activity.setLoginCount(level2Object.getLong("login_count"));
							activity.setReferrer(level2Object.getString("referrer"));
							
							activities.add(activity);
						}
						user.setActivity(activities);
						users.add(user);
					}
				}
				o.setUsers(users);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
