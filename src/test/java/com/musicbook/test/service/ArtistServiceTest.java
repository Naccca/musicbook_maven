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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.musicbook.dao.ArtistDAO;
import com.musicbook.entity.Artist;
import com.musicbook.form.CreateArtistForm;
import com.musicbook.form.UpdateArtistForm;
import com.musicbook.service.ArtistServiceImpl;
import com.musicbook.service.EmailService;
import com.musicbook.service.ImageService;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

	@InjectMocks
	private ArtistServiceImpl artistService;
	
	@Mock
	private ArtistDAO artistDAO;
	
	@Mock
	private EmailService emailService;

	@Mock
	private ImageService imageService;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Test
	@DisplayName("Test getArtist Success")
	void testGetArtist() {
		
		Artist artist = new Artist();
		artist.setId(11);
		
		Mockito.when(artistDAO.getArtist(11)).thenReturn(artist);
		
		Assertions.assertEquals(artist, artistService.getArtist(11), "Returned artist is not the same as mock");
	}
	
	@Test
	@DisplayName("Test getArtist Not Found")
	void testGetArtistNotFound() {
		
		Mockito.when(artistDAO.getArtist(11)).thenReturn(null);
		
		Assertions.assertNull(artistService.getArtist(11), "Artist should not have been found");
	}
	
	@Test
	@DisplayName("Test getArtistByEmail")
	void testGetArtistByEmail() {
		
		Artist artist = new Artist();
		artist.setEmail("johndoe@example.com");
		
		Mockito.when(artistDAO.findArtistByEmail("johndoe@example.com")).thenReturn(artist);
		
		Assertions.assertEquals(artist, artistService.getArtistByEmail("johndoe@example.com"), "Returned artists is not the same as mock");
	}
	
	@Test
	@DisplayName("Test getArtistByEmail Not Found")
	void testGetArtistByEmailNotFound() {
		
		Mockito.when(artistDAO.findArtistByEmail("johndoe@example.com")).thenReturn(null);
		
		Assertions.assertNull(artistService.getArtistByEmail("johndoe@example.com"), "Artist should not have been found");
	}
	
	@Test
	@DisplayName("Test getArtistByToken")
	void testGetArtistByToken() {
		
		Artist artist = new Artist();
		artist.setToken("Test Token");
		
		Mockito.when(artistDAO.findArtistByToken("Test Token")).thenReturn(artist);
		
		Assertions.assertEquals(artist, artistService.getArtistByToken("Test Token"), "Returned artists is not the same as mock");
	}
	
	@Test
	@DisplayName("Test getArtistByToken Not Found")
	void testGetArtistByTokenNotFound() {
		
		Mockito.when(artistDAO.findArtistByToken("Test Token")).thenReturn(null);
		
		Assertions.assertNull(artistService.getArtistByToken("Test Token"), "Artist should not have been found");
	}
	
	@Test
	@DisplayName("Test getArtists Success")
	void testGetArtists() {
		
		Artist artist1 = new Artist();
		artist1.setId(11);
		Artist artist2 = new Artist();
		artist2.setId(12);
		
		Mockito.when(artistDAO.getArtists()).thenReturn(Arrays.asList(artist1, artist2));
		
		Assertions.assertEquals(2, artistService.getArtists().size(), "Wrong number of artists returned");
	}
	
	@Test
	@DisplayName("Test searchArtists Success")
	void testSearchArtists() {
		
		Artist artist1 = new Artist();
		artist1.setName("John Doe");
		Artist artist2 = new Artist();
		artist2.setName("Jane Doe");
		
		Mockito.when(artistDAO.searchArtists("Doe")).thenReturn(Arrays.asList(artist1, artist2));
		
		Assertions.assertEquals(2, artistService.searchArtists("Doe").size(), "Wrong number of artists returned");
	}
	
	@Test
	@DisplayName("Test createArtist Success")
	void testCreateArtist() {
		
		CreateArtistForm createArtistForm = new CreateArtistForm();
		createArtistForm.setEmail("johndoe@example.com");
		createArtistForm.setPassword("123");
		createArtistForm.setName("John Doe");
		createArtistForm.setBio("Short bio");
		createArtistForm.setLocation("Test location");
		createArtistForm.setInstruments("Test instruments");
		
		Mockito.when(passwordEncoder.encode("123")).thenReturn("hashed 123");
		Mockito.when(artistDAO.saveArtist(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
		
		Artist createdArtist = artistService.createArtist(createArtistForm);
		
		Assertions.assertEquals("johndoe@example.com", createdArtist.getEmail(), "Email was not set properly");
		Assertions.assertEquals("hashed 123", createdArtist.getPassword_hash(), "Password Hash was not set properly");
		Assertions.assertEquals("John Doe", createdArtist.getName(), "Name was not set properly");
		Assertions.assertEquals("Short bio", createdArtist.getBio(), "Bio was not set properly");
		Assertions.assertEquals("Test location", createdArtist.getLocation(), "Location was not set properly");
		Assertions.assertEquals("Test instruments", createdArtist.getInstruments(), "Instruments was not set properly");
		Assertions.assertNotNull(createdArtist.getCreated_at(), "Created At was not set");
		Assertions.assertNotNull(createdArtist.getUpdated_at(), "Updated At was not set");
		Assertions.assertEquals(createdArtist.getCreated_at(), createdArtist.getUpdated_at(), "Created At and Updated At are different");
		Assertions.assertFalse(createdArtist.isHas_image(), "Has Image is not false");
		Assertions.assertFalse(createdArtist.isIs_enabled(), "Is Enabled is not false");
		Assertions.assertNotNull(createdArtist.getToken(), "Token was not set");
	}
	
	@Test
	@DisplayName("Test updateArtist Success")
	void testUpdateArtist() {
		
		Artist artist = new Artist();
		artist.setId(11);
		artist.setEmail("johndoe@example.com");
		artist.setName("John Doe");
		artist.setBio("Test Bio");
		artist.setInstruments("Test Instruments");
		artist.setLocation("Test Location");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		artist.setCreated_at(timestamp);
		artist.setUpdated_at(timestamp);
		artist.setHas_image(false);
		artist.setIs_enabled(true);
		artist.setToken("Test Token");
		
		UpdateArtistForm updateArtistForm = new UpdateArtistForm();
		updateArtistForm.setId(11);
		updateArtistForm.setName("Updated Name");
		updateArtistForm.setBio("Updated Bio");
		updateArtistForm.setLocation("Updated Location");
		updateArtistForm.setInstruments("Updated Instruments");
		
		Mockito.when(artistDAO.getArtist(11)).thenReturn(artist);
		Mockito.when(artistDAO.saveArtist(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
		
		Artist updatedArtist = artistService.updateArtist(updateArtistForm);
		
		Assertions.assertEquals("Updated Name", updatedArtist.getName(), "Name was not updated properly");
		Assertions.assertEquals("Updated Bio", updatedArtist.getBio(), "Bio was not updated properly");
		Assertions.assertEquals("Updated Location", updatedArtist.getLocation(), "Location was not updated properly");
		Assertions.assertEquals("Updated Instruments", updatedArtist.getInstruments(), "Instruments was not updated properly");
		Assertions.assertNotEquals(updatedArtist.getCreated_at(), updatedArtist.getUpdated_at(), "Updated at was not changed");
		
		Assertions.assertEquals(11, updatedArtist.getId(), "Id was changed");
		Assertions.assertEquals("johndoe@example.com", updatedArtist.getEmail(), "Email was changed");
		Assertions.assertFalse(updatedArtist.isHas_image(), "Has Image was changed");
		Assertions.assertTrue(updatedArtist.isIs_enabled(), "Is Enabled was changed");
		Assertions.assertEquals("Test Token", updatedArtist.getToken(), "Token was changedy");
	}
	
	@Test
	@DisplayName("Test verifyArtist Success")
	public void testVerifyArtist() {
		
		Artist artist = new Artist();
		artist.setIs_enabled(false);
		
		Mockito.when(artistDAO.saveArtist(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
		
		Artist verifiedArtist = artistService.verifyArtist(artist);
		
		Assertions.assertTrue(verifiedArtist.isIs_enabled(), "Is Enabled not set");
	}
	
	@Test
	@DisplayName("Test deleteArtist Success")
	public void testDeleteArtist() {
		
		Artist artist = new Artist();
		artist.setId(11);
		artist.setHas_image(false);
		
		artistService.deleteArtist(artist);
		
		Mockito.verify(artistDAO, Mockito.times(1)).deleteArtist(11);
	}
	
	@Test
	@DisplayName("Test loadUserByUsername Not Found")
	public void testLoadUserByUsernameNotFound() {
		
		Mockito.when(artistDAO.findArtistByEmail("test@example.com")).thenReturn(null);
		
		Exception exception = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			artistService.loadUserByUsername("test@example.com");
		});
		
		Assertions.assertEquals("User not found.", exception.getMessage(), "Wrong exception message");
	}
	
	@Test
	@DisplayName("Test loadUserByUsername Disabled")
	public void testLoadUserByUsernameDisabled() {
		
		Artist artist = new Artist();
		artist.setEmail("test@example.com");
		artist.setIs_enabled(false);
		
		Mockito.when(artistDAO.findArtistByEmail("test@example.com")).thenReturn(artist);
		
		Exception exception = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			artistService.loadUserByUsername("test@example.com");
		});
		
		Assertions.assertEquals("User not found.", exception.getMessage(), "Wrong exception message");
	}
	
	@Test
	@DisplayName("Test loadUserByUsername Success")
	public void testLoadUserByUsername() {
		
		Artist artist = new Artist();
		artist.setEmail("test@example.com");
		artist.setIs_enabled(true);
		artist.setPassword_hash("hashed 123");
		
		Mockito.when(artistDAO.findArtistByEmail("test@example.com")).thenReturn(artist);
		
		UserDetails userDetails = artistService.loadUserByUsername("test@example.com");
		
		Assertions.assertEquals("test@example.com", userDetails.getUsername(), "Email was not set properly");
		String[] expectedAuthorities = {"USER"};
		Assertions.assertEquals(expectedAuthorities[0], userDetails.getAuthorities().toArray()[0].toString());
		Assertions.assertEquals(1, userDetails.getAuthorities().size());
	}
	
	@Test
	@DisplayName("Test processAndSaveImage Success")
	public void testProcessAndSaveImage() throws IOException {
		
		Artist artist = new Artist();
		artist.setHas_image(false);
		
		Mockito.when(imageService.processAndSaveImage(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
		
		artistService.processAndSaveImage(artist, null);
		
		Assertions.assertTrue(artist.isHas_image(), "Has Image was not set");
	}
	
	@Test
	@DisplayName("Test processAndSaveImage Failure")
	public void testProcessAndSaveImageFailure() throws IOException {
		
		Artist artist = new Artist();
		artist.setHas_image(false);
		
		Mockito.when(imageService.processAndSaveImage(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(false);
		
		artistService.processAndSaveImage(artist, null);
		
		Assertions.assertFalse(artist.isHas_image(), "Has Image was set");
	}
}
