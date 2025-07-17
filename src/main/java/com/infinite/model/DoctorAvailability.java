package com.infinite.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class DoctorAvailability {

	private String availability_id;
	private Doctors doctor; // One-to-One relationship
	private Date available_date;
	private Time start_time;
	private Time end_time;
	private SlotType slot_type; // ENUM('STANDARD', 'ADHOC')
	private int max_capacity;
	private int patient_window; // Auto-calculated by DB
	private boolean is_recurring;
	private String notes;
	private Timestamp created_at;

	// Getters and Setters

	public String getAvailability_id() {
		return availability_id;
	}

	public void setAvailability_id(String availability_id) {
		this.availability_id = availability_id;
	}

	public Doctors getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctors doctor) {
		this.doctor = doctor;
	}

	public Date getAvailable_date() {
		return available_date;
	}

	public void setAvailable_date(Date available_date) {
		this.available_date = available_date;
	}

	public Time getStart_time() {
		return start_time;
	}

	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}

	public Time getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}

	public SlotType getSlot_type() {
		return slot_type;
	}

	public void setSlot_type(SlotType slot_type) {
		this.slot_type = slot_type;
	}

	public int getMax_capacity() {
		return max_capacity;
	}

	public void setMax_capacity(int max_capacity) {
		this.max_capacity = max_capacity;
	}

	public int getPatient_window() {
		return patient_window;
	}

	public void setPatient_window(int patient_window) {
		this.patient_window = patient_window;
	}

	public boolean isIs_recurring() {
		return is_recurring;
	}

	public void setIs_recurring(boolean is_recurring) {
		this.is_recurring = is_recurring;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Availability ID: " + availability_id + ", Date: " + available_date + ", Time: " + start_time + " - "
				+ end_time + ", Capacity: " + max_capacity;
	}

}
