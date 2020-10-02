package com.musicbook.test.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.musicbook.config.MusicbookConfig;
import com.musicbook.controller.BandsController;
import com.musicbook.dao.BandDAO;
import com.musicbook.entity.Artist;
import com.musicbook.entity.Band;
import com.musicbook.entity.Membership;
import com.musicbook.form.CreateBandForm;
import com.musicbook.form.CreateMembershipForm;
import com.musicbook.service.ArtistService;
import com.musicbook.service.BandService;
import com.musicbook.service.MembershipService;
import com.musicbook.test.config.TestConfig;

@SpringJUnitWebConfig({MusicbookConfig.class, TestConfig.class})
@ExtendWith(MockitoExtension.class)
@Transactional
public class BandControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BandService bandService;
	
	@Autowired
	private BandDAO bandDAO;
	
	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private MembershipService membershipService;
	
	@Mock
	private Principal principal;
	
	@InjectMocks
	BandsController bandsController;
	
	@Test
	@DisplayName("Test index")
	@SuppressWarnings(value="unchecked")
	public void testIndex() throws Exception {
		
		List<Band> bands = bandService.getBands();
		
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/bands"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		List<Band> returnedBands = (List<Band>)result.getModelAndView().getModel().get("bands");
		
		Assertions.assertEquals(bands.size(), returnedBands.size());
		for (int i = 0; i < bands.size(); i++) {
			Assertions.assertEquals(bands.get(i).getId(), returnedBands.get(i).getId());
			Assertions.assertEquals(bands.get(i).getOwner(), returnedBands.get(i).getOwner());
			Assertions.assertEquals(bands.get(i).getName(), returnedBands.get(i).getName());
			Assertions.assertEquals(bands.get(i).getBio(), returnedBands.get(i).getBio());
			Assertions.assertEquals(bands.get(i).getLocation(), returnedBands.get(i).getLocation());
			Assertions.assertEquals(bands.get(i).getGenres(), returnedBands.get(i).getGenres());
			Assertions.assertEquals(bands.get(i).isHas_image(), returnedBands.get(i).isHas_image());
		}
	}
	
	@Test
	@DisplayName("Test search")
	@SuppressWarnings(value="unchecked")
	public void testSearch() throws Exception {
		
		List<Band> bands = bandService.searchBands("Tool");
		
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/bands/search?search=Tool"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		List<Band> returnedBands = (List<Band>)result.getModelAndView().getModel().get("bands");
		
		Assertions.assertEquals(bands.size(), returnedBands.size());
		for (int i = 0; i < bands.size(); i++) {
			Assertions.assertEquals(bands.get(i).getId(), returnedBands.get(i).getId());
			Assertions.assertEquals(bands.get(i).getOwner(), returnedBands.get(i).getOwner());
			Assertions.assertEquals(bands.get(i).getName(), returnedBands.get(i).getName());
			Assertions.assertEquals(bands.get(i).getBio(), returnedBands.get(i).getBio());
			Assertions.assertEquals(bands.get(i).getLocation(), returnedBands.get(i).getLocation());
			Assertions.assertEquals(bands.get(i).getGenres(), returnedBands.get(i).getGenres());
			Assertions.assertEquals(bands.get(i).isHas_image(), returnedBands.get(i).isHas_image());
		}
	}
	
	@Test
	@DisplayName("Test show")
	@SuppressWarnings(value="unchecked")
	public void testShow() throws Exception {
		
		Band band = bandService.getBand(1);
		List<Membership> allMemberships = membershipService.getMembershipsByBandId(band.getId());
		Map<Integer, List<Membership>> membershipsByStateId = allMemberships.stream().collect(Collectors.groupingBy(Membership::getState_id));
		List<Membership> invites = membershipsByStateId.get(Membership.STATE_INVITED);
		List<Membership> memberships = membershipsByStateId.get(Membership.STATE_ACCEPTED);
		
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/bands/show?bandId=" + band.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		Band returnedBand = (Band)result.getModelAndView().getModel().get("band");
		List<Membership> returnedInvites = (List<Membership>)result.getModelAndView().getModel().get("invites");
		List<Membership> returnedMemberships = (List<Membership>)result.getModelAndView().getModel().get("memberships");
		CreateMembershipForm createMembershipForm = (CreateMembershipForm)result.getModelAndView().getModel().get("createMembershipForm");
		
		Assertions.assertEquals(band.getId(), returnedBand.getId());
		Assertions.assertEquals(band.getOwner(), returnedBand.getOwner());
		Assertions.assertEquals(band.getName(), returnedBand.getName());
		Assertions.assertEquals(band.getBio(), returnedBand.getBio());
		Assertions.assertEquals(band.getLocation(), returnedBand.getLocation());
		Assertions.assertEquals(band.getGenres(), returnedBand.getGenres());
		Assertions.assertEquals(band.isHas_image(), returnedBand.isHas_image());
		Assertions.assertEquals(band.getCreated_at(), returnedBand.getCreated_at());
		Assertions.assertEquals(band.getUpdated_at(), returnedBand.getUpdated_at());
		
		if (invites != null) {
			Assertions.assertEquals(invites.size(), returnedInvites.size());
			for (int i = 0; i < invites.size(); i++) {
				Assertions.assertEquals(invites.get(i).getId(), returnedInvites.get(i).getId());
				Assertions.assertEquals(invites.get(i).getArtist().getId(), returnedInvites.get(i).getArtist().getId());
				Assertions.assertEquals(invites.get(i).getBand().getId(), returnedInvites.get(i).getBand().getId());
				Assertions.assertEquals(invites.get(i).getState_id(), returnedInvites.get(i).getState_id());
				Assertions.assertEquals(invites.get(i).getCreated_at(), returnedInvites.get(i).getCreated_at());
				Assertions.assertEquals(invites.get(i).getUpdated_at(), returnedInvites.get(i).getUpdated_at());
			}
		}
		
		if (memberships != null) {
			Assertions.assertEquals(memberships.size(), returnedMemberships.size());
			for (int i = 0; i < memberships.size(); i++) {
				Assertions.assertEquals(memberships.get(i).getId(), returnedMemberships.get(i).getId());
				Assertions.assertEquals(memberships.get(i).getArtist().getId(), returnedMemberships.get(i).getArtist().getId());
				Assertions.assertEquals(memberships.get(i).getBand().getId(), returnedMemberships.get(i).getBand().getId());
				Assertions.assertEquals(memberships.get(i).getState_id(), returnedMemberships.get(i).getState_id());
				Assertions.assertEquals(memberships.get(i).getCreated_at(), returnedMemberships.get(i).getCreated_at());
				Assertions.assertEquals(memberships.get(i).getUpdated_at(), returnedMemberships.get(i).getUpdated_at());
			}
		}
		
		Assertions.assertNull(createMembershipForm.getArtist_name());
		Assertions.assertEquals(band.getId(), createMembershipForm.getBand_id());
	}
	
	@Test
	@DisplayName("Test new")
	public void testNew() throws Exception {
		
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/bands/new").principal(principal))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		CreateBandForm createBandForm = (CreateBandForm)result.getModelAndView().getModel().get("band");
		
		Assertions.assertEquals(artist.getId(), createBandForm.getOwner_id());
		Assertions.assertNull(createBandForm.getName());
		Assertions.assertNull(createBandForm.getLocation());
		Assertions.assertNull(createBandForm.getBio());
		Assertions.assertNull(createBandForm.getGenres());
	}
	
	@Test
	@DisplayName("Test create")
	public void testCreate() throws Exception {
				
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.post("/bands/create")
				.principal(principal)
				.param("name", "Test Band")
				.param("location", "Test Location")
				.param("bio", "Test Bio")
				.param("genres", "Test Genres")
				.param("owner_id", Integer.toString(artist.getId()))
			)
			.andExpect(MockMvcResultMatchers.redirectedUrl("/bands"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Band createdBand = bandService.getBandByName("Test Band");
		
		Assertions.assertEquals(artist, createdBand.getOwner());
		Assertions.assertEquals("Test Band", createdBand .getName());
		Assertions.assertEquals("Test Location", createdBand .getLocation());
		Assertions.assertEquals("Test Bio", createdBand .getBio());
		Assertions.assertEquals("Test Genres", createdBand .getGenres());
	}
	
	@Test
	@DisplayName("Test edit")
	public void testEdit() throws Exception {
		
		Band band = bandService.getBand(1);
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/bands/edit?bandId=" + band.getId()).principal(principal))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		Band editBand = (Band)result.getModelAndView().getModel().get("band");
		
		Assertions.assertEquals(band.getName(), editBand.getName());
		Assertions.assertEquals(band.getLocation(), editBand.getLocation());
		Assertions.assertEquals(band.getBio(), editBand.getBio());
		Assertions.assertEquals(band.getGenres(), editBand.getGenres());
	}
	
	@Test
	@DisplayName("Test update")
	public void testUpdate() throws Exception {
		
		Band band = bandService.getBand(1);
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.post("/bands/update")
				.principal(principal)
				.param("id", Integer.toString(band.getId()))
				.param("name", "Updated Band")
				.param("location", "Updated Location")
				.param("bio", "Updated Bio")
				.param("genres", "Updated Genres")
			)
			.andExpect(MockMvcResultMatchers.redirectedUrl("/bands/show?bandId=" + band.getId()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Band updatedBand = bandService.getBand(1);
		Assertions.assertEquals("Updated Band", updatedBand.getName());
		Assertions.assertEquals("Updated Location", updatedBand.getLocation());
		Assertions.assertEquals("Updated Bio", updatedBand.getBio());
		Assertions.assertEquals("Updated Genres", updatedBand.getGenres());
	}
	
	@Test
	@DisplayName("Test upload")
	public void testUpload() throws Exception {
		
		Band band = bandService.getBand(1);
		band.setHas_image(false);
		bandDAO.saveBand(band);
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		MockMultipartFile file = new MockMultipartFile("file", "", "jpg", "these-are-image-bytes".getBytes());
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.multipart("/bands/upload")
				.file(file)
				.principal(principal)
				.param("bandId", Integer.toString(band.getId()))
			)
			.andExpect(MockMvcResultMatchers.redirectedUrl("/bands/show?bandId=" + band.getId()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Band updatedBand = bandService.getBand(1);
		Assertions.assertTrue(updatedBand.isHas_image());
	}
	
	@Test
	@DisplayName("Test delete")
	public void testDelete() throws Exception {
		
		Band band = bandService.getBand(1);
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		int bandCount = bandService.getBands().size();
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.post("/bands/delete")
				.principal(principal)
				.param("id", Integer.toString(band.getId()))
			)
			.andExpect(MockMvcResultMatchers.redirectedUrl("/bands"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Assertions.assertEquals(bandCount - 1, bandService.getBands().size());
	}
}
