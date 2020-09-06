package com.musicbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicbook.dao.ArtistDAO;
import com.musicbook.dao.BandDAO;
import com.musicbook.dao.MembershipDAO;
import com.musicbook.entity.Artist;
import com.musicbook.entity.Band;
import com.musicbook.entity.Membership;
import com.musicbook.form.CreateMembershipForm;

@Service
public class MembershipServiceImpl implements MembershipService {
	
	@Autowired
	private MembershipDAO membershipDAO;
	
	@Autowired
	private ArtistDAO artistDAO;
	
	@Autowired
	private BandDAO bandDAO;

	@Override
	@Transactional
	public List<Membership> getMembershipsByArtistId(int id) {
		
		return membershipDAO.getMembershipsByArtistId(id);
	}
	
	@Override
	@Transactional
	public void create(CreateMembershipForm createMembershipForm) {
		
		Band band = bandDAO.getBand(createMembershipForm.getBand_id());
		Artist artist = artistDAO.findArtistByName(createMembershipForm.getArtist_name());
		if (artist == null) {
			return;
		}
		
		if(membershipDAO.findMembership(band.getId(), artist.getId()) != null) {
			return;
		}
		
		Membership membership = new Membership();
		membership.setArtist(artist);
		membership.setBand(band);
		membership.setState_id(Membership.STATE_INVITED);
		
		membershipDAO.saveMembership(membership);
	}
	
	@Override
	@Transactional
	public void accept(Membership membership) {
		
		membership.setState_id(Membership.STATE_ACCEPTED);
		
		membershipDAO.saveMembership(membership);
	}

	@Override
	@Transactional
	public void delete(int id) {
		
		membershipDAO.deleteMembership(id);
	}

	@Override
	@Transactional
	public List<Membership> getMembershipsByBandId(int bandId) {
		return membershipDAO.getMembershipsByBandId(bandId);
	}
}
