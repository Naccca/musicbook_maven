package com.musicbook.dao;

import java.util.List;

import com.musicbook.entity.Band;

public interface BandDAO {

	public List<Band> getBands();

	public void saveBand(Band theBand);

	public Band getBand(int theId);

	public void deleteBand(int theId);
}
