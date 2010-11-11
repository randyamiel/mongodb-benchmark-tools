package org.eatbacon.mongodb.benchmark;

import org.eatbacon.mongodb.benchmark.morphia.entity.TestObject;

import com.google.code.morphia.Morphia;
import com.mongodb.DBObject;

public class MorphiaBenchmark extends RawBenchmark {
	public static void main(String ... args){
		if(!parseArgs(args)){
			usage();
			return;
		}

		MorphiaBenchmark b = new MorphiaBenchmark();
		try {
			getMorphia();
			b.run();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void deserialize(DBObject obj){
		try{
			getMorphia().fromDBObject(TestObject.class, obj);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	static Morphia MORPHIA;
	
	public static Morphia getMorphia(){
		if(MORPHIA == null){
			MORPHIA = new Morphia();
			MORPHIA.map(TestObject.class);
		}
		return MORPHIA;
	}
}
