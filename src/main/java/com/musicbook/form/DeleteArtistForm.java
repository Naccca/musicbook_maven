package com.musicbook.form;

import javax.validation.constraints.NotNull;

public class DeleteArtistForm {

	@NotNull(message="is required")
	private int id;
	
	public DeleteArtistForm() {
		
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
