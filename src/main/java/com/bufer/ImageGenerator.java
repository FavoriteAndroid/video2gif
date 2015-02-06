package com.bufer;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import com.bufer.util.C;
import com.bufer.util.CommandLineTool;

public class ImageGenerator {

	public static void main(String[] args) {
		
		// Read all files in directory
		final File folder = new File(C.ROOT + C.OUT);
		Collection<File> files = FileUtils.listFiles(folder, C.VIDEO_EXTENSIONS, false);
		
		for (File file : files) {
			// Laufen <Film2GifBild>
			String imagesNamePattern = file.getName().replace(C.FORMAT, "") + C.PATTERN;
			
			String source = C.ROOT + C.OUT + file.getName();
			String target = C.ROOT + C.OUT + imagesNamePattern;
			
			String cmd = C.FFMPEG + " -i " + source + " -r 5 " + target;
			CommandLineTool.runcmd(cmd);
		}
	}
}
