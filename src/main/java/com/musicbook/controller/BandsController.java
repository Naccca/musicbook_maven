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
import com.musicbook.form.CreateBandForm;
import com.musicbook.form.CreateMembershipForm;
import com.musicbook.form.DeleteBandForm;
import com.musicbook.form.UpdateBandForm;
import com.musicbook.service.ArtistService;
import com.musicbook.service.BandService;
import com.musicbook.service.MembershipService;

@Controller
@RequestMapping("/bands")
public class BandsController {
	
	@Autowired
	private BandService bandService;
	
	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private MembershipService membershipService;
	
	@GetMapping("")
	public String index(Model model) {
		
		List<Band> bands = bandService.getBands();
		
		model.addAttribute("bands", bands);
		
		return "bands/index";
	}
	
	@GetMapping("/show")
	public String show(@RequestParam("bandId") int id, Model model) {
		
		Band band = bandService.getBand(id);
		List<Membership> memberships = membershipService.getMembershipsByBandId(band.getId());
		Map<Integer, List<Membership>> membershipsByStateId = memberships.stream().collect(Collectors.groupingBy(Membership::getState_id));
		CreateMembershipForm createMembershipForm = new CreateMembershipForm();
		createMembershipForm.setBand_id(band.getId());
		
		model.addAttribute("band", band);
		model.addAttribute("invites", membershipsByStateId.get(Membership.STATE_INVITED));
		model.addAttribute("memberships", membershipsByStateId.get(Membership.STATE_ACCEPTED));
		model.addAttribute("createMembershipForm", createMembershipForm);
		
		return "bands/show";
	}
	
	@GetMapping("/new")
	public String newForm(Model model, Principal principal) {
		
		CreateBandForm band = new CreateBandForm();
		Artist owner = artistService.getArtistByEmail(principal.getName());
		band.setOwner_id(owner.getId());
		
		model.addAttribute("band", band);
		
		return "bands/new_form";
	}
	
	@PostMapping("/create")
	public String createBand(@Valid @ModelAttribute("band") CreateBandForm band, BindingResult bindingResult, Principal principal) {
		
		Artist owner = artistService.getArtist(band.getOwner_id());
		if (!owner.getEmail().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		if (bindingResult.hasErrors()) {
			return "bands/new_form";
		}
		else {
			bandService.createBand(band);
			return "redirect:/bands";
		}
	}
	
	@PostMapping("/update")
	public String updateBand(@Valid @ModelAttribute("band") UpdateBandForm updateBandForm, BindingResult bindingResult, Principal principal) {
		
		Band band = bandService.getBand(updateBandForm.getId());
		if (!band.getOwner().getEmail().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		if (bindingResult.hasErrors()) {
			return "bands/edit_form";
		}
		else {
			bandService.updateBand(updateBandForm);
			return "redirect:/bands/show?bandId=" + band.getId();
		}
	}
	
	@GetMapping("/edit")
	public String editForm(@RequestParam("bandId") int id, Model model, Principal principal) {
		
		Band band = bandService.getBand(id);
		if (!band.getOwner().getEmail().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		model.addAttribute("band", band);
		
		return "/bands/edit_form";
	}
	
	@PostMapping("/delete")
	public String deleteBand(@ModelAttribute("band") DeleteBandForm deleteBandForm, Principal principal) {
		
		Band band = bandService.getBand(deleteBandForm.getId());
		if (!band.getOwner().getEmail().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		bandService.deleteBand(band);
		
		return "redirect:/bands";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("search") String search, Model model) {
		
		List<Band> bands = bandService.searchBands(search);
		
		model.addAttribute("bands", bands);
		
		return "bands/index";
	}
	
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file, @RequestParam("bandId") int bandId, Principal principal) throws IOException {
		
		Band band = bandService.getBand(bandId);
		if (!band.getOwner().getEmail().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		bandService.processAndSaveImage(band, file);
		
		return "redirect:/bands/show?bandId=" + band.getId();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
}
