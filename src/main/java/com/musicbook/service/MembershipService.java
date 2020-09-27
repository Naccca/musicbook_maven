package com.musicbook.service;

import java.util.List;

import com.musicbook.entity.Artist;
import com.musicbook.entity.Band;
import com.musicbook.entity.Membership;
import com.musicbook.form.CreateMembershipForm;

public interface MembershipService {

	public List<Membership> getMembershipsByArtistId(int id);
	
	public Membership create(CreateMembershipForm createMembershipForm);
	
	public Membership create(Artist artist, Band band, int state_id);
	
	public Membership accept(Membership membership);

	public void delete(Membership membership);

	public List<Membership> getMembershipsByBandId(int bandId);

	public Membership getMembership(int id);
}
