package com.musicbook.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.musicbook.dao.ArtistDAO;
import com.musicbook.dao.BandDAO;
import com.musicbook.entity.Artist;
import com.musicbook.entity.Band;
import com.musicbook.entity.Membership;
import com.musicbook.form.CreateBandForm;
import com.musicbook.form.UpdateBandForm;

@Service
public class BandServiceImpl implements BandService {
	
	@Autowired
	private BandDAO bandDAO;
	
	@Autowired
	private ArtistDAO artistDAO;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private MembershipService membershipService;
	
	@Override
	@Transactional
	public List<Band> getBands() {
		
		return bandDAO.getBands();
	}
	
	@Override
	@Transactional
	public List<Band> getBandsByOwnerId(int id) {
		
		return bandDAO.getBandsByOwnerId(id);
	}

	@Override
	@Transactional
	public Band createBand(CreateBandForm createBandForm) {
		
		Band band = new Band();
		band.setName(createBandForm.getName());
		band.setBio(createBandForm.getBio());
		band.setLocation(createBandForm.getLocation());
		band.setGenres(createBandForm.getGenres());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		band.setCreated_at(timestamp);
		band.setUpdated_at(timestamp);
		Artist owner = artistDAO.getArtist(createBandForm.getOwner_id());
		band.setOwner(owner);
		Band createdBand = bandDAO.saveBand(band);
		
		Membership createdMembership = membershipService.create(owner, createdBand, Membership.STATE_ACCEPTED);
		createdBand.setMemberships(Arrays.asList(createdMembership));
		
		return createdBand;
	}
	
	@Override
	@Transactional
	public Band updateBand(UpdateBandForm updateBandForm) {
		
		Band band = getBand(updateBandForm.getId());
		band.setName(updateBandForm.getName());
		band.setBio(updateBandForm.getBio());
		band.setLocation(updateBandForm.getLocation());
		band.setGenres(updateBandForm.getGenres());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		band.setUpdated_at(timestamp);
		return bandDAO.saveBand(band);
	}

	@Override
	@Transactional
	public Band getBand(int id) {
		
		return bandDAO.getBand(id);
	}

	@Override
	@Transactional
	public void deleteBand(Band band) {

		if (band.isHas_image()) {
			imageService.deleteImage("file-upload.bands-dir", "band_" + band.getId());
		}
		bandDAO.deleteBand(band.getId());
	}
	
	@Override
	@Transactional
	public List<Band> searchBands(String search) {
		
		return bandDAO.searchBands(search);
	}

	@Override
	@Transactional
	public void processAndSaveImage(Band band, MultipartFile file) throws IOException {
		
		boolean isImageSaved = imageService.processAndSaveImage(file, "file-upload.bands-dir", "band_" + band.getId());
		if (isImageSaved && !band.isHas_image()) {
			band.setHas_image(true);
			bandDAO.saveBand(band);
		}
	}
}
