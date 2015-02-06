package com.bufer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static List<String> listFilesForFolder(final File folder) {
		
		List<String> fileList = new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	fileList.add(fileEntry.getName());
	        }
	    }
	    return fileList;
	}
}
