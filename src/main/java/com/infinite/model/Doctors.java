package com.infinite.model;

import java.util.List;
import java.util.Set;

public class Doctors {

	private String doctor_id;
	private Providers provider; // Many-to-One relationship
	private String doctor_name;
	private String qualification;
	private String specialization;
	private String license_no;
	private String email;
	private String address;
	private String gender;
	private String password;
	private LoginStatus login_status; // Enum: PENDING, APPROVED, REJECTED
	private DoctorStatus doctor_status; // Enum: ACTIVE, INACTIVE

	// ✅ One-to-Many: A doctor can have multiple availability slots
	private Set<DoctorAvailability> availabilityList;

	// Getters and Setters

	public String getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}

	public Providers getProvider() {
		return provider;
	}

	public void setProvider(Providers provider) {
		this.provider = provider;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getLicense_no() {
		return license_no;
	}

	public void setLicense_no(String license_no) {
		this.license_no = license_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginStatus getLogin_status() {
		return login_status;
	}

	public void setLogin_status(LoginStatus login_status) {
		this.login_status = login_status;
	}

	public DoctorStatus getDoctor_status() {
		return doctor_status;
	}

	public void setDoctor_status(DoctorStatus doctor_status) {
		this.doctor_status = doctor_status;
	}

	public Set<DoctorAvailability> getAvailabilityList() {
		return availabilityList;
	}

	public void setAvailabilityList(Set<DoctorAvailability> availabilityList) {
		this.availabilityList = availabilityList;
	}

}
