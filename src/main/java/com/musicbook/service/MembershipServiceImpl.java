package com.musicbook.service;

import java.sql.Timestamp;
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
	public Membership create(CreateMembershipForm createMembershipForm) {
		
		Band band = bandDAO.getBand(createMembershipForm.getBand_id());
		Artist artist = artistDAO.findArtistByName(createMembershipForm.getArtist_name());
		if (artist == null) {
			return null;
		}
		
		if(membershipDAO.findMembership(band.getId(), artist.getId()) != null) {
			return null;
		}
		
		Membership membership = new Membership();
		membership.setArtist(artist);
		membership.setBand(band);
		membership.setState_id(Membership.STATE_INVITED);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		membership.setCreated_at(timestamp);
		membership.setUpdated_at(timestamp);
		
		return membershipDAO.saveMembership(membership);
	}
	
	@Override
	@Transactional
	public Membership create(Artist artist, Band band, int state_id) {
		
		Membership membership = new Membership();
		membership.setArtist(artist);
		membership.setBand(band);
		membership.setState_id(state_id);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		membership.setCreated_at(timestamp);
		membership.setUpdated_at(timestamp);
		
		return membershipDAO.saveMembership(membership);
	}
	
	@Override
	@Transactional
	public Membership accept(Membership membership) {
		
		membership.setState_id(Membership.STATE_ACCEPTED);
		
		return membershipDAO.saveMembership(membership);
	}

	@Override
	@Transactional
	public void delete(Membership membership) {
		
		if(membership.getBand().getOwner().getId() == membership.getArtist().getId()) {
			return;
		}
		
		membershipDAO.deleteMembership(membership.getId());
	}

	@Override
	@Transactional
	public List<Membership> getMembershipsByBandId(int bandId) {
		return membershipDAO.getMembershipsByBandId(bandId);
	}
	
	@Override
	@Transactional
	public Membership getMembership(int id) {
		return membershipDAO.getMembership(id);
	}
	
	@Override
	@Transactional
	public Membership getMembership(int artistId, int bandId) {
		
		return membershipDAO.findMembership(bandId, artistId);
	}
	
	@Override
	@Transactional
	public List<Membership> getMemberships() {
		
		return membershipDAO.getMemberships();
	}
}
