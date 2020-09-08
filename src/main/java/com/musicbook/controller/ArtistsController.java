package com.musicbook.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.musicbook.entity.Artist;
import com.musicbook.entity.Band;
import com.musicbook.entity.Membership;
import com.musicbook.form.CreateArtistForm;
import com.musicbook.form.DeleteArtistForm;
import com.musicbook.form.UpdateArtistForm;
import com.musicbook.service.ArtistService;
import com.musicbook.service.BandService;
import com.musicbook.service.MembershipService;

@Controller
@RequestMapping("/artists")
public class ArtistsController {
	
	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private BandService bandService;
	
	@Autowired
	private MembershipService membershipService;
	
	@GetMapping("")
	public String index(Model model) {
		
		List<Artist> artists = artistService.getArtists();
		
		model.addAttribute("artists", artists);
		
		return "artists/index";
	}
	
	@GetMapping("/show")
	public String show(@RequestParam("artistId") int id, Model model) {
		
		Artist artist = artistService.getArtist(id);
		List<Band> bands = bandService.getBandsByOwnerId(id);
		List<Membership> memberships = membershipService.getMembershipsByArtistId(id);
		Map<Integer, List<Membership>> membershipsByStateId = memberships.stream().collect(Collectors.groupingBy(Membership::getState_id));
		
		model.addAttribute("artist", artist);
		model.addAttribute("bands", bands);
		model.addAttribute("invites", membershipsByStateId.get(Membership.STATE_INVITED));
		model.addAttribute("memberships", membershipsByStateId.get(Membership.STATE_ACCEPTED));
		
		return "artists/show";
	}
	
	@GetMapping("/new")
	public String newForm(Model model) {
		
		CreateArtistForm artist = new CreateArtistForm();
		
		model.addAttribute("artist", artist);
		
		return "artists/new_form";
	}
	
	@PostMapping("/create")
	public String createArtist(@Valid @ModelAttribute("artist") CreateArtistForm artist, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "artists/new_form";
		}
		else {
			artistService.createArtist(artist);
			return "redirect:/login";
		}
	}
	
	@PostMapping("/update")
	public String updateArtist(@Valid @ModelAttribute("artist") UpdateArtistForm updateArtistForm, BindingResult bindingResult, Principal principal) {
		
		Artist artist = artistService.getArtist(updateArtistForm.getId());
		if (!artist.getUsername().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		if (bindingResult.hasErrors()) {
			return "artists/edit_form";
		}
		else {
			artistService.updateArtist(updateArtistForm);
			return "redirect:/artists";
		}
	}
	
	@GetMapping("/edit")
	public String editForm(@RequestParam("artistId") int id, Model model, Principal principal) {
		
		Artist artist = artistService.getArtist(id);
		if (!artist.getUsername().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		model.addAttribute("artist", artist);
		
		return "/artists/edit_form";
	}
	
	@PostMapping("/delete")
	public String deleteArtist(@ModelAttribute("artist") DeleteArtistForm deleteArtistForm, Principal principal) {
		
		Artist artist = artistService.getArtist(deleteArtistForm.getId());
		if (!artist.getUsername().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		artistService.deleteArtist(artist);
		
		return "redirect:/logout";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("search") String search, Model model) {
		
		List<Artist> artists = artistService.searchArtists(search);
		
		model.addAttribute("artists", artists);
		
		return "artists/index";
	}
	
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file, @RequestParam("artistId") int artistId, Principal principal) throws IOException {
		
		Artist artist = artistService.getArtist(artistId);
		if (!artist.getUsername().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		artistService.processAndSaveImage(artist, file);
		
		return "redirect:/artists/show?artistId=" + artist.getId();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
}
