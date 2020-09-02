package com.musicbook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="memberships")
public class Membership {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	// range 1-2 valid
	@Column(name="state_id")
	private int state_id;
	
	@Column(name="created_at")
	private java.sql.Timestamp created_at;
	
	@Column(name="updated_at")
	private java.sql.Timestamp updated_at;
	
	@ManyToOne
    @JoinColumn(name = "artist_id")
	private Artist artist;
	
	@ManyToOne
    @JoinColumn(name = "band_id")
	private Band band;
	
	public Membership() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState_id() {
		return state_id;
	}

	public void setState_id(int state_id) {
		this.state_id = state_id;
	}

	public java.sql.Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(java.sql.Timestamp created_at) {
		this.created_at = created_at;
	}

	public java.sql.Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(java.sql.Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Band getBand() {
		return band;
	}

	public void setBand(Band band) {
		this.band = band;
	}
}
