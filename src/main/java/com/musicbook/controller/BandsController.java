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

import com.musicbook.entity.Band;
import com.musicbook.service.BandService;

@Controller
@RequestMapping("/bands")
public class BandsController {
	
	@Autowired
	private BandService bandService;
	
	@GetMapping("")
	public String index( Model theModel) {
		
		List<Band> theBands = bandService.getBands();
		
		theModel.addAttribute("bands", theBands);
		
		return "bands/index";
	}
	
	@GetMapping("/show")
	public String show(@RequestParam("bandId") int theId, Model theModel) {
		
		Band theBand = bandService.getBand(theId);
		
		theModel.addAttribute("band", theBand);
		
		return "bands/show";
	}
	
	@GetMapping("/new")
	public String newForm(Model theModel) {
		
		Band theBand = new Band();
		theModel.addAttribute("band", theBand);
		return "bands/form";
	}
	
	@PostMapping("/saveBand")
	public String saveBand(@Valid @ModelAttribute("band") Band theBand, BindingResult theBindingResult) {
		
		if (theBindingResult.hasErrors()) {
			return "bands/form";
		}
		else {
			bandService.saveBand(theBand);
			return "redirect:/bands";
		}
	}
	
	@GetMapping("/edit")
	public String editForm(@RequestParam("bandId") int theId, Model theModel) {
		
		Band theBand = bandService.getBand(theId);
		
		theModel.addAttribute("band", theBand);
		
		return "/bands/form";
	}
	
	@PostMapping("/delete")
	public String deleteBand(@ModelAttribute("band") Band theBand) {
		
		bandService.deleteBand(theBand.getId());
		
		return "redirect:/bands";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
}
