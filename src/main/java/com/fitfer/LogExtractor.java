package com.fitfer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class LogExtractor {

	String root = "\\\\10.100.101.141\\log\\uci";
	
	public String copy2local(String pattern) {
		// TODO: search last file that starts with pattern [FIT, PC, FML, SIA]
		
		return "d:/asda/PC2012-Nov-27 11.log";
	}

	public String getLog(String fullFilename, String tx, String sub, String user) {

		String transaction = "<SUB>"+tx.trim()+"</SUB>";
		String subsystem = "<TRN>"+sub.trim()+"</TRN>";
		user = user.trim();

		String startword = "<FITBANK>";
		String endword = "</FITBANK>";
		
		String contents = "";
		try {
			List<String> lines = FileUtils.readLines(new File(fullFilename));
			for (String line : lines) {
				
				if (line.contains(transaction) && line.contains(subsystem) && line.contains(user)) {
					
					if (line.contains(endword)) {
						contents = line;
					} else {
						contents += line;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int start = contents.indexOf(startword);
		int end = contents.indexOf(endword);
		
		String lastcontent = contents.substring(start, end + endword.length());
		
		return lastcontent;
	}
}
