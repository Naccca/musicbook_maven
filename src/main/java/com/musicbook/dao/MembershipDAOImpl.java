package com.musicbook.dao;

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
	public void saveMembership(Membership theMembership) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.merge(theMembership);
	}

	@Override
	public void deleteMembership(int theId) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("delete from Membership where id=:membershipId");
		
		theQuery.setParameter("membershipId", theId);
		
		theQuery.executeUpdate();
		
	}

}
