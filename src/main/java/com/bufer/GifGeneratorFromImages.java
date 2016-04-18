package com.bufer;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import com.bufer.util.C;
import com.bufer.util.CommandLineTool;
import com.bufer.util.ImageManager;

public class GifGeneratorFromImages {

	public static void main(String[] args) {
		
		File folder = new File(C.ROOT+C.OUT);

		// add caption
		Collection<File> images = FileUtils.listFiles(folder, C.IMAGE_EXTENSIONS, true);
		for (File file : images) {
			String name = C.ROOT + C.OUT + file.getName();
			ImageManager.addText(name, "TTEESSTT CAPITON", name);
		}
		
		// join files into GIF
		Collection<File> files = FileUtils.listFiles(folder, C.VIDEO_EXTENSIONS, true);
		for (File file : files) {
			// Hinzufugen Beschriftungstext zum Film
			String name = C.ROOT + C.OUT + file.getName().replace(C.FORMAT, "");
			String cmd = "convert -delay 20 -loop 0 "+name+"*.png "+name+".gif";
			// ffmpeg -i input\tmp_%d.jpg  -r 1 combine.swf
			// ffmpeg -f image2 -r 1/5 -i img%03d.png -vcodec libx264 out.mp4
			CommandLineTool.runcmd(cmd);
		}
	}
}
