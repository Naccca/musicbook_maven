package com.musicbook.test.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.musicbook.dao.ArtistDAO;
import com.musicbook.dao.BandDAO;
import com.musicbook.entity.Artist;
import com.musicbook.entity.Band;
import com.musicbook.entity.Membership;
import com.musicbook.form.CreateBandForm;
import com.musicbook.form.UpdateBandForm;
import com.musicbook.service.BandServiceImpl;
import com.musicbook.service.ImageService;
import com.musicbook.service.MembershipService;

@ExtendWith(MockitoExtension.class)
public class BandServiceTest {

	@InjectMocks
	private BandServiceImpl bandService;
	
	@Mock
	private BandDAO bandDAO;
	
	@Mock
	private ArtistDAO artistDAO;
	
	@Mock
	private ImageService imageService;
	
	@Mock
	private MembershipService membershipService;
	
	@Test
	@DisplayName("Test getBand Success")
	void testGetBand() {
		
		Band band = new Band();
		band.setId(11);
		
		Mockito.when(bandDAO.getBand(11)).thenReturn(band);
		
		Assertions.assertEquals(band, bandService.getBand(11), "Returned band is not the same as mock");
	}
	
	@Test
	@DisplayName("Test getBand Not Found")
	void testGetBandNotFound() {
		
		Mockito.when(bandDAO.getBand(11)).thenReturn(null);
		
		Assertions.assertNull(bandService.getBand(11), "Band should not have been found");
	}
	
	@Test
	@DisplayName("Test getBandsByOwnerId")
	void testGetBandsByOwnerId() {
		
		Artist artist = new Artist();
		artist.setId(11);
		Band band1 = new Band();
		band1.setId(11);
		band1.setOwner(artist);
		Band band2 = new Band();
		band2.setId(12);
		band2.setOwner(artist);
		
		Mockito.when(bandDAO.getBandsByOwnerId(11)).thenReturn(Arrays.asList(band1, band2));
		
		Assertions.assertEquals(2, bandService.getBandsByOwnerId(11).size(), "Wrong number of bands returned");
	}
	
	@Test
	@DisplayName("Test getBands Success")
	void testGetBands() {
		
		Band band1 = new Band();
		band1.setId(11);
		Band band2 = new Band();
		band2.setId(12);
		
		Mockito.when(bandDAO.getBands()).thenReturn(Arrays.asList(band1, band2));
		
		Assertions.assertEquals(2, bandService.getBands().size(), "Wrong number of bands returned");
	}
	
	@Test
	@DisplayName("Test searchBands Success")
	void testSearchBands() {
		
		Band band1 = new Band();
		band1.setName("John Doe");
		Band band2 = new Band();
		band2.setName("Jane Doe");
		
		Mockito.when(bandDAO.searchBands("Doe")).thenReturn(Arrays.asList(band1, band2));
		
		Assertions.assertEquals(2, bandService.searchBands("Doe").size(), "Wrong number of bands returned");
	}
	
	@Test
	@DisplayName("Test createBand Success")
	void testCreateBand() {
		
		CreateBandForm createBandForm = new CreateBandForm();
		createBandForm.setName("John Doe");
		createBandForm.setBio("Short bio");
		createBandForm.setLocation("Test location");
		createBandForm.setGenres("Test genres");
		Artist artist = new Artist();
		artist.setId(11);
		createBandForm.setOwner_id(11);
		
		Mockito.when(artistDAO.getArtist(11)).thenReturn(artist);
		Mockito.when(bandDAO.saveBand(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
		
		Band createdBand = bandService.createBand(createBandForm);
		
		Assertions.assertEquals("John Doe", createdBand.getName(), "Name was not set properly");
		Assertions.assertEquals("Short bio", createdBand.getBio(), "Bio was not set properly");
		Assertions.assertEquals("Test location", createdBand.getLocation(), "Location was not set properly");
		Assertions.assertEquals("Test genres", createdBand.getGenres(), "Genres was not set properly");
		Assertions.assertNotNull(createdBand.getCreated_at(), "Created At was not set");
		Assertions.assertNotNull(createdBand.getUpdated_at(), "Updated At was not set");
		Assertions.assertEquals(createdBand.getCreated_at(), createdBand.getUpdated_at(), "Created At and Updated At are different");
		Assertions.assertFalse(createdBand.isHas_image(), "Has Image is not false");
		Assertions.assertEquals(artist, createdBand.getOwner(), "Owner was not set properly");
		Assertions.assertNotNull(createdBand.getMemberships(), "Membership was not set");
	}
	
	@Test
	@DisplayName("Test updateBand Success")
	void testUpdateBand() {
		
		Band band = new Band();
		band.setId(11);
		band.setName("John Doe");
		band.setBio("Test Bio");
		band.setGenres("Test Genress");
		band.setLocation("Test Location");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		band.setCreated_at(timestamp);
		band.setUpdated_at(timestamp);
		band.setHas_image(false);
		Artist artist = new Artist();
		artist.setId(11);
		band.setOwner(artist);
		Membership membership = new Membership();
		membership.setArtist(artist);
		membership.setBand(band);
		band.setMemberships(Arrays.asList(membership));
		
		UpdateBandForm updateBandForm = new UpdateBandForm();
		updateBandForm.setId(11);
		updateBandForm.setName("Updated Name");
		updateBandForm.setBio("Updated Bio");
		updateBandForm.setLocation("Updated Location");
		updateBandForm.setGenres("Updated Genres");
		
		Mockito.when(bandDAO.getBand(11)).thenReturn(band);
		Mockito.when(bandDAO.saveBand(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
		
		Band updatedBand = bandService.updateBand(updateBandForm);
		
		Assertions.assertEquals("Updated Name", updatedBand.getName(), "Name was not updated properly");
		Assertions.assertEquals("Updated Bio", updatedBand.getBio(), "Bio was not updated properly");
		Assertions.assertEquals("Updated Location", updatedBand.getLocation(), "Location was not updated properly");
		Assertions.assertEquals("Updated Genres", updatedBand.getGenres(), "Genress was not updated properly");
		Assertions.assertNotEquals(updatedBand.getCreated_at(), updatedBand.getUpdated_at(), "Updated at was not changed");
		
		Assertions.assertEquals(11, updatedBand.getId(), "Id was changed");
		Assertions.assertEquals(artist, updatedBand.getOwner(), "Owner was changed");
		Assertions.assertFalse(updatedBand.isHas_image(), "Has Image was changed");
		Assertions.assertNotNull(updatedBand.getMemberships(), "Membership was changed");
	}
	
	@Test
	@DisplayName("Test deleteBand Success")
	public void testDeleteBand() {
		
		Band band = new Band();
		band.setId(11);
		band.setHas_image(false);
		
		bandService.deleteBand(band);
		
		Mockito.verify(bandDAO, Mockito.times(1)).deleteBand(11);
	}
	
	@Test
	@DisplayName("Test processAndSaveImage Success")
	public void testProcessAndSaveImage() throws IOException {
		
		Band band = new Band();
		band.setHas_image(false);
		
		Mockito.when(imageService.processAndSaveImage(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
		
		bandService.processAndSaveImage(band, null);
		
		Assertions.assertTrue(band.isHas_image(), "Has Image was not set");
	}
	
	@Test
	@DisplayName("Test processAndSaveImage Failure")
	public void testProcessAndSaveImageFailure() throws IOException {
		
		Band band = new Band();
		band.setHas_image(false);
		
		Mockito.when(imageService.processAndSaveImage(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(false);
		
		bandService.processAndSaveImage(band, null);
		
		Assertions.assertFalse(band.isHas_image(), "Has Image was set");
	}
}
