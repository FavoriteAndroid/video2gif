package com.bufer.util;

public class ConverterTool {

	public static Long string2second(String time) {
		String[] timea = time.split(":");
		Long total = 60*60*Long.valueOf(timea[0]) + 60*Long.valueOf(timea[1]) + Long.valueOf(timea[2]);
		return total;
	}
	
	public static String second2string(Long time) {
		String result = "0";
		Long secs = time % 60;
		Long mins = time / 60;
		result = String.format("00:%02d:%02d.0", mins, secs);
		return result;
	}
}
