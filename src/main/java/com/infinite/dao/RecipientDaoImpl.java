package com.infinite.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query; // Use this in Hibernate 3.6 (not javax.persistence.Query)

import com.infinite.model.Recipient;
import com.infinite.util.SessionHelper;

public class RecipientDaoImpl {

	// Search recipient by ID (Primary Key)
	public Recipient searchRecipientById(String hId) {
		Session session = SessionHelper.getSessionFactory().openSession();
		try {
			return (Recipient) session.get(Recipient.class, hId);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	// Retrieve all recipients
	@SuppressWarnings("unchecked")
	public List<Recipient> getAllRecipients() {
		Session session = SessionHelper.getSessionFactory().openSession();
		try {
			Query query = session.createQuery("from Recipient");
			return query.list();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	// Save recipient
	public String saveRecipient(Recipient recipient) {
		Session session = SessionHelper.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(recipient);
			tx.commit();
			return "Recipient saved successfully.";
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return "Error saving recipient.";
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	// Update recipient
	public String updateRecipient(Recipient recipient) {
		Session session = SessionHelper.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(recipient);
			tx.commit();
			return "Recipient updated successfully.";
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return "Error updating recipient.";
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	// Delete recipient by ID
	public String deleteRecipient(String hId) {
		Session session = SessionHelper.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			Recipient recipient = (Recipient) session.get(Recipient.class, hId);
			if (recipient != null) {
				tx = session.beginTransaction();
				session.delete(recipient);
				tx.commit();
				return "Recipient deleted successfully.";
			} else {
				return "Recipient not found.";
			}
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return "Error deleting recipient.";
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}
