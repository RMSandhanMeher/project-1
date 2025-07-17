package com.infinite.dao;

import java.util.List;

import com.infinite.model.Providers;

public interface ProviderDao {

	public List<Providers> getAllApprovedProvider();

	public Providers searchProviderById(String providerId);
}
