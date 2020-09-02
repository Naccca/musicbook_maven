package com.musicbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicbook.dao.MembershipDAO;
import com.musicbook.entity.Membership;

@Service
public class MembershipServiceImpl implements MembershipService {
	
	@Autowired
	private MembershipDAO membershipDAO;

	private final int STATE_INVITED = 1;
	private final int STATE_ACCEPTED = 2;
	
	@Override
	@Transactional
	public void create(Membership theMembership) {
		
		theMembership.setState_id(STATE_INVITED);
		
		membershipDAO.saveMembership(theMembership);
	}
	
	@Override
	@Transactional
	public void accept(Membership theMembership) {
		
		theMembership.setState_id(STATE_ACCEPTED);
		
		membershipDAO.saveMembership(theMembership);
	}

	@Override
	@Transactional
	public void delete(int theId) {
		
		membershipDAO.deleteMembership(theId);
		
	}
}
