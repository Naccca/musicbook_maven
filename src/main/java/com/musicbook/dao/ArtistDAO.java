package com.musicbook.dao;

import java.util.List;

import com.musicbook.entity.Artist;

public interface ArtistDAO {

	public List<Artist> getArtists();

	public void saveArtist(Artist artist);

	public Artist getArtist(int id);

	public void deleteArtist(int id);
	
	public Artist findArtistByUsername(String username);

	public List<Artist> searchArtists(String search);

	Artist findArtistByName(String name);
}
