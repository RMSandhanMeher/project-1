package com.infinite.dao;

import java.util.List;

import com.infinite.model.Doctors;

public interface DoctorDao {
	public List<Doctors> getAllApprovedDoctor();

	public List<Doctors> getApprovedDoctorsByProviderId(String providerId);

	public Doctors searchADoctorById(String doctorId);
}
