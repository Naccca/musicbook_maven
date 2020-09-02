package com.musicbook.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateBandForm {

	@NotNull(message="is required")
	@Size(min=3, message="minimum 3 characters")
	private String name;
	
	private String bio;
	
	private String location;
	
	private String genres;
	
	@NotNull(message="is required")
	private int owner_id;
	
	public CreateBandForm() {
		
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

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}
	
	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}
}
