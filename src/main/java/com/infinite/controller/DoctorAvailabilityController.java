package com.infinite.controller;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.infinite.dao.DoctorAvailabilityDaoImpl;
import com.infinite.dao.DoctorDaoImpl;
import com.infinite.dao.RecipientDaoImpl;
import com.infinite.dao.AppointmentDaoImpl;
import com.infinite.model.*;
import com.infinite.util.MailSend;

public class DoctorAvailabilityController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final DoctorAvailabilityDaoImpl availabilityDao = new DoctorAvailabilityDaoImpl();
	private final AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

	private String doctorId = "D1003";
	private List<DayAvailabilitySummary> groupedAvailabilityList;
	private Date selectedDate;
	private String selectedDateInput;
	private List<SlotDisplay> availableSlots;
	private Map<Date, List<DoctorAvailability>> dateMap;
	private String selectedAvailabilityId;
	private int selectedSlotNumber;
	private Doctors doctor = new DoctorDaoImpl().searchADoctorById(doctorId);

	private List<String> availabilityTiming;

	@PostConstruct
	public void init() {
		loadAllUpcomingAvailability();
	}

	public void loadAllUpcomingAvailability() {
		Date today = new Date(System.currentTimeMillis());
		List<DoctorAvailability> futureSlots = availabilityDao.getUpcomingAvailabilitiesForDoctor(doctorId, today);

		dateMap = futureSlots.stream().collect(
				Collectors.groupingBy(DoctorAvailability::getAvailable_date, TreeMap::new, Collectors.toList()));

		groupedAvailabilityList = dateMap.entrySet().stream()
				.map(entry -> new DayAvailabilitySummary(entry.getKey(), formatDisplayDate(entry.getKey()),
						entry.getValue().stream()
								.mapToInt(
										da -> availabilityDao.getRemainingSlotsForAvailability(da.getAvailability_id()))
								.sum()))
				.collect(Collectors.toList());
	}

	public void loadAvailableSlots() {
		availabilityTiming = new ArrayList<String>();
		for (DoctorAvailability a : new DoctorAvailabilityDaoImpl().getAvailabilityByDoctorAndDate(doctorId,
				selectedDate)) {
			availabilityTiming.add(
					a.getStart_time().toString().substring(0, 5) + " - " + a.getEnd_time().toString().substring(0, 5));
		}
		availableSlots = new ArrayList<>();
		if (selectedDate == null)
			return;

		List<DoctorAvailability> dailyAvailabilities = dateMap.get(selectedDate);
		if (dailyAvailabilities == null)
			return;

		for (DoctorAvailability availability : dailyAvailabilities) {
			String availabilityId = availability.getAvailability_id();
			List<Integer> availableSlotNumbers = appointmentDao.getAvailableSlotNumbers(availabilityId);

			Timestamp start = Timestamp.valueOf(availability.getAvailable_date() + " " + availability.getStart_time());
			Timestamp end = Timestamp.valueOf(availability.getAvailable_date() + " " + availability.getEnd_time());
			int maxCapacity = availability.getMax_capacity();
			long slotDuration = (end.getTime() - start.getTime()) / (maxCapacity * 60000);

			availableSlotNumbers.forEach(slotNo -> {
				long slotStartMillis = start.getTime() + (slotNo - 1) * slotDuration * 60000;
				LocalTime slotStartTime = new Timestamp(slotStartMillis).toLocalDateTime().toLocalTime();
				LocalTime slotEndTime = new Timestamp(slotStartMillis + slotDuration * 60000).toLocalDateTime()
						.toLocalTime();

				availableSlots.add(
						new SlotDisplay(availabilityId, slotNo, formatTime(slotStartTime), formatTime(slotEndTime)));
			});
		}
	}

	public void handleDateSelection() {
		try {
			if (selectedDateInput != null && !selectedDateInput.isEmpty()) {
				selectedDate = Date.valueOf(selectedDateInput);
				loadAvailableSlots();
			}
		} catch (IllegalArgumentException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid date format. Please use YYYY-MM-DD", null));
		}
	}

	public String bookAppointment() {
		try {
			System.out.println("selectedAvailabilityId" + selectedAvailabilityId);
			FacesContext context = FacesContext.getCurrentInstance();
			DoctorAvailability availability = availabilityDao.getAvailabilityById(selectedAvailabilityId);

			if (availability == null) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Time slot no longer available", null));
				this.selectedAvailabilityId = null;
				this.selectedSlotNumber = 0;
				return null;
			}

			Appointment appointment = new Appointment();
			appointment.setAvailability(availability);
			appointment.setDoctor(availability.getDoctor());
			appointment.setSlot_no(selectedSlotNumber);

			// Set current user as recipient (should come from session)
			Recipient recipient = new Recipient();
			recipient.setH_id("H1003");
			appointment.setRecipient(recipient);

			Providers provider = new Providers();
			provider.setProvider_id("P1003");
			appointment.setProvider(provider);

			String result = appointmentDao.bookAnAppointment(appointment);
			if (result.startsWith("Appointment booked")) {

				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
						.getSession(true);
				session.setAttribute("confirmationMessage", result);
				this.selectedAvailabilityId = null;
				this.selectedSlotNumber = 0;
				this.selectedDateInput = null;
				this.selectedDate = null;
				this.loadAvailableSlots(); // Refresh available slots
				Recipient res = new RecipientDaoImpl().searchRecipientById(recipient.getH_id());
				Doctors doctor = new DoctorDaoImpl().searchADoctorById(doctorId);
				ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
						.getContext();
				Appointment ap = new AppointmentDaoImpl()
						.getAppointmentById(result.split(" ")[result.split(" ").length - 1]);
				String subject = "Appointment Request Received – Awaiting Confirmation";
				AppointmentSlip apSli = new AppointmentSlip(res.getFirst_name() + " " + res.getLast_name(),
						ap.getAppointment_id(), "Infinite HealthSure Hospital",
						servletContext.getInitParameter("providerEmail"), servletContext.getInitParameter("contact"),
						doctor.getDoctor_name(), doctor.getSpecialization(), ap.getStart().toString().split(" ")[0],
						appointment.getSlot_no(),
						ap.getStart().toString().split(" ")[1] + " - " + ap.getEnd().toString().split(" ")[1]);
				try {
					MailSend.sendInfo(res.getEmail(), subject, MailSend.appointmentRequest(apSli));
				} catch (Exception e) {
					System.out.println("error while sending the mail here ");
				}
				return "appointmentConfirmation?faces-redirect=true";
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result, null));
				this.selectedAvailabilityId = null;
				this.selectedSlotNumber = 0;
				this.selectedDateInput = null;
				this.selectedDate = null;
				this.loadAvailableSlots(); // Refresh available slots
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + e.getMessage(), null));
		}
		return null;
	}

	// Helper methods for time period filtering
	public List<SlotDisplay> getMorningSlots() {
		return filterSlotsByTime(LocalTime.MIN, LocalTime.NOON);
	}

	public List<SlotDisplay> getAfternoonSlots() {
		return filterSlotsByTime(LocalTime.NOON, LocalTime.of(17, 0));
	}

	public List<SlotDisplay> getEveningSlots() {
		return filterSlotsByTime(LocalTime.of(17, 0), LocalTime.MAX);
	}

	private List<SlotDisplay> filterSlotsByTime(LocalTime start, LocalTime end) {
		if (availableSlots == null)
			return Collections.emptyList();

		return availableSlots.stream().filter(slot -> {
			LocalTime time = LocalTime.parse(slot.getStartTime());
			return !time.isBefore(start) && time.isBefore(end);
		}).collect(Collectors.toList());
	}

	// Utility methods
	private String formatDisplayDate(Date date) {
		return new SimpleDateFormat("E d, MMM", Locale.ENGLISH).format(date);
	}

	private String formatTime(LocalTime time) {
		return String.format("%02d:%02d", time.getHour(), time.getMinute());
	}

	// Getters and Setters
	public List<DayAvailabilitySummary> getGroupedAvailabilityList() {
		return groupedAvailabilityList;
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getSelectedDateInput() {
		return selectedDateInput;
	}

	public void setSelectedDateInput(String selectedDateInput) {
		this.selectedDateInput = selectedDateInput;
	}

	public List<SlotDisplay> getAvailableSlots() {
		return availableSlots;
	}

	public int getMorningSlotCount() {
		return getMorningSlots().size();
	}

	public int getAfternoonSlotCount() {
		return getAfternoonSlots().size();
	}

	public int getEveningSlotCount() {
		return getEveningSlots().size();
	}

	public String getSelectedAvailabilityId() {
		return selectedAvailabilityId;
	}

	public void setSelectedAvailabilityId(String selectedAvailabilityId) {
		this.selectedAvailabilityId = selectedAvailabilityId;
	}

	public int getSelectedSlotNumber() {
		return selectedSlotNumber;
	}

	public void setSelectedSlotNumber(int selectedSlotNumber) {
		this.selectedSlotNumber = selectedSlotNumber;
	}

	// Inner classes
	public static class DayAvailabilitySummary {
		private final Date date;
		private final String displayDate;
		private final int totalSlots;

		public DayAvailabilitySummary(Date date, String displayDate, int totalSlots) {
			this.date = date;
			this.displayDate = displayDate;
			this.totalSlots = totalSlots;
		}

		public Date getDate() {
			return date;
		}

		public String getDisplayDate() {
			return displayDate;
		}

		public int getTotalSlots() {
			return totalSlots;
		}
	}

	public static class SlotDisplay {
		private final String availabilityId;
		private final int slotNumber;
		private final String startTime;
		private final String endTime;

		public SlotDisplay(String availabilityId, int slotNumber, String startTime, String endTime) {
			this.availabilityId = availabilityId;
			this.slotNumber = slotNumber;
			this.startTime = startTime;
			this.endTime = endTime;
		}

		public String getAvailabilityId() {
			return availabilityId;
		}

		public int getSlotNumber() {
			return slotNumber;
		}

		public String getStartTime() {
			return startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public String getFormattedTimeRange() {
			return startTime + " - " + endTime;
		}
	}

	public Doctors getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctors doctor) {
		this.doctor = doctor;
	}

	public List<String> getAvailabilityTiming() {
		return availabilityTiming;
	}

	public void setAvailabilityTiming(List<String> availabilityTiming) {
		this.availabilityTiming = availabilityTiming;
	}

}