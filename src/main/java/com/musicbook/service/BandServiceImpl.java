package com.musicbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicbook.dao.ArtistDAO;
import com.musicbook.dao.BandDAO;
import com.musicbook.entity.Artist;
import com.musicbook.entity.Band;

@Service
public class BandServiceImpl implements BandService {
	
	@Autowired
	private BandDAO bandDAO;
	
	@Autowired
	private ArtistDAO artistDAO;

	@Override
	@Transactional
	public List<Band> getBands() {
		
		return bandDAO.getBands();
	}

	@Override
	@Transactional
	public void saveBand(Band theBand) {
		
		Artist owner = artistDAO.getArtist(1);
		theBand.setOwner(owner);
		
		bandDAO.saveBand(theBand);
		
	}

	@Override
	@Transactional
	public Band getBand(int theId) {
		
		return bandDAO.getBand(theId);
	}

	@Override
	@Transactional
	public void deleteBand(int theId) {
		
		bandDAO.deleteBand(theId);
		
	}

}
