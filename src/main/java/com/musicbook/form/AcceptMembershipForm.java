package com.musicbook.form;

import javax.validation.constraints.NotNull;

public class AcceptMembershipForm {
	
	@NotNull(message="is required")
	private int id;
	
	public AcceptMembershipForm() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
