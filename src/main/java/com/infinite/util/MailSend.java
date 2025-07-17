package com.infinite.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.infinite.model.AppointmentSlip;

public class MailSend {

	public static String sendInfo(String toEmail, String subject, String htmlContent) {
		String from = "infinitehealthsure@gmail.com";
		String host = "smtp.gmail.com";

		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "xrascqydsfthxttk");
			}
		});

		session.setDebug(true);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);

			message.setContent(htmlContent, "text/html");

			Transport.send(message);
			return "Mail Sent Successfully...";
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return "Error: " + mex.getMessage();
		}
	}

	public static String appointmentRequest(AppointmentSlip apSli) {
		String htmlContent = "<html><body style='font-family:Arial, sans-serif;'>"
				+ "<h2 style='color:#2E86C1;'>Your appointment request has been received </h2>" + "<p>Dear "
				+ apSli.getPatientName() + ",</p>" + "<p>Thank you for booking your appointment with <strong>"
				+ apSli.getProviderName() + "</strong>.</p>"
				+ "<table style='border-collapse: collapse; width: 100%; margin-top: 10px;'>"
				+ "<tr><td style='padding: 8px; border: 1px solid #ddd;'>Appointment ID</td><td style='padding: 8px; border: 1px solid #ddd;'>"
				+ apSli.getAppointmentId() + "</td></tr>"
				+ "<tr><td style='padding: 8px; border: 1px solid #ddd;'>Doctor</td><td style='padding: 8px; border: 1px solid #ddd;'>"
				+ apSli.getDoctorName() + " (" + apSli.getDoctorSpecialization() + ")</td></tr>"
				+ "<tr><td style='padding: 8px; border: 1px solid #ddd;'>Date</td><td style='padding: 8px; border: 1px solid #ddd;'>"
				+ apSli.getDate() + "</td></tr>"
				+ "<tr><td style='padding: 8px; border: 1px solid #ddd;'>Time</td><td style='padding: 8px; border: 1px solid #ddd;'>Slot "
				+ apSli.getSlotNo() + " - " + apSli.getTiming() + "</td></tr>"
				+ "<tr><td style='padding: 8px; border: 1px solid #ddd;'>Provider Contact</td><td style='padding: 8px; border: 1px solid #ddd;'>"
				+ apSli.getProviderEmail() + " | " + apSli.getProviderNumber() + "</td></tr>" + "</table>"
				+ "<p style='margin-top: 20px;'>You will receive a confirmation once the provider reviews and approves your request.\r\n"
				+ "\r\n" + "Thank you for choosing Infinite HealthSure.</p>"
				+ "<p style='margin-top: 20px;'>Please arrive 15 minutes early and bring any necessary documents.</p>"
				+ "<hr></body></html>";
		return htmlContent;
	}

	public static String appointmentCancellation(AppointmentSlip apSli) {
		String htmlContent = "<html><body style='font-family:Arial, sans-serif;'>"
				+ "<h2 style='color:#C0392B;'>Your appointment has been cancelled</h2>" + "<p>Dear "
				+ apSli.getPatientName() + ",</p>" + "<p>Your appointment with <strong>" + apSli.getDoctorName()
				+ "</strong> at <strong>" + apSli.getProviderName() + "</strong> has been successfully cancelled.</p>"

				+ "<table style='border-collapse: collapse; width: 100%; margin-top: 10px;'>"
				+ "<tr><td style='padding: 8px; border: 1px solid #ddd;'>Appointment ID</td>"
				+ "<td style='padding: 8px; border: 1px solid #ddd;'>" + apSli.getAppointmentId() + "</td></tr>"

				+ "<tr><td style='padding: 8px; border: 1px solid #ddd;'>Date</td>"
				+ "<td style='padding: 8px; border: 1px solid #ddd;'>" + apSli.getDate() + "</td></tr>"

				+ "<tr><td style='padding: 8px; border: 1px solid #ddd;'>Time</td>"
				+ "<td style='padding: 8px; border: 1px solid #ddd;'>Slot " + apSli.getSlotNo() + " - "
				+ apSli.getTiming() + "</td></tr>"

				+ "<tr><td style='padding: 8px; border: 1px solid #ddd;'>Provider Contact</td>"
				+ "<td style='padding: 8px; border: 1px solid #ddd;'>" + apSli.getProviderEmail() + " | "
				+ apSli.getProviderNumber() + "</td></tr>" + "</table>"

				+ "<p style='margin-top: 20px;'>If this was a mistake or you want to reschedule, please contact us or book a new appointment.</p>"
				+ "<p>Thank you,<br>Infinite HealthSure Team</p>" + "<hr></body></html>";
		return htmlContent;
	}

}
