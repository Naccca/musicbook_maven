package com.musicbook.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateArtistForm {

	@NotNull(message="is required")
	private int id;
	
	@NotNull(message="is required")
	@Size(min=3, message="minimum 3 characters")
	private String name;
	
	private String bio;
	
	private String location;
	
	private String instruments;
	
	public UpdateArtistForm() {
		
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
