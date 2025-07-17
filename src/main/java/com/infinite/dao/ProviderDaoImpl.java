package com.infinite.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.infinite.model.Providers;
import com.infinite.util.SessionHelper;

public class ProviderDaoImpl implements ProviderDao {

    private SessionFactory sessionFactory;

    public ProviderDaoImpl() {
        this.sessionFactory = SessionHelper.getSessionFactory();
    }

    @Override
    public List<Providers> getAllApprovedProvider() {
        Session session = null;
        List<Providers> providers = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from Providers where status = 'APPROVED'");
            providers = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return providers;
    }

    @Override
    public Providers searchProviderById(String providerId) {
        Session session = null;
        Providers provider = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from Providers where provider_id = :pid");
            query.setParameter("pid", providerId);
            provider = (Providers) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return provider;
    }
}
