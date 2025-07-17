package com.infinite.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.infinite.dao.AppointmentDaoImpl;
import com.infinite.dao.DoctorDaoImpl;
import com.infinite.dao.RecipientDaoImpl;
import com.infinite.model.Appointment;
import com.infinite.model.AppointmentSlip;
import com.infinite.model.Doctors;
import com.infinite.model.Recipient;
import com.infinite.util.MailSend;

@ManagedBean
@ViewScoped
public class RecipientAppointmentController implements Serializable {

	private static final long serialVersionUID = 1L;

	private final AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

	private String hId = "H1003"; // This should ideally come from session/login

	private List<Appointment> upcomingAppointments = new ArrayList<>();
	private List<Appointment> pastAppointments = new ArrayList<>();
	private List<Appointment> filteredAppointments = new ArrayList<>();

	private String filterType = "upcoming"; // default filter
	private Appointment selectedAppointment;

	@PostConstruct
	public void init() {
		loadAppointments();
	}

	public void loadAppointments() {
		try {
			upcomingAppointments = appointmentDao.getUpcomingAppointmentsByRecipient(hId);
			pastAppointments = appointmentDao.getPastAppointmentsByRecipient(hId);
			updateFilteredAppointments(); // initial filter
		} catch (Exception e) {
			System.err.println("Error loading appointments: " + e.getMessage());
			upcomingAppointments = new ArrayList<>();
			pastAppointments = new ArrayList<>();
			filteredAppointments = new ArrayList<>();
		}
	}

	public void updateFilteredAppointments() {
		if ("past".equals(filterType)) {
			filteredAppointments = new ArrayList<>(pastAppointments);
		} else {
			filteredAppointments = new ArrayList<>(upcomingAppointments);
		}
	}

	public String cancelAppointment() {
		if (selectedAppointment != null) {
			try {
				boolean success = appointmentDao.cancelAppointment(selectedAppointment.getAppointment_id());

				Recipient res = new RecipientDaoImpl()
						.searchRecipientById(selectedAppointment.getRecipient().getH_id());

				if (success) {
					// Load doctor info
					Doctors doctor = new DoctorDaoImpl()
							.searchADoctorById(selectedAppointment.getDoctor().getDoctor_id());

					ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
							.getContext();

					String subject = "Appointment Cancelled – Infinite HealthSure";

					AppointmentSlip apSli = new AppointmentSlip(
							res.getFirst_name() + " " + res.getLast_name(),
							selectedAppointment.getAppointment_id(),
							"Infinite HealthSure Hospital",
							servletContext.getInitParameter("providerEmail"),
							servletContext.getInitParameter("contact"),
							doctor.getDoctor_name(),
							doctor.getSpecialization(),
							selectedAppointment.getStart().toString().split(" ")[0],
							selectedAppointment.getSlot_no(),
							selectedAppointment.getStart().toString().split(" ")[1] + " - " + selectedAppointment.getEnd().toString().split(" ")[1]);

					try {
						MailSend.sendInfo(res.getEmail(), subject, MailSend.appointmentCancellation(apSli));
					} catch (Exception e) {
						System.err.println("Error while sending cancellation email: " + e.getMessage());
					}

					loadAppointments(); // Refresh the lists
					return "recipient-appointments?faces-redirect=true";
				}
			} catch (Exception e) {
				System.err.println("Error canceling appointment: " + e.getMessage());
			}
		}
		return null;
	}


	// ===================== GETTERS & SETTERS =====================

	public List<Appointment> getUpcomingAppointments() {
		return upcomingAppointments;
	}

	public List<Appointment> getPastAppointments() {
		return pastAppointments;
	}

	public List<Appointment> getFilteredAppointments() {
		return filteredAppointments;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
		updateFilteredAppointments(); // Automatically reapply filter
	}

	public Appointment getSelectedAppointment() {
		return selectedAppointment;
	}

	public void setSelectedAppointment(Appointment selectedAppointment) {
		this.selectedAppointment = selectedAppointment;
	}

	public String getHId() {
		return hId;
	}

	public void setHId(String hId) {
		this.hId = hId;
	}
}