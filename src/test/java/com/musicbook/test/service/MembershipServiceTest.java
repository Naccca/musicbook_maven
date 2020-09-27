package com.musicbook.test.service;

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
import com.musicbook.dao.MembershipDAO;
import com.musicbook.entity.Artist;
import com.musicbook.entity.Band;
import com.musicbook.entity.Membership;
import com.musicbook.form.CreateMembershipForm;
import com.musicbook.service.MembershipServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {

	@Mock
	private BandDAO bandDAO;
	
	@Mock
	private ArtistDAO artistDAO;
	
	@Mock
	private MembershipDAO membershipDAO;
	
	@InjectMocks
	private MembershipServiceImpl membershipService;
	
	@Test
	@DisplayName("Test getMembership Success")
	void testGetMembership() {
		
		Membership membership = new Membership();
		membership.setId(11);
		
		Mockito.when(membershipDAO.getMembership(11)).thenReturn(membership);
		
		Assertions.assertEquals(membership, membershipService.getMembership(11), "Returned membership is not the same as mock");
	}
	
	@Test
	@DisplayName("Test getMembership Not Found")
	void testGetMembershipNotFound() {
		
		Mockito.when(membershipDAO.getMembership(11)).thenReturn(null);
		
		Assertions.assertNull(membershipService.getMembership(11), "Membership should not have been found");
	}
	
	@Test
	@DisplayName("Test getMembershipsByArtistId Success")
	void testGetMembershipsByArtistId() {
		
		Membership membership1 = new Membership();
		Membership membership2 = new Membership();
		
		Mockito.when(membershipDAO.getMembershipsByArtistId(11)).thenReturn(Arrays.asList(membership1, membership2));
		
		Assertions.assertEquals(2, membershipService.getMembershipsByArtistId(11).size(), "Wrong number of memberships returned");
	}
	
	@Test
	@DisplayName("Test getMembershipsByBandId Success")
	void testGetMembershipsByBandId() {
		
		Membership membership1 = new Membership();
		Membership membership2 = new Membership();
		
		Mockito.when(membershipDAO.getMembershipsByBandId(11)).thenReturn(Arrays.asList(membership1, membership2));
		
		Assertions.assertEquals(2, membershipService.getMembershipsByBandId(11).size(), "Wrong number of memberships returned");
	}
	
	@Test
	@DisplayName("Test createMemebrship With Form Success")
	void testCreateMembershipWithForm() {
		
		Artist artist = new Artist();
		artist.setName("John Doe");
		Band band = new Band();
		band.setId(11);
		
		CreateMembershipForm createMembershipForm = new CreateMembershipForm();
		createMembershipForm.setArtist_name("John Doe");
		createMembershipForm.setBand_id(11);
		
		Mockito.when(artistDAO.findArtistByName("John Doe")).thenReturn(artist);
		Mockito.when(bandDAO.getBand(11)).thenReturn(band);
		Mockito.when(membershipDAO.saveMembership(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
		
		Membership createdMembership = membershipService.create(createMembershipForm);
		
		Assertions.assertEquals(artist, createdMembership.getArtist(), "Artist was not set properly");
		Assertions.assertEquals(band, createdMembership.getBand(), "Band was not set properly");
		Assertions.assertEquals(Membership.STATE_INVITED, createdMembership.getState_id(), "State was not set properly");
		Assertions.assertNotNull(createdMembership.getCreated_at(), "Created At was not set");
		Assertions.assertNotNull(createdMembership.getUpdated_at(), "Updated At was not set");
		Assertions.assertEquals(createdMembership.getCreated_at(), createdMembership.getUpdated_at(), "Created At and Updated At are different");
	}
	
	@Test
	@DisplayName("Test acceptMembership")
	void testAcceptMembership() {
		
		Membership membership = new Membership();
		membership.setState_id(Membership.STATE_INVITED);
		
		Mockito.when(membershipDAO.saveMembership(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
		
		Membership acceptedMembership = membershipService.accept(membership);
		
		Assertions.assertEquals(Membership.STATE_ACCEPTED, acceptedMembership.getState_id(), "State was not set properly");
	}
	
	@Test
	@DisplayName("Test createMemebrship Success")
	void testCreateMembership() {
		
		Artist artist = new Artist();
		artist.setName("John Doe");
		Band band = new Band();
		band.setId(11);
		
		Mockito.when(membershipDAO.saveMembership(ArgumentMatchers.any())).then(AdditionalAnswers.returnsFirstArg());
		
		Membership createdMembership = membershipService.create(artist, band, Membership.STATE_ACCEPTED);
		
		Assertions.assertEquals(artist, createdMembership.getArtist(), "Artist was not set properly");
		Assertions.assertEquals(band, createdMembership.getBand(), "Band was not set properly");
		Assertions.assertEquals(Membership.STATE_ACCEPTED, createdMembership.getState_id(), "State was not set properly");
		Assertions.assertNotNull(createdMembership.getCreated_at(), "Created At was not set");
		Assertions.assertNotNull(createdMembership.getUpdated_at(), "Updated At was not set");
		Assertions.assertEquals(createdMembership.getCreated_at(), createdMembership.getUpdated_at(), "Created At and Updated At are different");
	}
	
	@Test
	@DisplayName("Test deleteMembership Success")
	public void testDeleteMembership() {
		
		Artist artist = new Artist();
		artist.setId(11);
		Artist artist1 = new Artist();
		artist1.setId(12);
		Band band = new Band();
		band.setOwner(artist);
		Membership membership = new Membership();
		membership.setId(11);
		membership.setArtist(artist1);
		membership.setBand(band);
		
		membershipService.delete(membership);
		
		Mockito.verify(membershipDAO, Mockito.times(1)).deleteMembership(11);
	}
}
