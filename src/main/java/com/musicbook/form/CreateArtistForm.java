package com.musicbook.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateArtistForm {

	@NotNull(message="is required")
	@Size(min=3, message="minimum 3 characters")
	private String username;
	
	@NotNull(message="is required")
	@Size(min=3, message="minimum 3 characters")
	private String  password;
	
	@NotNull(message="is required")
	@Size(min=3, message="minimum 3 characters")
	private String name;
	
	private String bio;
	
	private String location;
	
	private String instruments;
	
	public CreateArtistForm() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInstruments() {
		return instruments;
	}

	public void setInstruments(String instruments) {
		this.instruments = instruments;
	}
}
