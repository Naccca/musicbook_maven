package com.musicbook.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.musicbook.entity.Artist;

@Repository
public class ArtistDAOImpl implements ArtistDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Artist> getArtists() {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Artist> theQuery = currentSession.createQuery("from Artist", Artist.class);
		
		List<Artist> artists = theQuery.getResultList();
		
		return artists;
	}

	@Override
	public void saveArtist(Artist theArtist) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.merge(theArtist);
	}

	@Override
	public Artist getArtist(int theId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Artist theArtist = currentSession.get(Artist.class, theId);
		
		return theArtist;
	}

	@Override
	public void deleteArtist(int theId) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("delete from Artist where id=:artistId");
		
		theQuery.setParameter("artistId", theId);
		
		theQuery.executeUpdate();
		
	}
	
	@Override
	public Artist findArtistByUsername(String username) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Artist> query = currentSession.createQuery("from Artist where username=:username", Artist.class);
		
		query.setParameter("username", username);
		
		return query.uniqueResult(); 
	}
}
