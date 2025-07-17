package com.infinite.controller;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.infinite.dao.AppointmentDao;
import com.infinite.dao.AppointmentDaoImpl;
import com.infinite.dao.DoctorAvailabilityDao;
import com.infinite.dao.DoctorAvailabilityDaoImpl;
import com.infinite.model.*;

@ManagedBean(name = "appointmentController")
@SessionScoped
public class AppointmentController implements Serializable {

	private static final long serialVersionUID = 1L;

	private String availabilityId;
	private int selectedSlot;
	private Appointment appointment = new Appointment();
	private DoctorAvailability availability;
	private List<SlotDisplay> availableSlots;
	private List<AvailabilitySlotTimeing> availabilitySlotTimeing;

	private String message;
	private boolean success;
	private Date day;

	private AppointmentDao appointmentDao = new AppointmentDaoImpl();
	private DoctorAvailabilityDao doctorAvailabilityDao = new DoctorAvailabilityDaoImpl();

	// Called from UI to load slots
	public String selectAvailabilityAndLoadSlots(String selectedAvailabilityId) {
		this.availabilityId = selectedAvailabilityId;
		return loadAvailableSlots();
	}

	public String loadAvailableSlots() {
		Map<Date, List<DoctorAvailability>> a = (Map<Date, List<DoctorAvailability>>) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("dateMap");
		System.out.println(a.toString());

		try {

			List<DoctorAvailability> b = a.get(day);
			availabilitySlotTimeing = new ArrayList<AppointmentController.AvailabilitySlotTimeing>();

			for (DoctorAvailability doctorAvailability : b) {
				availableSlots = new ArrayList<>();
				availabilityId = doctorAvailability.getAvailability_id();
				AppointmentController.AvailabilitySlotTimeing avaslotimee = new AvailabilitySlotTimeing();
				avaslotimee.setNum(availabilityId);

				if (availabilityId == null || availabilityId.trim().isEmpty()) {
					message = "❌ Availability ID is missing.";
					success = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));

					return null;
				}

				availability = doctorAvailabilityDao.getAvailabilityById(availabilityId);
				if (availability == null) {
					message = "❌ Availability not found for ID: " + availabilityId;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));

					success = false;
					return null;
				}

				List<Integer> slotNumbers = appointmentDao.getAvailableSlotNumbers(availabilityId);
				if (slotNumbers == null || slotNumbers.isEmpty()) {
					message = "❌ No available slots found.";
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));

					success = false;
					return null;
				}

				Timestamp start = Timestamp
						.valueOf(availability.getAvailable_date() + " " + availability.getStart_time());
				Timestamp end = Timestamp.valueOf(availability.getAvailable_date() + " " + availability.getEnd_time());
				int maxCapacity = availability.getMax_capacity();

				long totalMinutes = (end.getTime() - start.getTime()) / (1000 * 60);
				long slotMinutes = totalMinutes / maxCapacity;

				for (int slotNo : slotNumbers) {
					long slotStartMillis = start.getTime() + (slotNo - 1) * slotMinutes * 60 * 1000;
					long slotEndMillis = slotStartMillis + slotMinutes * 60 * 1000;

					LocalTime slotStartTime = new Timestamp(slotStartMillis).toLocalDateTime().toLocalTime();
					LocalTime slotEndTime = new Timestamp(slotEndMillis).toLocalDateTime().toLocalTime();

					availableSlots.add(new SlotDisplay(slotNo, slotStartTime.toString(), slotEndTime.toString()));
				}
				avaslotimee.setSlot(availableSlots);
				availabilitySlotTimeing.add(avaslotimee);

				message = "✅ Loaded " + availableSlots.size() + " available slot(s)";
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));

				success = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "❌ Error loading slots: " + e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
			success = false;
			availableSlots = new ArrayList<>();
		}
		return "slotListView"; // navigate to slot listing page if needed
	}

	public String bookAppointment(String availabilityId, int slotNumber) {
		try {

			availability = doctorAvailabilityDao.getAvailabilityById(availabilityId);

			Doctors doctor = new Doctors();
			Providers provider = new Providers();

			Recipient recipient = new Recipient();
			recipient.setH_id("H1003"); // should be dynamic from logged in user

			doctor.setDoctor_id("D1003");
			provider.setProvider_id("P1003");

			Timestamp start = Timestamp.valueOf(availability.getAvailable_date() + " " + availability.getStart_time());
			Timestamp end = Timestamp.valueOf(availability.getAvailable_date() + " " + availability.getEnd_time());
			int maxCapacity = availability.getMax_capacity();

			long slotMinutes = ((end.getTime() - start.getTime()) / (1000 * 60)) / maxCapacity;
			long slotStart = start.getTime() + (selectedSlot - 1) * slotMinutes * 60 * 1000;
			long slotEnd = slotStart + slotMinutes * 60 * 1000;

			appointment.setAvailability(availability);
			appointment.setDoctor(doctor);
			appointment.setProvider(provider);
			appointment.setRecipient(recipient);
			appointment.setSlot_no(slotNumber);

			String result = appointmentDao.bookAnAppointment(appointment);

			if (result.startsWith("Appointment booked")) {
				message = "✅ " + result;
				success = true;
				loadAvailableSlots();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
			} else {
				message = "❌ Booking failed: " + result;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));

				success = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			message = "❌ Internal error: " + e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));

		}
		return null;
	}

	public static class AvailabilitySlotTimeing {
		private String num;
		private List<SlotDisplay> slot;

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public List<SlotDisplay> getSlot() {
			return slot;
		}

		public void setSlot(List<SlotDisplay> slot) {
			this.slot = slot;
		}

	}

	public List<AvailabilitySlotTimeing> getAvailabilitySlotTimeing() {
		return availabilitySlotTimeing;
	}

	public void setAvailabilitySlotTimeing(List<AvailabilitySlotTimeing> availabilitySlotTimeing) {
		this.availabilitySlotTimeing = availabilitySlotTimeing;
	}

	// Getters and Setters
	public String getAvailabilityId() {
		return availabilityId;
	}

	public void setAvailabilityId(String availabilityId) {
		this.availabilityId = availabilityId;
	}

	public int getSelectedSlot() {
		return selectedSlot;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public void setSelectedSlot(int selectedSlot) {
		this.selectedSlot = selectedSlot;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public List<SlotDisplay> getAvailableSlots() {
		return availableSlots;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

	public DoctorAvailability getAvailability() {
		return availability;
	}

}
