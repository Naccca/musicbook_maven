package com.musicbook.dao;

import java.util.List;

import com.musicbook.entity.Band;

public interface BandDAO {

	public List<Band> getBands();
	
	public List<Band> getBandsByOwnerId(int id);

	public Band saveBand(Band band);

	public Band getBand(int id);

	public void deleteBand(int id);

	public List<Band> searchBands(String search);

	public Band getBandByName(String name);
}
