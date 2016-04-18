package com.bufer.util;

import java.io.IOException;

public class CommandLineTool {

	public static String runcmd(String cmd) {

		System.out.println("Command: "+cmd);
		String filenamePattern = null;
		Runtime rt = Runtime.getRuntime();
		try {
			//			Process proc = 
			rt.exec(cmd);
			//
			//			String s = "";
			//	        BufferedReader stdInput = new BufferedReader(new 
			//	                InputStreamReader(proc.getInputStream()));
			//
			//	           BufferedReader stdError = new BufferedReader(new 
			//	                InputStreamReader(proc.getErrorStream()));
			//
			//	           // read the output from the command
			//	           System.out.println("Here is the standard output of the command:\n");
			//	           while ((s = stdInput.readLine()) != null) {
			//	               System.out.println(s);
			//	           }
			//
			//	           // read any errors from the attempted command
			//	           System.out.println("Here is the standard error of the command (if any):\n");
			//	           while ((s = stdError.readLine()) != null) {
			//	               System.out.println(s);
			//	           }

		} catch (IOException e) {
			e.printStackTrace();
		}

		return filenamePattern;

	}
}
