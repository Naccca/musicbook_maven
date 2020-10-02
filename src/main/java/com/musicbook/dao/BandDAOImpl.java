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
		
		Query<Band> query = currentSession.createQuery("from Band", Band.class);
		
		List<Band> bands = query.getResultList();
		
		return bands;
	}
	
	@Override
	public List<Band> getBandsByOwnerId(int id) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Band> query = currentSession.createQuery("from Band where owner_id=:id", Band.class);
		
		query.setParameter("id", id);
		
		List<Band> bands = query.getResultList();
		
		return bands;
	}

	@Override
	public Band saveBand(Band band) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		return (Band)currentSession.merge(band);
	}

	@Override
	public Band getBand(int id) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Band band = currentSession.get(Band.class, id);
		
		return band;
	}

	@Override
	public void deleteBand(int id) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query query = currentSession.createQuery("delete from Band where id=:bandId");
		
		query.setParameter("bandId", id);
		
		query.executeUpdate();
		
	}

	@Override
	public List<Band> searchBands(String search) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Band> query = currentSession.createQuery("from Band b where upper(b.name) like upper(:search)", Band.class);
		
		query.setParameter("search", "%" + search + "%");
		
		List<Band> bands = query.getResultList();
		
		return bands;
	}

	@Override
	public Band getBandByName(String name) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Band> query = currentSession.createQuery("from Band where name=:name", Band.class);
		
		query.setParameter("name", name);
		
		return query.uniqueResult(); 
	}

}
