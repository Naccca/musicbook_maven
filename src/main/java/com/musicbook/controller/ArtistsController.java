package com.musicbook.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
import com.musicbook.service.ArtistService;

@Controller
@RequestMapping("/artists")
public class ArtistsController {
	
	@Autowired
	private ArtistService artistService;
	
	@GetMapping("")
	public String index( Model theModel) {
		
		List<Artist> theArtists = artistService.getArtists();
		
		theModel.addAttribute("artists", theArtists);
		
		return "artists/index";
	}
	
	@GetMapping("/show")
	public String show(@RequestParam("artistId") int theId, Model theModel) {
		
		Artist theArtist = artistService.getArtist(theId);
		
		theModel.addAttribute("artist", theArtist);
		
		return "artists/show";
	}
	
	@GetMapping("/new")
	public String newForm(Model theModel) {
		
		Artist theArtist = new Artist();
		theModel.addAttribute("artist", theArtist);
		return "artists/form";
	}
	
	@PostMapping("/saveArtist")
	public String saveArtist(@Valid @ModelAttribute("artist") Artist theArtist, BindingResult theBindingResult) {
		
		if (theBindingResult.hasErrors()) {
			return "artists/form";
		}
		else {
			artistService.saveArtist(theArtist);
			return "redirect:/artists";
		}
	}
	
	@GetMapping("/edit")
	public String editForm(@RequestParam("artistId") int theId, Model theModel) {
		
		Artist theArtist = artistService.getArtist(theId);
		
		theModel.addAttribute("artist", theArtist);
		
		return "/artists/form";
	}
	
	@PostMapping("/delete")
	public String deleteArtist(@ModelAttribute("artist") Artist theArtist) {
		
		artistService.deleteArtist(theArtist.getId());
		
		return "redirect:/artists";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
}
