package com.musicbook.service;

import java.util.List;

import com.musicbook.entity.Artist;
import com.musicbook.form.CreateArtistForm;
import com.musicbook.form.DeleteArtistForm;
import com.musicbook.form.UpdateArtistForm;

public interface ArtistService {

	public List<Artist> getArtists();

	public void createArtist(CreateArtistForm artist);
	
	public void updateArtist(UpdateArtistForm artist);

	public Artist getArtist(int id);
	
	public Artist getArtistByUsername(String username);

	public void deleteArtist(DeleteArtistForm artist);

	public List<Artist> searchArtists(String search);
}
