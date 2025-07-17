package com.infinite.main;

import com.infinite.model.AppointmentSlip;
import com.infinite.util.MailSend;

public class Main {

	public static void main(String[] args) {
		AppointmentSlip apSli = new AppointmentSlip("sourav kumar das ", "APT123456", "Infinite HealthSure Hospital",
				"contact@infinitehealthsure.com", "+91-9876543210", "Dr. Meera Sharma", "Cardiologist", "2025-07-20", 3,
				"11:00 AM - 11:30 AM");

		String subject = "Appointment Request Received – Awaiting Confirmation";

		String result = MailSend.sendInfo("souravkumardas503@gmail.com", subject, MailSend.appointmentRequest(apSli));
		System.out.println(result);
	}
}
