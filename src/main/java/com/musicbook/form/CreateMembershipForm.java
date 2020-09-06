package com.musicbook.form;

import javax.validation.constraints.NotNull;

public class CreateMembershipForm {

	@NotNull(message="is required")
	private String artist_name;
	
	@NotNull(message="is required")
	private int band_id;
	
	public CreateMembershipForm() {
		
	}

	public String getArtist_name() {
		return artist_name;
	}

	public void setArtist_name(String artist_name) {
		this.artist_name = artist_name;
	}

	public int getBand_id() {
		return band_id;
	}

	public void setBand_id(int band_id) {
		this.band_id = band_id;
	}
}
