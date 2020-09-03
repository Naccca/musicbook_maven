package com.musicbook.service;

import java.util.List;

import com.musicbook.entity.Membership;

public interface MembershipService {

	public List<Membership> getMembershipsByArtistId(int id);
	
	public void create(Membership theMembership);
	
	public void accept(Membership theMembership);

	public void delete(int theId);
}
