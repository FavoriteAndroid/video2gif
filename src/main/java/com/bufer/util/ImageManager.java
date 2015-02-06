package com.bufer.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

public class ImageManager {

	public static boolean addText(String imageFilename, String captionText, String out) {
		BufferedImage image;
		boolean res = false;
		try {
			image = ImageIO.read(new File(imageFilename));
			Graphics g = image.getGraphics();
			g.setFont(g.getFont().deriveFont(14f));
			g.drawString(captionText, 5, 20);
			g.dispose();

			res = ImageIO.write(image, "png", new File(out));
			System.out.println("File " + imageFilename + " converted");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	public static void addTextToManyImages(String pattern, String captionText, String out) {
		String path = "."; 

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().startsWith(pattern)) {
				String filename = listOfFiles[i].getName();
				addText(filename, captionText, out);
			}
		}
	}
}
