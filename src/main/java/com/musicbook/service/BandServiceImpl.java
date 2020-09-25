package com.musicbook.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.musicbook.dao.ArtistDAO;
import com.musicbook.dao.BandDAO;
import com.musicbook.dao.MembershipDAO;
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
	private MembershipDAO membershipDAO;
	
	@Autowired
	private ImageService imageService;
	
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
	public void createBand(CreateBandForm createBandForm) {
		
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
		
		Membership membership = new Membership();
		membership.setArtist(owner);
		membership.setBand(createdBand);
		membership.setState_id(Membership.STATE_ACCEPTED);
		membership.setCreated_at(timestamp);
		membership.setUpdated_at(timestamp);
		membershipDAO.saveMembership(membership);
	}
	
	@Override
	@Transactional
	public void updateBand(UpdateBandForm updateBandForm) {
		
		Band band = getBand(updateBandForm.getId());
		band.setName(updateBandForm.getName());
		band.setBio(updateBandForm.getBio());
		band.setLocation(updateBandForm.getLocation());
		band.setGenres(updateBandForm.getGenres());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		band.setUpdated_at(timestamp);
		bandDAO.saveBand(band);
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
