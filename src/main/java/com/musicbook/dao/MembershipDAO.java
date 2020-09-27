package com.musicbook.dao;

import java.util.List;

import com.musicbook.entity.Membership;

public interface MembershipDAO {

	public List<Membership> getMembershipsByArtistId(int id);
	
	public Membership saveMembership(Membership membership);

	public void deleteMembership(int id);

	public Membership findMembership(int bandId, int artistId);

	public List<Membership> getMembershipsByBandId(int bandId);

	public Membership getMembership(int id);
}
