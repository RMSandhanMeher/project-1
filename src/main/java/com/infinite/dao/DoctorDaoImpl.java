package com.infinite.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.infinite.model.Doctors;
import com.infinite.util.SessionHelper;

public class DoctorDaoImpl implements DoctorDao {

	private SessionFactory sessionFactory;

	public DoctorDaoImpl() {
		this.sessionFactory = SessionHelper.getSessionFactory();
	}

	@Override
	public List<Doctors> getAllApprovedDoctor() {
		Session session = null;
		List<Doctors> doctors = null;

		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Doctors where login_status = 'APPROVED'");
			doctors = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return doctors;
	}

	@Override
	public List<Doctors> getApprovedDoctorsByProviderId(String providerId) {
		Session session = null;
		List<Doctors> doctors = null;

		try {
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("from Doctors where login_status = 'APPROVED' and provider.provider_id = :pid");
			query.setParameter("pid", providerId);
			doctors = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return doctors;
	}

	@Override
	public Doctors searchADoctorById(String doctorId) {
		Session session = null;
		Doctors doctor = null;

		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Doctors where doctor_id = :docId");
			query.setParameter("docId", doctorId);
			doctor = (Doctors) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return doctor;
	}
}
