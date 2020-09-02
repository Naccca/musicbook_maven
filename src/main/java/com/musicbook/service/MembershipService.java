package com.musicbook.service;

import com.musicbook.entity.Membership;

public interface MembershipService {

	public void create(Membership theMembership);
	
	public void accept(Membership theMembership);

	public void delete(int theId);
}
