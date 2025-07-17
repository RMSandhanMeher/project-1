package com.infinite.main;


import com.infinite.dao.AppointmentDao;
import com.infinite.dao.AppointmentDaoImpl;
import com.infinite.model.Appointment;
import com.infinite.model.DoctorAvailability;
import com.infinite.model.Doctors;
import com.infinite.model.Providers;
import com.infinite.model.Recipient;

public class Appoint {

	public static void main(String[] args) {

		// Initialize DAO
		AppointmentDao dao = new AppointmentDaoImpl();

		// ========== 1. Book a new appointment ==========
		Appointment appointment = new Appointment();

		Doctors doctor = new Doctors();
		doctor.setDoctor_id("D1003");

		Providers provider = new Providers();
		provider.setProvider_id("P1003");

		Recipient recipient = new Recipient();
		recipient.setH_id("H1004");

		DoctorAvailability availability = new DoctorAvailability();
		availability.setAvailability_id("A2021");

		appointment.setDoctor(doctor);
		appointment.setProvider(provider);
		appointment.setRecipient(recipient);
		appointment.setAvailability(availability);
		appointment.setSlot_no(10);


		String result = dao.bookAnAppointment(appointment);
		System.out.println(result);

	}
}
