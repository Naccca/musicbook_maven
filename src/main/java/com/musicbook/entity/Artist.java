package com.musicbook.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="artists")
public class Artist {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password_hash")
	private String  password_hash;
	
	@Column(name="name")
	private String name;
	
	@Column(name="bio")
	private String bio;
	
	@Column(name="location")
	private String location;
	
	@Column(name="instruments")
	private String instruments;
	
	@Column(name="created_at")
	private java.sql.Timestamp created_at;
	
	@Column(name="updated_at")
	private java.sql.Timestamp updated_at;
	
	@Column(name="has_image")
	private boolean has_image;
	
	@OneToMany(mappedBy="artist")
	private List<Membership> memberships;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="owner")
	private List<Band> bands;
	
	public Artist() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
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

	public boolean isHas_image() {
		return has_image;
	}

	public void setHas_image(boolean has_image) {
		this.has_image = has_image;
	}
	
	public List<Membership> getMemberships() {
		return memberships;
	}

	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}

	public List<Band> getBands() {
		return bands;
	}

	public void setBands(List<Band> bands) {
		this.bands = bands;
	}

	@Override
	public String toString() {
		return "Artists [id=" + id + ", username=" + username + ", name=" + name + ", created_at=" + created_at
				+ ", updated_at=" + updated_at + "]";
	}
}
