package com.musicbook.form;

import javax.validation.constraints.NotNull;

public class DeleteBandForm {

	@NotNull(message="is required")
	private int id;
	
	public DeleteBandForm() {
		
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
