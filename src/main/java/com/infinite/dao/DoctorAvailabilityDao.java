package com.infinite.dao;

import java.sql.Date;
import java.util.List;

import com.infinite.model.DoctorAvailability;

public interface DoctorAvailabilityDao {

	// Fetch all available timings for a doctor on a specific date
	List<DoctorAvailability> getAvailableSlotsByDoctorAndDate(String doctorId, Date date);

	// Get availability by ID
	DoctorAvailability getAvailabilityById(String availabilityId);

	// Fetch future availabilities of a doctor starting from today (for booking)
	List<DoctorAvailability> getUpcomingAvailabilitiesForDoctor(String doctorId, Date fromDate);

	// Fetch availability by doctor and date (to check duplicate availabilities or
	// schedule conflicts)
	List<DoctorAvailability> getAvailabilityByDoctorAndDate(String doctorId, Date availableDate);

	// Fetch all availability records for a provider (admin/provider view)
	List<DoctorAvailability> getAllAvailabilitiesByProvider(String providerId);

	// Create a new doctor availability (used by provider/staff)
	void addDoctorAvailability(DoctorAvailability availability);

	// Update existing doctor availability
	void updateDoctorAvailability(DoctorAvailability availability);

	// Delete availability (only allowed if no appointments exist)
	boolean deleteAvailabilityIfNoAppointments(String availabilityId);

	// Fetch only today's availabilities (used on dashboard)
	List<DoctorAvailability> getTodayAvailabilities();

	// Count available unbooked slots for a particular availability (used during
	// slot rendering)
	int getRemainingSlotsForAvailability(String availabilityId);
}
