package com.musicbook.dao;

import com.musicbook.entity.Membership;

public interface MembershipDAO {

	public void saveMembership(Membership theMembership);

	public void deleteMembership(int theId);
}
