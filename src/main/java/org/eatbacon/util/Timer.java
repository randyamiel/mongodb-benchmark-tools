package org.eatbacon.util;

public class Timer {
	long start = System.currentTimeMillis();
	long end = 0;
	public static Timer start(){
		return new Timer();
	}

	public void reset(){
		start = System.currentTimeMillis();
	}

	public void finish(){
		end = System.currentTimeMillis();
	}
	
	public long getElapsedTimeMillis(){
		return end - start;
	}
	
	public double getRate(long numberProcessed){
		double rate = (double)numberProcessed / (getElapsedTimeMillis() / 1000.0);
		return rate;
	}
}