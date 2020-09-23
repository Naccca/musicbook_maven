package com.musicbook.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.musicbook.entity.Artist;
import com.musicbook.form.CreateArtistForm;
import com.musicbook.form.UpdateArtistForm;

public interface ArtistService {

	public List<Artist> getArtists();

	public void createArtist(CreateArtistForm artist);
	
	public void updateArtist(UpdateArtistForm artist);

	public Artist getArtist(int id);
	
	public Artist getArtistByEmail(String email);

	public void deleteArtist(Artist artist);

	public List<Artist> searchArtists(String search);

	public void processAndSaveImage(Artist artist, MultipartFile file) throws IOException;

	public Artist getArtistByToken(String token);

	public void verifyArtist(Artist artist);
}
