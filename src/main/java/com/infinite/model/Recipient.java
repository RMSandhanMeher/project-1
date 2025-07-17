package com.infinite.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Recipient implements Serializable {

	private String h_id;
	private String first_name;
	private String last_name;
	private String mobile;
	private String user_name;
	private Gender gender;
	private RecipientStatus status;
	private Date dob;
	private String address;
	private Date created_at;
	private String password;
	private String email;
	private Integer login_attempts; // changed to wrapper
	private Date locked_until;
	private Date last_login;
	private Date password_updated_at;

	// If appointments mapped later
	private Set<Appointment> appointments;

	// Default constructor
	public Recipient() {
	}

	// Getters and Setters

	public String getH_id() {
		return h_id;
	}

	public void setH_id(String h_id) {
		this.h_id = h_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public RecipientStatus getStatus() {
		return status;
	}

	public void setStatus(RecipientStatus status) {
		this.status = status;
	}

	public Integer getLogin_attempts() {
		return login_attempts;
	}

	public void setLogin_attempts(Integer login_attempts) {
		this.login_attempts = login_attempts;
	}

	public Date getLocked_until() {
		return locked_until;
	}

	public void setLocked_until(Date locked_until) {
		this.locked_until = locked_until;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public Date getPassword_updated_at() {
		return password_updated_at;
	}

	public void setPassword_updated_at(Date password_updated_at) {
		this.password_updated_at = password_updated_at;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
}
