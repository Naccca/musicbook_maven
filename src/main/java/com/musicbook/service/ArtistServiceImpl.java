package com.musicbook.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.musicbook.dao.ArtistDAO;
import com.musicbook.entity.Artist;
import com.musicbook.form.CreateArtistForm;
import com.musicbook.form.UpdateArtistForm;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
@PropertySource("classpath:file-upload.properties")
public class ArtistServiceImpl implements ArtistService, UserDetailsService {
	
	@Autowired
	private ArtistDAO artistDAO;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private Environment env;

	@Override
	@Transactional
	public List<Artist> getArtists() {
		
		return artistDAO.getArtists();
	}

	@Override
	@Transactional
	public Artist createArtist(CreateArtistForm createArtistForm) {
		
		Artist artist = new Artist();
		artist.setEmail(createArtistForm.getEmail());
		artist.setName(createArtistForm.getName());
		artist.setBio(createArtistForm.getBio());
		artist.setLocation(createArtistForm.getLocation());
		artist.setInstruments(createArtistForm.getInstruments());
		artist.setPassword_hash(passwordEncoder.encode(createArtistForm.getPassword()));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		artist.setCreated_at(timestamp);
		artist.setUpdated_at(timestamp);
		artist.setHas_image(false);
		artist.setIs_enabled(false);
		artist.setToken(UUID.randomUUID().toString());
		Artist createdArtist = artistDAO.saveArtist(artist);
		emailService.sendVerificationEmail(createdArtist);
		return createdArtist;
	}
	
	@Override
	@Transactional
	public Artist updateArtist(UpdateArtistForm updateArtistForm) {
		
		Artist artist = getArtist(updateArtistForm.getId());
		artist.setName(updateArtistForm.getName());
		artist.setBio(updateArtistForm.getBio());
		artist.setLocation(updateArtistForm.getLocation());
		artist.setInstruments(updateArtistForm.getInstruments());
		artist.setUpdated_at(new Timestamp(System.currentTimeMillis()));
		return artistDAO.saveArtist(artist);
	}

	@Override
	@Transactional
	public Artist getArtist(int id) {
		
		return artistDAO.getArtist(id);
	}
	
	@Override
	@Transactional
	public Artist getArtistByEmail(String email) {
		
		return artistDAO.findArtistByEmail(email);
	}

	@Override
	@Transactional
	public void deleteArtist(Artist artist) {
		
		if (artist.isHas_image()) {
			new File(env.getProperty("file-upload.artists-dir") + artist.getId() + "_big.jpg").delete();
			new File(env.getProperty("file-upload.artists-dir") + artist.getId() + "_small.jpg").delete();
		}
		artistDAO.deleteArtist(artist.getId());
	}
	
	@Override
	@Transactional
	public Artist getArtistByToken(String token) {
		
		return artistDAO.findArtistByToken(token);
	}
	
	@Override
	@Transactional
	public Artist verifyArtist(Artist artist) {
		
		artist.setIs_enabled(true);
		
		return artistDAO.saveArtist(artist);
	}
	
	
	
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Artist artist = artistDAO.findArtistByEmail(email);
		UserBuilder builder = null;
		if (artist != null && artist.isIs_enabled()) {
	      
			builder = org.springframework.security.core.userdetails.User.withUsername(email);
			builder.password(artist.getPassword_hash());
			String[] authorities = {"USER"};
			builder.authorities(authorities);
		} else {
	    	throw new UsernameNotFoundException("User not found.");
		}
		return builder.build();
	}

	@Override
	@Transactional
	public List<Artist> searchArtists(String search) {
		
		return artistDAO.searchArtists(search);
	}

	@Override
	@Transactional
	public void processAndSaveImage(Artist artist, MultipartFile file) throws IOException {
		
		if (file.getSize() == 0) {
			return;
		}
		
		String tempFileName = env.getProperty("file-upload.tmp-dir") + artist.getId() + "_artist_temp.jpg";
		file.transferTo(Paths.get(tempFileName));
		
		String bigFileName = env.getProperty("file-upload.artists-dir") + artist.getId() + "_big.jpg";
		String smallFileName = env.getProperty("file-upload.artists-dir") + artist.getId() + "_small.jpg";
		
		Thumbnails.of(tempFileName)
			.crop(Positions.CENTER)
        	.size(636, 421)
        	.outputQuality(1)
        	.toFile(bigFileName);
		
		Thumbnails.of(tempFileName)
			.crop(Positions.CENTER)
	    	.size(128, 128)
	    	.outputQuality(1)
	    	.toFile(smallFileName);
		
		if (!artist.isHas_image()) {
			artist.setHas_image(true);
			artistDAO.saveArtist(artist);
		}
	}
}
