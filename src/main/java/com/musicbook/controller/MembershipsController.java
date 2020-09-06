package com.musicbook.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.musicbook.entity.Band;
import com.musicbook.entity.Membership;
import com.musicbook.form.CreateMembershipForm;
import com.musicbook.service.BandService;
import com.musicbook.service.MembershipService;

@Controller
@RequestMapping("/memberships")
public class MembershipsController {
	
	@Autowired
	private MembershipService membershipService;
	
	@Autowired
	private BandService bandService;
	
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("membership") CreateMembershipForm createMembershipForm, BindingResult bindingResult, Principal principal) {
		
		Band band = bandService.getBand(createMembershipForm.getBand_id());
		String artistUsername = band.getOwner().getUsername();
		if(!artistUsername.equals(principal.getName())) {
			throw new AccessDeniedException("Forbidden");
		}
		
		if (bindingResult.hasErrors()) {
			return "bands/show";
		}
		else {
			membershipService.create(createMembershipForm);
			return "redirect:/bands/show?bandId=" + band.getId();
		}
	}
	
	@PostMapping("/accept")
	public String accept(@ModelAttribute("membership") Membership membership) {
		
		membershipService.accept(membership);
		return "redirect:/memberships"; // todo
	}
	
	@PostMapping("/delete")
	public String delete(@ModelAttribute("membership") Membership membership) {
		
		membershipService.delete(membership.getId());
		
		return "redirect:/memberships"; //todo
	}
}
