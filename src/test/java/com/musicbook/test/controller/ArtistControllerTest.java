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
import com.musicbook.controller.ArtistsController;
import com.musicbook.dao.ArtistDAO;
import com.musicbook.entity.Artist;
import com.musicbook.entity.Membership;
import com.musicbook.form.CreateArtistForm;
import com.musicbook.service.ArtistService;
import com.musicbook.service.MembershipService;
import com.musicbook.test.config.TestConfig;

@SpringJUnitWebConfig({MusicbookConfig.class, TestConfig.class})
@ExtendWith(MockitoExtension.class)
@Transactional
public class ArtistControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private ArtistDAO artistDAO;
	
	@Autowired
	private MembershipService membershipService;
	
	@Mock
	private Principal principal;
	
	@InjectMocks
	ArtistsController artistsController;
	
	@Test
	@DisplayName("Test index")
	@SuppressWarnings(value="unchecked")
	public void testIndex() throws Exception {
		
		List<Artist> artists = artistService.getArtists();
		
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/artists"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		List<Artist> returnedArtists = (List<Artist>)result.getModelAndView().getModel().get("artists");
		
		Assertions.assertEquals(artists.size(), returnedArtists.size());
		for (int i = 0; i < artists.size(); i++) {
			Assertions.assertEquals(artists.get(i).getId(), returnedArtists.get(i).getId());
			Assertions.assertEquals(artists.get(i).getEmail(), returnedArtists.get(i).getEmail());
			Assertions.assertEquals(artists.get(i).getName(), returnedArtists.get(i).getName());
			Assertions.assertEquals(artists.get(i).getBio(), returnedArtists.get(i).getBio());
			Assertions.assertEquals(artists.get(i).getLocation(), returnedArtists.get(i).getLocation());
			Assertions.assertEquals(artists.get(i).getInstruments(), returnedArtists.get(i).getInstruments());
			Assertions.assertEquals(artists.get(i).isHas_image(), returnedArtists.get(i).isHas_image());
		}
	}
	
	@Test
	@DisplayName("Test search-page")
	@SuppressWarnings(value="unchecked")
	public void testSearchPage() throws Exception {
		
		List<Artist> artists = artistService.searchArtists("Anthony");
		
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/artists/search-page?search=Anthony"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		List<Artist> returnedArtists = (List<Artist>)result.getModelAndView().getModel().get("artists");
		
		Assertions.assertEquals(artists.size(), returnedArtists.size());
		for (int i = 0; i < artists.size(); i++) {
			Assertions.assertEquals(artists.get(i).getId(), returnedArtists.get(i).getId());
			Assertions.assertEquals(artists.get(i).getEmail(), returnedArtists.get(i).getEmail());
			Assertions.assertEquals(artists.get(i).getName(), returnedArtists.get(i).getName());
			Assertions.assertEquals(artists.get(i).getBio(), returnedArtists.get(i).getBio());
			Assertions.assertEquals(artists.get(i).getLocation(), returnedArtists.get(i).getLocation());
			Assertions.assertEquals(artists.get(i).getInstruments(), returnedArtists.get(i).getInstruments());
			Assertions.assertEquals(artists.get(i).isHas_image(), returnedArtists.get(i).isHas_image());
		}
	}
	
	@Test
	@DisplayName("Test search")
	public void testSearch() throws Exception {
		
		mockMvc
			.perform(MockMvcRequestBuilders.get("/artists/search?search=Anthony"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json("[{'name':'Anthony Kiedis'}]"));
	}
	
	@Test
	@DisplayName("Test show")
	@SuppressWarnings(value="unchecked")
	public void testShow() throws Exception {
		
		Artist artist = artistService.getArtist(1);
		List<Membership> allMemberships = membershipService.getMembershipsByArtistId(artist.getId());
		Map<Integer, List<Membership>> membershipsByStateId = allMemberships.stream().collect(Collectors.groupingBy(Membership::getState_id));
		List<Membership> invites = membershipsByStateId.get(Membership.STATE_INVITED);
		List<Membership> memberships = membershipsByStateId.get(Membership.STATE_ACCEPTED);
		
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/artists/show?artistId=" + artist.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		Artist returnedArtist = (Artist)result.getModelAndView().getModel().get("artist");
		List<Membership> returnedInvites = (List<Membership>)result.getModelAndView().getModel().get("invites");
		List<Membership> returnedMemberships = (List<Membership>)result.getModelAndView().getModel().get("memberships");

		Assertions.assertEquals(artist.getId(), returnedArtist.getId());
		Assertions.assertEquals(artist.getEmail(), returnedArtist.getEmail());
		Assertions.assertEquals(artist.getName(), returnedArtist.getName());
		Assertions.assertEquals(artist.getBio(), returnedArtist.getBio());
		Assertions.assertEquals(artist.getLocation(), returnedArtist.getLocation());
		Assertions.assertEquals(artist.getInstruments(), returnedArtist.getInstruments());
		Assertions.assertEquals(artist.isHas_image(), returnedArtist.isHas_image());
		Assertions.assertEquals(artist.getCreated_at(), returnedArtist.getCreated_at());
		Assertions.assertEquals(artist.getUpdated_at(), returnedArtist.getUpdated_at());
		
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
	}
	
	@Test
	@DisplayName("Test my-profile")
	public void testMyProfile() throws Exception {
		
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		mockMvc
			.perform(MockMvcRequestBuilders.get("/artists/my-profile").principal(principal))
			.andExpect(MockMvcResultMatchers.redirectedUrl("show?artistId=" + artist.getId()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
	
	@Test
	@DisplayName("Test new")
	public void testNew() throws Exception {
		
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/artists/new"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		CreateArtistForm createArtistForm = (CreateArtistForm)result.getModelAndView().getModel().get("artist");
		
		Assertions.assertNull(createArtistForm.getEmail());
		Assertions.assertNull(createArtistForm.getPassword());
		Assertions.assertNull(createArtistForm.getName());
		Assertions.assertNull(createArtistForm.getLocation());
		Assertions.assertNull(createArtistForm.getBio());
		Assertions.assertNull(createArtistForm.getInstruments());
	}
	
	@Test
	@DisplayName("Test create")
	public void testCreate() throws Exception {
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.post("/artists/create")
				.param("email", "testemail@example.com")
				.param("name", "Test Artist")
				.param("password", "123")
				.param("location", "Test Location")
				.param("bio", "Test Bio")
				.param("instruments", "Test Instruments")
			)
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		Artist createdArtist = artistService.getArtistByEmail("testemail@example.com");
		Assertions.assertEquals("Test Artist", createdArtist.getName());
		Assertions.assertEquals("Test Location", createdArtist.getLocation());
		Assertions.assertEquals("Test Bio", createdArtist.getBio());
		Assertions.assertEquals("Test Instruments", createdArtist.getInstruments());
	}
	
	@Test
	@DisplayName("Test edit")
	public void testEdit() throws Exception {
		
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		MvcResult result = mockMvc
			.perform(MockMvcRequestBuilders.get("/artists/edit?artistId=" + artist.getId()).principal(principal))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		Artist editArtist = (Artist)result.getModelAndView().getModel().get("artist");
		
		Assertions.assertEquals(artist.getName(), editArtist.getName());
		Assertions.assertEquals(artist.getLocation(), editArtist.getLocation());
		Assertions.assertEquals(artist.getBio(), editArtist.getBio());
		Assertions.assertEquals(artist.getInstruments(), editArtist.getInstruments());
	}
	
	@Test
	@DisplayName("Test update")
	public void testUpdate() throws Exception {
		
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.post("/artists/update")
				.principal(principal)
				.param("id", Integer.toString(artist.getId()))
				.param("name", "Updated Artist")
				.param("location", "Updated Location")
				.param("bio", "Updated Bio")
				.param("instruments", "Updated Instruments")
			)
			.andExpect(MockMvcResultMatchers.redirectedUrl("/artists/show?artistId=" + artist.getId()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Artist updatedArtist = artistService.getArtist(1);
		Assertions.assertEquals("Updated Artist", updatedArtist.getName());
		Assertions.assertEquals("Updated Location", updatedArtist.getLocation());
		Assertions.assertEquals("Updated Bio", updatedArtist.getBio());
		Assertions.assertEquals("Updated Instruments", updatedArtist.getInstruments());
	}
	
	@Test
	@DisplayName("Test verify")
	public void testVerify() throws Exception {
		
		Artist artist = artistService.getArtist(1);
		artist.setIs_enabled(false);
		artistDAO.saveArtist(artist);
		
		mockMvc
			.perform(MockMvcRequestBuilders.get("/artists/verify?token=" + artist.getToken()))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Artist verifiedArtist = artistService.getArtist(1);
		Assertions.assertTrue(verifiedArtist.isIs_enabled());
	}
	
	@Test
	@DisplayName("Test upload")
	public void testUpload() throws Exception {
		
		Artist artist = artistService.getArtist(1);
		artist.setHas_image(false);
		artistDAO.saveArtist(artist);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		MockMultipartFile file = new MockMultipartFile("file", "", "jpg", "these-are-image-bytes".getBytes());
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.multipart("/artists/upload")
				.file(file)
				.principal(principal)
				.param("artistId", Integer.toString(artist.getId()))
			)
			.andExpect(MockMvcResultMatchers.redirectedUrl("/artists/show?artistId=" + artist.getId()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Artist updatedArtist = artistService.getArtist(1);
		Assertions.assertTrue(updatedArtist.isHas_image());
	}
	
	@Test
	@DisplayName("Test delete")
	public void testDelete() throws Exception {
		
		Artist artist = artistService.getArtist(1);
		Mockito.when(principal.getName()).thenReturn(artist.getEmail());
		
		int artistCount = artistService.getArtists().size();
		
		mockMvc
			.perform(MockMvcRequestBuilders
				.post("/artists/delete")
				.principal(principal)
				.param("id", Integer.toString(artist.getId()))
			)
			.andExpect(MockMvcResultMatchers.redirectedUrl("/logout"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		
		Assertions.assertEquals(artistCount - 1, artistService.getArtists().size());
	}
}
