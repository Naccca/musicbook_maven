package com.musicbook.test.controller;

import java.security.Principal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.musicbook.config.MusicbookConfig;
import com.musicbook.controller.MembershipsController;
import com.musicbook.entity.Artist;
import com.musicbook.entity.Band;
import com.musicbook.entity.Membership;
import com.musicbook.form.CreateMembershipForm;
import com.musicbook.service.ArtistService;
import com.musicbook.service.BandService;
import com.musicbook.service.MembershipService;
import com.musicbook.test.config.TestConfig;

@SpringJUnitWebConfig({MusicbookConfig.class, TestConfig.class})
@ExtendWith(MockitoExtension.class)
@Transactional
public class MembershipControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BandService bandService;
	
	@Autowired
	private ArtistService artistService;
		
	@Autowired
	private MembershipService membershipService;
	
	@Mock
	private Principal principal;
	
	@InjectMocks
	MembershipsController membershipsController;
	
	@Test
	@DisplayName("Test create")
	public void testCreate() throws Exception {
				
		Band band = bandService.getBand(1);
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.post("/memberships/create")
				.principal(principal)
				.param("band_id", Integer.toString(band.getId()))
				.param("artist_name", "Test Name")
			)
			.andExpect(MockMvcResultMatchers.redirectedUrl("/bands/show?bandId=" + band.getId()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Membership createdMembership = membershipService.getMembership(artist.getId(), band.getId());
		
		Assertions.assertEquals(artist.getId(), createdMembership.getArtist().getId());
		Assertions.assertEquals(band.getId(), createdMembership.getBand().getId());
	}
	
	@Test
	@DisplayName("Test accept")
	public void testAccept() throws Exception {
		
		Membership membership = membershipService.getMembership(1);
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.post("/memberships/accept")
				.principal(principal)
				.param("id", Integer.toString(membership.getId()))
		)
			.andExpect(MockMvcResultMatchers.redirectedUrl("/artists/show?artistId=" + artist.getId()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Membership acceptedMembership = membershipService.getMembership(1);
		
		Assertions.assertEquals(Membership.STATE_ACCEPTED, acceptedMembership.getState_id());	
	}
	
	@Test
	@DisplayName("Test delete")
	public void testDelete() throws Exception {
		
		Membership membership = membershipService.getMembership(2);
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		int membershipCount = membershipService.getMemberships().size();
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.post("/memberships/delete")
				.principal(principal)
				.param("id", Integer.toString(membership.getId()))
			)
			.andExpect(MockMvcResultMatchers.redirectedUrl("/artists/show?artistId=2"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Assertions.assertEquals(membershipCount - 1, membershipService.getMemberships().size());
	}
}
