package com.musicbook.dao;

import java.util.List;

import com.musicbook.entity.Artist;

public interface ArtistDAO {

	public List<Artist> getArtists();

	public Artist saveArtist(Artist artist);

	public Artist getArtist(int id);

	public void deleteArtist(int id);
	
	public Artist findArtistByEmail(String email);

	public List<Artist> searchArtists(String search);

	public Artist findArtistByName(String name);

	public Artist findArtistByToken(String token);
}
