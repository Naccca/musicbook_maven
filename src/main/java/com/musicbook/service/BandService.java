package com.musicbook.service;

import java.util.List;

import com.musicbook.entity.Band;

public interface BandService {

	public List<Band> getBands();

	public void saveBand(Band theBand);

	public Band getBand(int theId);

	public void deleteBand(int theId);
}
