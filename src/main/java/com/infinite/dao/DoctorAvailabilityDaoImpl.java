package com.infinite.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.infinite.model.DoctorAvailability;
import com.infinite.util.SessionHelper;

public class DoctorAvailabilityDaoImpl implements DoctorAvailabilityDao {

	private SessionFactory sessionFactory;

	public DoctorAvailabilityDaoImpl() {
		this.sessionFactory = SessionHelper.getSessionFactory();
	}

	@Override
	public List<DoctorAvailability> getAvailableSlotsByDoctorAndDate(String doctorId, Date date) {
		Session session = null;
		List<DoctorAvailability> slots = null;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(
					"from DoctorAvailability where doctor.doctor_id = :doctorId and available_date = :date order by start_time asc");
			query.setParameter("doctorId", doctorId);
			query.setParameter("date", date);
			slots = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return slots;
	}

	@Override
	public DoctorAvailability getAvailabilityById(String availabilityId) {
		Session session = null;
		DoctorAvailability availability = null;
		try {
			session = sessionFactory.openSession();
			availability = (DoctorAvailability) session.get(DoctorAvailability.class, availabilityId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return availability;
	}

	@Override
	public List<DoctorAvailability> getUpcomingAvailabilitiesForDoctor(String doctorId, Date fromDate) {
		Session session = null;
		List<DoctorAvailability> slots = null;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(
					"from DoctorAvailability where doctor.doctor_id = :doctorId and available_date >= :fromDate order by available_date, start_time asc");
			query.setParameter("doctorId", doctorId);
			query.setParameter("fromDate", fromDate);
			slots = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return slots;
	}

	@Override
	public List<DoctorAvailability> getAvailabilityByDoctorAndDate(String doctorId, Date date) {
		Session session = null;
		List<DoctorAvailability> slots = null;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(
					"from DoctorAvailability where doctor.doctor_id = :doctorId and available_date = :date order by start_time");
			query.setParameter("doctorId", doctorId);
			query.setParameter("date", date);
			slots = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return slots;
	}

	@Override
	public List<DoctorAvailability> getAllAvailabilitiesByProvider(String providerId) {
		Session session = null;
		List<DoctorAvailability> list = null;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(
					"from DoctorAvailability where doctor.provider.provider_id = :providerId order by available_date desc, start_time desc");
			query.setParameter("providerId", providerId);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	@Override
	public void addDoctorAvailability(DoctorAvailability availability) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(availability);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null)
				session.getTransaction().rollback();
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void updateDoctorAvailability(DoctorAvailability availability) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(availability);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null)
				session.getTransaction().rollback();
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean deleteAvailabilityIfNoAppointments(String availabilityId) {
		Session session = null;
		boolean deleted = false;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			Long count = (Long) session
					.createQuery(
							"select count(*) from Appointment where availability.availability_id = :availabilityId")
					.setParameter("availabilityId", availabilityId).uniqueResult();

			if (count == 0) {
				DoctorAvailability availability = (DoctorAvailability) session.get(DoctorAvailability.class,
						availabilityId);
				if (availability != null) {
					session.delete(availability);
					deleted = true;
				}
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null)
				session.getTransaction().rollback();
		} finally {
			if (session != null)
				session.close();
		}
		return deleted;
	}

	@Override
	public List<DoctorAvailability> getTodayAvailabilities() {
		Session session = null;
		List<DoctorAvailability> list = null;
		try {
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("from DoctorAvailability where available_date = current_date() order by start_time");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	@Override
	public int getRemainingSlotsForAvailability(String availabilityId) {
		Session session = null;
		int remainingSlots = 0;
		try {
			session = sessionFactory.openSession();

			DoctorAvailability availability = (DoctorAvailability) session.get(DoctorAvailability.class,
					availabilityId);
			if (availability != null) {
				int bookedSlots = ((Long) session.createQuery(
						"select count(*) from Appointment where availability.availability_id = :availabilityId and status = 'BOOKED'")
						.setParameter("availabilityId", availabilityId).uniqueResult()).intValue();

				remainingSlots = availability.getMax_capacity() - bookedSlots;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return remainingSlots;
	}
}