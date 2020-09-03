package com.musicbook.dao;

import java.util.List;

import com.musicbook.entity.Membership;

public interface MembershipDAO {

	public List<Membership> getMembershipsByArtistId(int id);
	
	public void saveMembership(Membership membership);

	public void deleteMembership(int id);
}
