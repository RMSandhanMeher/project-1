package com.infinite.model;

public class AppointmentSlip {
	private String patientName;
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	private String appointmentId;
	private String providerName;
	private String providerEmail;
	private String providerNumber;
	private String doctorName;
	private String doctorSpecialization;
	private String date;
	private int slotNo;
	private String timing;

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderEmail() {
		return providerEmail;
	}

	public void setProviderEmail(String providerEmail) {
		this.providerEmail = providerEmail;
	}

	public String getProviderNumber() {
		return providerNumber;
	}

	public void setProviderNumber(String providerNumber) {
		this.providerNumber = providerNumber;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorSpecialization() {
		return doctorSpecialization;
	}

	public void setDoctorSpecialization(String doctorSpecialization) {
		this.doctorSpecialization = doctorSpecialization;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(int slotNo) {
		this.slotNo = slotNo;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

	public AppointmentSlip(String patientName,String appointmentId, String providerName, String providerEmail, String providerNumber,
			String doctorName, String doctorSpecialization, String date, int slotNo, String timing) {
		super();
		this.patientName=patientName;
		this.appointmentId = appointmentId;
		this.providerName = providerName;
		this.providerEmail = providerEmail;
		this.providerNumber = providerNumber;
		this.doctorName = doctorName;
		this.doctorSpecialization = doctorSpecialization;
		this.date = date;
		this.slotNo = slotNo;
		this.timing = timing;
	}

	public AppointmentSlip() {
	}

}
