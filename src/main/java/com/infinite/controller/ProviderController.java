package com.infinite.controller;

import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.*;

import com.infinite.dao.ProviderDaoImpl;
import com.infinite.model.Providers;

public class ProviderController implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProviderDaoImpl dao = new ProviderDaoImpl();
	private List<Providers> approvedProviders;
	private int pageSize = 5;
	private String searchText = "";
	private String searchField = "provider_name"; // default field

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String goToDoctorList(String providerId) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getSessionMap().put("selectedProviderId", providerId);
			return "doctorList?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Providers searchAProviderById(String providerId) {
		return dao.searchProviderById(providerId);
	}

	public void searchProviders() {
		List<Providers> allProviders = dao.getAllApprovedProvider();
		if (searchText != null && !searchText.trim().isEmpty()) {
			String lowerSearch = searchText.toLowerCase();
			approvedProviders = new ArrayList<>();
			for (Providers p : allProviders) {
				boolean match = false;
				switch (searchField) {
				case "provider_name":
					match = p.getProvider_name().toLowerCase().contains(lowerSearch);
					break;
				case "hospital_name":
					match = p.getHospital_name().toLowerCase().contains(lowerSearch);
					break;
				case "city":
					match = p.getCity().toLowerCase().contains(lowerSearch);
					break;
				case "state":
					match = p.getState().toLowerCase().contains(lowerSearch);
					break;
				case "all":
					match = p.getProvider_name().toLowerCase().contains(lowerSearch)
							|| p.getHospital_name().toLowerCase().contains(lowerSearch)
							|| p.getCity().toLowerCase().contains(lowerSearch)
							|| p.getState().toLowerCase().contains(lowerSearch);
					break;
				}
				if (match) {
					approvedProviders.add(p);
				}
			}
		} else {
			approvedProviders = allProviders;
		}
		sortProviders();
		currentPage = 0;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	private int currentPage = 0;

	private String sortField = "provider_id";
	private boolean ascending = true;

	public ProviderController() {
		approvedProviders = dao.getAllApprovedProvider();
		sortProviders();
	}

	public List<Providers> getPaginatedApprovedProviders() {
		int fromIndex = currentPage * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, approvedProviders.size());
		if (fromIndex > toIndex) {
			currentPage = 0;
			fromIndex = 0;
			toIndex = Math.min(pageSize, approvedProviders.size());
		}
		return approvedProviders.subList(fromIndex, toIndex);
	}

	public void nextPage() {
		if ((currentPage + 1) * pageSize < approvedProviders.size()) {
			currentPage++;
		}
	}

	public void prevPage() {
		if (currentPage > 0) {
			currentPage--;
		}
	}

	public void sortBy(String field) {
		if (sortField.equals(field)) {
			ascending = !ascending;
		} else {
			sortField = field;
			ascending = true;
		}
		sortProviders();
		currentPage = 0;
	}

	private void sortProviders() {
		Comparator<Providers> comparator;

		switch (sortField) {
		case "provider_name":
			comparator = Comparator.comparing(Providers::getProvider_name);
			break;
		case "hospital_name":
			comparator = Comparator.comparing(Providers::getHospital_name);
			break;
		case "email":
			comparator = Comparator.comparing(Providers::getEmail);
			break;
		case "city":
			comparator = Comparator.comparing(Providers::getCity);
			break;
		case "state":
			comparator = Comparator.comparing(Providers::getState);
			break;
		case "zip_code":
			comparator = Comparator.comparing(Providers::getZip_code);
			break;
		case "status":
			comparator = Comparator.comparing(p -> p.getStatus().toString());
			break;
		default:
			comparator = Comparator.comparing(Providers::getProvider_id);
		}

		if (!ascending) {
			comparator = comparator.reversed();
		}

		approvedProviders.sort(comparator);
	}

	public boolean isHasNextPage() {
		return (currentPage + 1) * pageSize < approvedProviders.size();
	}

	public boolean isHasPrevPage() {
		return currentPage > 0;
	}

	public int getCurrentPage() {
		return currentPage + 1;
	}

	public String getSortField() {
		return sortField;
	}

	public boolean isAscending() {
		return ascending;
	}
}
