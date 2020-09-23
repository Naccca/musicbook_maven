package com.musicbook.service;

import com.musicbook.entity.Artist;

public interface EmailService {

	public void sendVerificationEmail(Artist artist);
}
