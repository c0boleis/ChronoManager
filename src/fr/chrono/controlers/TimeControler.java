package fr.chrono.controlers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeControler {

	public static final String TIME_FORMAT ="^[0-9]{2}:[0-5][0-9]:[0-5][0-9],[0-9]{3}$";

	private static final boolean NO_DATE_FORMATER = true;

	public static boolean isTimeString(String timeString) {
		Pattern pattern = Pattern.compile(TimeControler.TIME_FORMAT);
		Matcher match = pattern.matcher(timeString);
		return match.find();
	}

	public static long parseTimeTolong(String timeString) {
		if(!isTimeString(timeString)) {
			throw new IllegalArgumentException("\""+timeString+"\" dosen't match with: "+TIME_FORMAT);
		}
		String[] info = timeString.split(":");
		String[] info2 = info[2].split(",");
		String hoursString = info[0];
		String minutesString = info[1];
		String secondsString = info2[0];
		String milliSecondsString = info2[1];

		int hours = Integer.parseInt(hoursString);
		int minutes = Integer.parseInt(minutesString);
		int seconds = Integer.parseInt(secondsString);
		int milliSeconds = Integer.parseInt(milliSecondsString);

		long hoursL = hours*60l*60l*1000l;
		long minutesL = minutes*60l*1000l;
		long secondsL = seconds*1000l;

		return hoursL+minutesL+secondsL+milliSeconds;
	}

	public static String parseTimeToString(long timeLong) {
		if(timeLong<0) {
			return "N/A";
		}
		if(NO_DATE_FORMATER) {
			long milliSeconds = timeLong%1000l;
			timeLong -= milliSeconds;
			long seconds = timeLong%(1000l*60l);
			timeLong -= seconds;
			long minutes = timeLong%(1000l*60l*60l);
			timeLong -= minutes;

			//			System.out.println("milliSeconds: "+milliSeconds);
			//			System.out.println("seconds: "+seconds/(1000l));
			//			System.out.println("minutes: "+minutes/(1000l*60l));
			//			System.out.println("hours: "+timeLong/(1000l*60l*60l));
			return longToString(timeLong/(1000l*60l*60l),2)+":"
			+longToString(minutes/(1000l*60l), 2)+":"
			+longToString(seconds/(1000l), 2)+","
			+longToString(milliSeconds, 3);
		}else {
			Date date = new Date(timeLong);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss,SSS");
			return format.format(date);
		}

	}

	public static String[] parseTimeToStringTab(long timeLong) {
		long milliSeconds = timeLong%1000l;
		timeLong -= milliSeconds;
		long seconds = timeLong%(1000l*60l);
		timeLong -= seconds;
		long minutes = timeLong%(1000l*60l*60l);
		timeLong -= minutes;

		String[] tabOut = new String[4];
		tabOut[0] = longToString(timeLong/(1000l*60l*60l),2);
		tabOut[1] = longToString(minutes/(1000l*60l),2);
		tabOut[2] = longToString(seconds/(1000l),2);
		tabOut[3] = longToString(milliSeconds,3);
		return tabOut;

	}

	private static String longToString(long number,int length) {
		String text = String.valueOf(number);
		while( text.length()<length) {
			text = "0"+text;
		}
		return text;
	}
	
	public static long getCurrentTime() {
		long dayTime = 24l*60l*60l*1000l;
		long currentTime = System.currentTimeMillis();
		TimeZone zone = TimeZone.getTimeZone("Etc/GMT-2");
		int offset = zone.getOffset(currentTime);
		currentTime+=offset;
		return currentTime%dayTime;
	}
	
	public static String getCurrentTimeString() {
		return parseTimeToString(getCurrentTime());
	}

}
