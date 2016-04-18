package com.bufer.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("gifs")
public class Gif extends Model {
	
}

/*

DROP TABLE `gifs`;
CREATE TABLE IF NOT EXISTS `gifs` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `father_video_file` VARCHAR(255) NOT NULL,
  `father_video_url` VARCHAR(255) NOT NULL,
  `start` VARCHAR(20) NOT NULL,
  `end` VARCHAR(20) NOT NULL,
  `duration` VARCHAR(20) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `filename` VARCHAR(255) NOT NULL,
  `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- creation date of this row
  `updated_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- update date of this row
  `mp4_created_on` TIMESTAMP, -- generation of mp4 file from father file
  `gif_created_on` TIMESTAMP, -- creation date of gif file
  `images_created_on` TIMESTAMP,
  `captionized_on` TIMESTAMP,
  PRIMARY KEY (`id`)
);

*/