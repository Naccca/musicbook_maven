package com.musicbook.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicbook.dao.ArtistDAO;
import com.musicbook.entity.Artist;
import com.musicbook.form.CreateArtistForm;
import com.musicbook.form.DeleteArtistForm;
import com.musicbook.form.UpdateArtistForm;

@Service
public class ArtistServiceImpl implements ArtistService, UserDetailsService {
	
	@Autowired
	private ArtistDAO artistDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public List<Artist> getArtists() {
		
		return artistDAO.getArtists();
	}

	@Override
	@Transactional
	public void createArtist(CreateArtistForm createArtistForm) {
		
		Artist artist = new Artist();
		artist.setUsername(createArtistForm.getUsername());
		artist.setName(createArtistForm.getName());
		artist.setBio(createArtistForm.getBio());
		artist.setLocation(createArtistForm.getLocation());
		artist.setInstruments(createArtistForm.getInstruments());
		artist.setPassword_hash(passwordEncoder.encode(createArtistForm.getPassword()));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		artist.setCreated_at(timestamp);
		artist.setUpdated_at(timestamp);
		artistDAO.saveArtist(artist);
	}
	
	@Override
	@Transactional
	public void updateArtist(UpdateArtistForm updateArtistForm) {
		
		Artist artist = getArtist(updateArtistForm.getId());
		artist.setName(updateArtistForm.getName());
		artist.setBio(updateArtistForm.getBio());
		artist.setLocation(updateArtistForm.getLocation());
		artist.setInstruments(updateArtistForm.getInstruments());
		artist.setUpdated_at(new Timestamp(System.currentTimeMillis()));
		artistDAO.saveArtist(artist);
	}

	@Override
	@Transactional
	public Artist getArtist(int id) {
		
		return artistDAO.getArtist(id);
	}

	@Override
	@Transactional
	public void deleteArtist(DeleteArtistForm artist) {
		
		artistDAO.deleteArtist(artist.getId());
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Artist artist = artistDAO.findArtistByUsername(username);
		UserBuilder builder = null;
		if (artist != null) {
	      
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(artist.getPassword_hash());
			String[] authorities = {"USER"};
			builder.authorities(authorities);
		} else {
	    	throw new UsernameNotFoundException("User not found.");
		}
		return builder.build();
	}
}
