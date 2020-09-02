package com.musicbook.controller;

import java.security.Principal;
import java.util.List;

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

import com.musicbook.entity.Artist;
import com.musicbook.entity.Band;
import com.musicbook.form.CreateBandForm;
import com.musicbook.form.DeleteBandForm;
import com.musicbook.form.UpdateBandForm;
import com.musicbook.service.ArtistService;
import com.musicbook.service.BandService;

@Controller
@RequestMapping("/bands")
public class BandsController {
	
	@Autowired
	private BandService bandService;
	
	@Autowired
	private ArtistService artistService;
	
	@GetMapping("")
	public String index(Model model) {
		
		List<Band> bands = bandService.getBands();
		
		model.addAttribute("bands", bands);
		
		return "bands/index";
	}
	
	@GetMapping("/show")
	public String show(@RequestParam("bandId") int id, Model model) {
		
		Band band = bandService.getBand(id);
		
		model.addAttribute("band", band);
		
		return "bands/show";
	}
	
	@GetMapping("/new")
	public String newForm(Model model, Principal principal) {
		
		CreateBandForm band = new CreateBandForm();
		Artist owner = artistService.getArtistByUsername(principal.getName());
		band.setOwner_id(owner.getId());
		
		model.addAttribute("band", band);
		
		return "bands/new_form";
	}
	
	@PostMapping("/create")
	public String createBand(@Valid @ModelAttribute("band") CreateBandForm band, BindingResult bindingResult, Principal principal) {
		
		Artist owner = artistService.getArtist(band.getOwner_id());
		if (!owner.getUsername().equals(principal.getName())) {
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
		if (!band.getOwner().getUsername().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		if (bindingResult.hasErrors()) {
			return "bands/edit_form";
		}
		else {
			bandService.updateBand(updateBandForm);
			return "redirect:/bands";
		}
	}
	
	@GetMapping("/edit")
	public String editForm(@RequestParam("bandId") int id, Model model, Principal principal) {
		
		Band band = bandService.getBand(id);
		if (!band.getOwner().getUsername().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		model.addAttribute("band", band);
		
		return "/bands/edit_form";
	}
	
	@PostMapping("/delete")
	public String deleteBand(@ModelAttribute("band") DeleteBandForm deleteBandForm, Principal principal) {
		
		Band band = bandService.getBand(deleteBandForm.getId());
		if (!band.getOwner().getUsername().equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		bandService.deleteBand(deleteBandForm);
		
		return "redirect:/bands";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
}
