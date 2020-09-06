package com.musicbook.service;

import java.util.List;

import com.musicbook.entity.Membership;
import com.musicbook.form.CreateMembershipForm;

public interface MembershipService {

	public List<Membership> getMembershipsByArtistId(int id);
	
	public void create(CreateMembershipForm createMembershipForm);
	
	public void accept(Membership membership);

	public void delete(int id);

	public List<Membership> getMembershipsByBandId(int bandId);
}
