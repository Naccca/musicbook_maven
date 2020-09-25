package com.musicbook.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	public boolean processAndSaveImage(MultipartFile file, String dirPropertyName, String name) throws IOException;
	
	public void deleteImage(String dirPropertyName, String name);
}
