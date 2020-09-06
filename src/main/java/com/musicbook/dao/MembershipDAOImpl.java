package com.musicbook.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.musicbook.entity.Membership;

@Repository
public class MembershipDAOImpl implements MembershipDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Membership> getMembershipsByArtistId(int id) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Membership> query = currentSession.createQuery("from Membership m join fetch m.band band where artist_id=:id", Membership.class);
		
		query.setParameter("id", id);
		
		List<Membership> memberships = query.getResultList();
		
		return memberships;
	}
	
	@Override
	public void saveMembership(Membership membership) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.merge(membership);
	}

	@Override
	public void deleteMembership(int id) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query query = currentSession.createQuery("delete from Membership where id=:membershipId");
		
		query.setParameter("membershipId", id);
		
		query.executeUpdate();
	}
	
	@Override
	public Membership findMembership(int bandId, int artistId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Membership> query = currentSession.createQuery("from Membership where band_id=:bandId and artist_id=:artistId", Membership.class);
		
		query.setParameter("bandId", bandId);
		query.setParameter("artistId", artistId);
		
		return query.uniqueResult(); 
	}

	@Override
	public List<Membership> getMembershipsByBandId(int bandId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Membership> query = currentSession.createQuery("from Membership m join fetch m.artist artist where band_id=:bandId", Membership.class);
		
		query.setParameter("bandId", bandId);
		
		List<Membership> memberships = query.getResultList();
		
		return memberships;
	}

	@Override
	public Membership getMembership(int id) {
	
		Session currentSession = sessionFactory.getCurrentSession();
		
		Membership membership = currentSession.get(Membership.class, id);
		
		return membership;
	}
}
