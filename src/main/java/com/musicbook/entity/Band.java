package com.musicbook.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="bands")
public class Band {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="bio")
	private String bio;
	
	@Column(name="location")
	private String location;
	
	@Column(name="genres")
	private String genres;
	
	@Column(name="created_at")
	private java.sql.Timestamp created_at;
	
	@Column(name="updated_at")
	private java.sql.Timestamp updated_at;
	
	@Column(name="has_image")
	private boolean has_image;
	
	@ManyToOne
    @JoinColumn(name = "owner_id")
	private Artist owner;
	
	@OneToMany(mappedBy="band")
	private List<Membership> memberships;
	
	public Band() {
		
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

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
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

	public Artist getOwner() {
		return owner;
	}

	public void setOwner(Artist owner) {
		this.owner = owner;
	}

	public List<Membership> getMemberships() {
		return memberships;
	}

	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}

	@Override
	public String toString() {
		return "Band [id=" + id + ", name=" + name + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
}
