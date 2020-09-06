package com.musicbook.form;

import javax.validation.constraints.NotNull;

public class DeleteMembershipForm {

	@NotNull(message="is required")
	private int id;
	
	public DeleteMembershipForm() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
