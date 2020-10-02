package com.musicbook.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.musicbook.entity.Band;
import com.musicbook.form.CreateBandForm;
import com.musicbook.form.UpdateBandForm;

public interface BandService {

	public List<Band> getBands();
	
	public List<Band> getBandsByOwnerId(int id);

	public Band createBand(CreateBandForm band);
	
	public Band updateBand(UpdateBandForm band);

	public Band getBand(int id);

	public void deleteBand(Band band);

	public List<Band> searchBands(String search);

	public void processAndSaveImage(Band band, MultipartFile file) throws IOException;

	public Band getBandByName(String name);
}
