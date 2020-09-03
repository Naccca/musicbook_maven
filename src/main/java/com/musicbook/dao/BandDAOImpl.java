package com.musicbook.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.musicbook.entity.Band;

@Repository
public class BandDAOImpl implements BandDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Band> getBands() {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Band> theQuery = currentSession.createQuery("from Band", Band.class);
		
		List<Band> bands = theQuery.getResultList();
		
		return bands;
	}
	
	@Override
	public List<Band> getBandsByOwnerId(int id) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Band> theQuery = currentSession.createQuery("from Band where owner_id=:id", Band.class);
		
		theQuery.setParameter("id", id);
		
		List<Band> bands = theQuery.getResultList();
		
		return bands;
	}

	@Override
	public Band saveBand(Band theBand) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		return (Band)currentSession.merge(theBand);
	}

	@Override
	public Band getBand(int theId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Band theBand = currentSession.get(Band.class, theId);
		
		return theBand;
	}

	@Override
	public void deleteBand(int theId) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("delete from Band where id=:bandId");
		
		theQuery.setParameter("bandId", theId);
		
		theQuery.executeUpdate();
		
	}

}
