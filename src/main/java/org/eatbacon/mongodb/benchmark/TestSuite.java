package org.eatbacon.mongodb.benchmark;

public class TestSuite {
	public static void main(String ... args){
		for(int i = 0; i < 5; i++){
			System.out.println("LOOP " + (i+1) + "---------------------------------");
			System.out.println("test_name,total_objects,rate (req/sec),throughput (mb/s)");
			RawBenchmark.main(args);
			ManualBenchmark.main(args);
			JacksonBenchmark.main(args);
//			MorphiaBenchmark.main(args);
		}
	}
}
