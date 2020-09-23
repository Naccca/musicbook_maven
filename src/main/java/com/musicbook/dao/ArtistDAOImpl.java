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
		
		Query<Artist> query = currentSession.createQuery("from Artist", Artist.class);
		
		List<Artist> artists = query.getResultList();
		
		return artists;
	}

	@Override
	public Artist saveArtist(Artist artist) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		return (Artist)currentSession.merge(artist);
	}

	@Override
	public Artist getArtist(int id) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Artist artist = currentSession.get(Artist.class, id);
		
		return artist;
	}

	@Override
	public void deleteArtist(int id) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query query = currentSession.createQuery("delete from Artist where id=:artistId");
		
		query.setParameter("artistId", id);
		
		query.executeUpdate();
	}
	
	@Override
	public Artist findArtistByName(String name) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Artist> query = currentSession.createQuery("from Artist where name=:name", Artist.class);
		
		query.setParameter("name", name);
		
		return query.uniqueResult(); 
	}

	@Override
	public Artist findArtistByEmail(String email) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Artist> query = currentSession.createQuery("from Artist where email=:email", Artist.class);
		
		query.setParameter("email", email);
		
		return query.uniqueResult(); 
	}
	
	@Override
	public Artist findArtistByToken(String token) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Artist> query = currentSession.createQuery("from Artist where token=:token", Artist.class);
		
		query.setParameter("token", token);
		
		return query.uniqueResult();
	}

	@Override
	public List<Artist> searchArtists(String search) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Artist> query = currentSession.createQuery("from Artist a where upper(a.name) like upper(:search)", Artist.class);
		
		query.setParameter("search", "%" + search + "%");
		
		List<Artist> artists = query.getResultList();
		
		return artists;
	}
}
