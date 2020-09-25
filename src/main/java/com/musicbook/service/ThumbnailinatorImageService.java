package com.musicbook.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
@PropertySource("classpath:file-upload.properties")
public class ThumbnailinatorImageService implements ImageService {

	@Autowired
	private Environment env;
	
	@Override
	public boolean processAndSaveImage(MultipartFile file, String dirPropertyName, String name) throws IOException {
		
		if (file.getSize() == 0) {
			return false;
		}
		
		String tempFileName = env.getProperty("file-upload.tmp-dir") + name + "_temp.jpg";//
		file.transferTo(Paths.get(tempFileName));
		
		String bigFileName = env.getProperty(dirPropertyName) + name + "_big.jpg";
		String smallFileName = env.getProperty(dirPropertyName) + name + "_small.jpg";
		
		Thumbnails.of(tempFileName)
			.crop(Positions.CENTER)
        	.size(636, 421)
        	.outputQuality(1)
        	.toFile(bigFileName);
		
		Thumbnails.of(tempFileName)
			.crop(Positions.CENTER)
	    	.size(128, 128)
	    	.outputQuality(1)
	    	.toFile(smallFileName);
		
		return true;
	}

	@Override
	public void deleteImage(String dirPropertyName, String name) {
		
		new File(env.getProperty(dirPropertyName) + name + "_big.jpg").delete();
		new File(env.getProperty(dirPropertyName) + name + "_small.jpg").delete();
	}
}
