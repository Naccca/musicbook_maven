package com.musicbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.musicbook.entity.Membership;
import com.musicbook.service.MembershipService;

@Controller
@RequestMapping("/memberships")
public class MembershipsController {
	
	@Autowired
	private MembershipService membershipService;
	
	@PostMapping("/create")
	public String create(@ModelAttribute("membership") Membership theMembership) {
		
		membershipService.create(theMembership);
		return "redirect:/memberships"; // todo
	}
	
	@PostMapping("/accept")
	public String accept(@ModelAttribute("membership") Membership theMembership) {
		
		membershipService.accept(theMembership);
		return "redirect:/memberships"; // todo
	}
	
	@PostMapping("/delete")
	public String delete(@ModelAttribute("membership") Membership theMembership) {
		
		membershipService.delete(theMembership.getId());
		
		return "redirect:/memberships"; //todo
	}
}
