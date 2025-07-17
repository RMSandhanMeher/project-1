package com.infinite.helper;

import com.infinite.model.Appointment;
import java.time.format.DateTimeFormatter;

public class AppointmentView {

	private final Appointment appointment;

	public AppointmentView(Appointment appointment) {
		this.appointment = appointment;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public String getFormattedDate() {
		try {
			return appointment.getStart().toLocalDateTime().toLocalDate().toString(); // e.g., 2025-07-14
		} catch (Exception e) {
			return "N/A";
		}
	}

	public String getFormattedTimeRange() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
			String start = appointment.getStart().toLocalDateTime().toLocalTime().format(formatter);
			String end = appointment.getEnd().toLocalDateTime().toLocalTime().format(formatter);
			return start + " - " + end;
		} catch (Exception e) {
			return "N/A";
		}
	}

	public String getDoctorName() {
		return (appointment.getDoctor() != null) ? appointment.getDoctor().getDoctor_name() : "N/A";
	}

	public String getNotes() {
		return (appointment.getNotes() != null) ? appointment.getNotes() : "None";
	}
}
