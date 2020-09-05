package com.musicbook.service;

import java.util.List;

import com.musicbook.entity.Band;
import com.musicbook.form.CreateBandForm;
import com.musicbook.form.DeleteBandForm;
import com.musicbook.form.UpdateBandForm;

public interface BandService {

	public List<Band> getBands();
	
	public List<Band> getBandsByOwnerId(int id);

	public void createBand(CreateBandForm band);
	
	public void updateBand(UpdateBandForm band);

	public Band getBand(int id);

	public void deleteBand(DeleteBandForm band);

	public List<Band> searchBands(String search);
}
