package com.infinite.controller;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;

import com.infinite.dao.*;
import com.infinite.model.*;
import com.infinite.util.SessionHelper;

public class DoctorProviderController implements Serializable {
	private static final long serialVersionUID = 1L;

	// DAOs
	private transient DoctorDaoImpl doctorDao = new DoctorDaoImpl();
	private transient ProviderDaoImpl providerDao = new ProviderDaoImpl();
	private transient RecipientDaoImpl recipientDao = new RecipientDaoImpl();
	private transient DoctorAvailabilityDaoImpl availabilityDao = new DoctorAvailabilityDaoImpl();
	private transient AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

	// Entities
	private Doctors doctor;
	private Providers provider;
	private Recipient recipient;

	// Form fields
	private String doctorId;
	private String providerId;
	private String customDate;
	private String notes;
	private String selectedAvailabilityId;

	private List<DoctorAvailability> availableSlots = new ArrayList<>();
	private Appointment appointment = new Appointment();

	// Constructor
	public DoctorProviderController() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		doctorId = (String) session.getAttribute("selectedDoctorId");
		providerId = (String) session.getAttribute("selectedProviderId");
		loadEntities();
	}

	private void loadEntities() {
		if (doctorId != null && !doctorId.isEmpty()) {
			doctor = doctorDao.searchADoctorById(doctorId);
		}
		if (providerId != null && !providerId.isEmpty()) {
			provider = providerDao.searchProviderById(providerId);
		}
		// Make dynamic later
		recipient = recipientDao.searchRecipientById("H1005");
	}

	// Called when user clicks "Find Slots"
	public void findAvailabilityForDate() {
		searchSlotsByDate();
	}

	public void searchSlotsByDate() {
		try {
			if (customDate == null || customDate.trim().isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Please enter a valid date.", null));
				return;
			}

			Date parsedDate = Date.valueOf(customDate.trim());
			availableSlots = availabilityDao.getAvailableSlotsByDoctorAndDate(doctorId, parsedDate);

			if (availableSlots == null || availableSlots.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "No slots available for selected date.", null));
			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to fetch available slots.", null));
		}
	}

	public List<SelectItem> getSlotOptions() {
		List<SelectItem> items = new ArrayList<>();
		if (availableSlots != null) {
			for (DoctorAvailability slot : availableSlots) {
				int booked = getCurrentAppointmentsCount(slot.getAvailability_id());
				boolean isFull = booked >= slot.getMax_capacity();

				// Format: 09:00 - 09:30
				String label = String.format("%tR - %tR", slot.getStart_time(), slot.getEnd_time());
				if (isFull)
					label += " (Full)";

				items.add(new SelectItem(slot.getAvailability_id(), label));
			}
		}
		return items;
	}

	public String bookAppointment() {
		try {
			if (selectedAvailabilityId == null || selectedAvailabilityId.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a time slot.", null));
				return null;
			}

			DoctorAvailability selectedAvailability = availabilityDao.getAvailabilityById(selectedAvailabilityId);
			if (selectedAvailability == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selected slot is invalid.", null));
				return null;
			}

			int currentCount = getCurrentAppointmentsCount(selectedAvailabilityId);
			if (currentCount >= selectedAvailability.getMax_capacity()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Selected slot is already full.", null));
				return null;
			}

			// Prepare appointment
			Timestamp now = new Timestamp(System.currentTimeMillis());
			appointment = new Appointment(); // Always new for clean object

			appointment.setDoctor(doctor);
			appointment.setProvider(provider);
			appointment.setRecipient(recipient);
			appointment.setAvailability(selectedAvailability);
			appointment.setBooked_at(now);
			appointment.setRequested_at(now);
			appointment.setStatus(AppointmentStatus.PENDING);
			appointment.setNotes(notes);
			appointment.setSlot_no(currentCount + 1);

			String result = appointmentDao.bookAnAppointment(appointment);

			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);

			if (result.startsWith("Appointment Booked")) {
				session.setAttribute("confirmationMessage", "✅ " + result);
				return "appointmentConfirmation?faces-redirect=true";
			} else {
				session.setAttribute("confirmationMessage", "⚠️ " + result);
				return "index?faces-redirect=true";
			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error during booking.", null));
			return null;
		}
	}

	private int getCurrentAppointmentsCount(String availabilityId) {
		int count = 0;
		try {
			Session session = SessionHelper.getSessionFactory().openSession();
			Query query = session
					.createQuery("SELECT COUNT(a) FROM Appointment a WHERE a.availability.availability_id = :aid");
			query.setParameter("aid", availabilityId);
			Long result = (Long) query.uniqueResult();
			if (result != null)
				count = result.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// Getters & Setters

	public Doctors getDoctor() {
		return doctor;
	}

	public Providers getProvider() {
		return provider;
	}

	public String getCustomDate() {
		return customDate;
	}

	public void setCustomDate(String customDate) {
		this.customDate = customDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<DoctorAvailability> getAvailableSlots() {
		return availableSlots;
	}

	public String getSelectedAvailabilityId() {
		return selectedAvailabilityId;
	}

	public void setSelectedAvailabilityId(String selectedAvailabilityId) {
		this.selectedAvailabilityId = selectedAvailabilityId;
	}
}
