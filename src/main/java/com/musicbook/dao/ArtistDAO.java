package com.musicbook.dao;

import java.util.List;

import com.musicbook.entity.Artist;

public interface ArtistDAO {

	public List<Artist> getArtists();

	public void saveArtist(Artist theArtist);

	public Artist getArtist(int theId);

	public void deleteArtist(int theId);
}
