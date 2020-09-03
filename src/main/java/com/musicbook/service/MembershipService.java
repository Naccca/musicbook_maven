package com.musicbook.service;

import java.util.List;

import com.musicbook.entity.Membership;

public interface MembershipService {

	public List<Membership> getMembershipsByArtistId(int id);
	
	public void create(Membership membership);
	
	public void accept(Membership membership);

	public void delete(int id);
}
