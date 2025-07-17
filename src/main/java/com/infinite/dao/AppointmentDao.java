package com.infinite.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.infinite.model.Appointment;

public interface AppointmentDao {

    // Book a new appointment
    String bookAnAppointment(Appointment appointment);

    // Check if the given availability slot is full
    boolean isAvailabilitySlotFull(String availabilityId);

    // Get all upcoming appointments for a given recipient (max 10)
    List<Appointment> getUpcomingAppointmentsByRecipient(String recipientId);

    // Get past appointments for recipient (with optional filters)
    List<Appointment> getPastAppointmentsByRecipient(String recipientId);

    // Get appointment by ID
    Appointment getAppointmentById(String appointmentId);

    // Cancel appointment (future ones only)
    boolean cancelAppointment(String appointmentId);

    // Update an existing appointment (change slot etc.)
    boolean updateAppointment(Appointment appointment);

    // Get current booked count for a slot
    int getBookedCountForAvailability(String availabilityId);

    // Validate if recipient has overlapping appointment at given time
    boolean hasOverlappingAppointment(String recipientId, String availabilityId, Timestamp start, Timestamp end);

    // Get all available (unbooked) slot numbers for a given availability
    List<Integer> getAvailableSlotNumbers(String availabilityId);

    // Check if a specific slot number is already booked
    boolean isSlotAlreadyBooked(String availabilityId, int slotNo);

    // Get all appointments for a specific availability (for doctor or admin view)
    List<Appointment> getAppointmentsByAvailability(String availabilityId);

    // Check if the appointment time is in the past
    boolean isAppointmentInPast(String appointmentId);

    // Get appointments by doctor and date (doctor’s daily schedule)
    List<Appointment> getAppointmentsByDoctorAndDate(String doctorId, Date date);

    // Check if a specific slot in an availability is in the future
    boolean isSlotTimeInFuture(String availabilityId, int slotNo);
}
