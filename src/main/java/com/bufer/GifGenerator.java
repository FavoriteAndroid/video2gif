package com.bufer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.javalite.activejdbc.Base;

import au.com.bytecode.opencsv.CSVReader;

import com.bufer.model.Gif;
import com.bufer.util.C;
import com.bufer.util.CommandLineTool;
import com.bufer.util.ConverterTool;
import com.bufer.util.ImageManager;

public class GifGenerator implements Runnable {
	
	Gif g;
	boolean finished = true;
	private static final Logger log = Logger.getLogger(GifGenerator.class.getCanonicalName());

	public void run() {
		log.info("starting thread");
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gif_crawler", "root", "root");

		updateCsvToDatabase();

		// TODO: read next item in database
		DateFormat df = new SimpleDateFormat("dd-MM-yy");
		Date date;
		try {
			date = (Date)df.parse("21-03-81");
			g = Gif.findFirst("gif_created_on < ?", date);
	    	getVideoChunk(
	    			g.getString("father_filename"), 
	    			g.getString("start"),
	    			g.getString("end"),
	    			g.getString("name"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

 		while (finished) {
    		try {
				Thread.sleep(5000);
    			File file = new File(g.getString("filename")); 
    			if(file.exists()) {
        			generateImages(file);
    			}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

    		try {
				Thread.sleep(5000);
    			File file = new File(g.getString("filename")); 
    			if(file.exists()) {
    	    		addCaption(file);
    				createGif(file);
    			}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			finished = false;
    	}
    }
	
	public boolean existsFile() {
		return false;
	}
	
	public boolean addCaption(File folder) {
		Collection<File> images = FileUtils.listFiles(folder, C.IMAGE_EXTENSIONS, true);
		for (File file : images) {
			String name = C.ROOT + C.OUT + file.getName();
			ImageManager.addText(name, "TTEESSTT CAPITON", name);
		}
		g.set("captionized_on", new java.util.Date()).saveIt();
		return false;
	}

	public boolean generateImages(File videoFile) {
		// TODO: create folder for TARGET
		String imagesNamePattern = videoFile.getName().replace(C.FORMAT, "") + C.PATTERN;
		
		String source = C.ROOT + C.OUT + videoFile.getName();
		String target = C.ROOT + C.OUT + imagesNamePattern;
		
		String cmd = C.FFMPEG + " -i " + source + " -r 5 " + target;
		CommandLineTool.runcmd(cmd);
		
		return false;
	}
	
	/*
	 * Cut the video in chunks
	 * Start and Duration should have format hh:mm:ss.x
	 * filename: input filename
	 * name: used to generate output filename
	 */
	public static void getVideoChunk(String filename, String start, String duration, String name) {
		System.out.println("Processing: "+name);
		String cmd = null;
		
		// geschnitten Filme
		String input = C.ROOT + filename;
		String output = C.OUT + name + C.FORMAT;
		cmd = String.format(C.FFMPEG + " -ss %s -t %s -i \"%s\" \"%s\"", start, duration, input, output);
		CommandLineTool.runcmd(cmd);
	}
	
	public void createGif(File folder) {
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
	
	public void updateCsvToDatabase() {
		String videoListCSV = C.ROOT + C.CSV_FILE;
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(videoListCSV));
		    String[] next;
		    String father_video_file = "";
		    String father_video_url = "";
		    while ((next = reader.readNext()) != null) {
		    	if (!next[0].isEmpty()) {
		    		father_video_file = next[0];
		    		father_video_url = next[5];
		    	}
		    	String start = next[1];
		    	String end = next[2];
		    	String name = next[4];
		    	if (!start.isEmpty() || !end.isEmpty() || !name.isEmpty()) {

		        	Long duration = ConverterTool.string2second(end)-ConverterTool.string2second(start)+1;
		        	
		    		g = new Gif();
		    		g.set("father_video_file", father_video_file);
		    		g.set("father_video_url", father_video_url);
		    		g.set("start", start);
		    		g.set("end", end);
		    		g.set("duration", ConverterTool.second2string(duration));
		    		g.set("name", name);
		    		g.set("filename", name);
		    		g.set("created_on", new java.util.Date());
		    		g.saveIt();
		    	}
		    }
		} catch (FileNotFoundException e) {
			System.out.println("File <"+videoListCSV+"> not found");
		} catch (IOException e) {
			System.out.println("File <"+videoListCSV+"> can't be read");
		}
	}
}
