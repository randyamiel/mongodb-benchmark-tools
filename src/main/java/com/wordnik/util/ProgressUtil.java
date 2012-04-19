package com.wordnik.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ProgressUtil {
	long START_TIME = 0;
	long LAST_REPORT = 0;
	long INTERVAL = 10000;
	NumberFormat PERCENT_FORMAT = new DecimalFormat("##.#%");
	NumberFormat LONG_FORMAT = new DecimalFormat("###,###");
	
	public void setInterval(long interval){
		INTERVAL = interval;
	}
	
	public void printProgress(String message, long wordsCount, long startTime, String type) {
		String msg = "";
		long elapsed = System.currentTimeMillis() - startTime;
		double rate = (double)wordsCount / (elapsed / 1000.0);
		NumberFormat nf = new DecimalFormat("#.##");

		msg = message + " " + LONG_FORMAT.format(wordsCount) + " (" + nf.format(rate) + " "+ type +"/sec)";
		System.out.println(msg);
	}

	public String update(String message, long current){
		synchronized(this){
			if(START_TIME == 0){
				START_TIME = System.currentTimeMillis();
				LAST_REPORT = System.currentTimeMillis();
			}
			String msg = "";
			if((System.currentTimeMillis() - LAST_REPORT) > INTERVAL){
				long elapsed = System.currentTimeMillis() - START_TIME;
				double rate = (double)current / (elapsed / 1000.0);
				NumberFormat nf = new DecimalFormat("#.##");
		
				msg = message + " " + LONG_FORMAT.format(current) + " (" + nf.format(rate) + " req/sec)";
				System.out.println(msg);
				LAST_REPORT = System.currentTimeMillis();
			}
			return msg;
		}
	}

	public boolean shouldUpdate() {
        if(START_TIME == 0){
            START_TIME = System.currentTimeMillis();
            LAST_REPORT = System.currentTimeMillis();
        }
        if((System.currentTimeMillis() - LAST_REPORT) > INTERVAL) {
            LAST_REPORT = System.currentTimeMillis();
            return true;
        }
        return false;
    }

	public String update(String message, long current, long totalCount){
		if(START_TIME == 0){
			START_TIME = System.currentTimeMillis();
			LAST_REPORT = System.currentTimeMillis();
		}
		String msg = "";
		if((System.currentTimeMillis() - LAST_REPORT) > INTERVAL){
			double percentComplete = ((double)current) / ((double) totalCount);
			long duration = System.currentTimeMillis() - START_TIME;
	
			double estimatedDuration = duration / (percentComplete);
			double eta = estimatedDuration - duration;
	
			String unit;
			if(eta > 86400000){
				eta /= 86400000.0;
				unit = "days";
			}
			else if (eta > (1000.0 * 60.0 * 60.0)){
				eta /= 1000.0 * 60.0 * 60.0;
				unit = "hours";
			}
			else{
				eta /= (1000.0 * 60.0);
				unit = "minutes";
			}
			
			long elapsed = System.currentTimeMillis() - START_TIME;
			double rate = (double)current / (elapsed / 1000.0);
			NumberFormat nf = new DecimalFormat("#.##");
	
			msg = message + " " + LONG_FORMAT.format(current) + " of " + LONG_FORMAT.format(totalCount) + " (" + nf.format(rate) + " req/sec, " + PERCENT_FORMAT.format(percentComplete) + ", elapsed: " + elapsed + ", eta: " + nf.format(eta) + " " + unit + ")";
			System.out.println(msg);
			LAST_REPORT = System.currentTimeMillis();
		}
		return msg;
	}
	
	public String finish(String message, long count){
		long duration = System.currentTimeMillis() - START_TIME;
		
		double rate = (double)count / (duration / 1000.0);
		NumberFormat nf = new DecimalFormat("#.##");

		String msg = "Finished " + message + " in " + TimeUtil.millisToSec(duration) + " seconds, " + nf.format(rate) + " req/sec";
		
		System.out.println(msg);

		reset();
		return msg;
	}
	
	public void reset(){
		START_TIME = 0;
		LAST_REPORT = 0;
	}
}
