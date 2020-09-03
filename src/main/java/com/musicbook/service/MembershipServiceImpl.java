package com.musicbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicbook.dao.MembershipDAO;
import com.musicbook.entity.Membership;

@Service
public class MembershipServiceImpl implements MembershipService {
	
	@Autowired
	private MembershipDAO membershipDAO;

	@Override
	@Transactional
	public List<Membership> getMembershipsByArtistId(int id) {
		
		return membershipDAO.getMembershipsByArtistId(id);
	}
	
	@Override
	@Transactional
	public void create(Membership theMembership) {
		
		theMembership.setState_id(Membership.STATE_INVITED);
		
		membershipDAO.saveMembership(theMembership);
	}
	
	@Override
	@Transactional
	public void accept(Membership theMembership) {
		
		theMembership.setState_id(Membership.STATE_ACCEPTED);
		
		membershipDAO.saveMembership(theMembership);
	}

	@Override
	@Transactional
	public void delete(int theId) {
		
		membershipDAO.deleteMembership(theId);
		
	}
}
