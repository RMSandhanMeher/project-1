package com.infinite.controller;

import java.io.Serializable;
import java.util.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.infinite.dao.DoctorDaoImpl;
import com.infinite.model.Doctors;

public class DoctorController implements Serializable {

	private static final long serialVersionUID = 1L;

	private DoctorDaoImpl dao = new DoctorDaoImpl();

	private List<Doctors> fullList = new ArrayList<>();
	private List<Doctors> paginatedList = new ArrayList<>();

	private int currentPage = 0;
	private final int pageSize = 10;

	private String sortField = "doctor_name";
	private boolean ascending = true;

	private String searchField = "doctor_name";
	private String searchKeyword = "";

	private boolean initialized = false;

	public void init() {
		if (initialized)
			return;
		initialized = true;

		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			String providerId = (String) session.getAttribute("selectedProviderId");

			if (providerId != null) {
				fullList = dao.getApprovedDoctorsByProviderId(providerId);
				applyFilterSortAndPagination();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String selectDoctor(String doctorId) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
			session.setAttribute("selectedDoctorId", doctorId);
			return "doctorAvailabilityList?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Doctors searchADoctorById(String doctorId) {
		return dao.searchADoctorById(doctorId);
	}

	private void applyFilterSortAndPagination() {
		// 1. Filter
		List<Doctors> filtered = new ArrayList<>();
		for (Doctors d : fullList) {
			String fieldValue = "";
			if ("doctor_name".equals(searchField)) {
				fieldValue = d.getDoctor_name();
			} else if ("specialization".equals(searchField)) {
				fieldValue = d.getSpecialization();
			}
			if (fieldValue != null && fieldValue.toLowerCase().contains(searchKeyword.toLowerCase())) {
				filtered.add(d);
			}
		}

		// 2. Sort
		filtered.sort((d1, d2) -> {
			String val1 = "", val2 = "";
			if ("doctor_name".equals(sortField)) {
				val1 = d1.getDoctor_name();
				val2 = d2.getDoctor_name();
			} else if ("specialization".equals(sortField)) {
				val1 = d1.getSpecialization();
				val2 = d2.getSpecialization();
			}
			return ascending ? val1.compareToIgnoreCase(val2) : val2.compareToIgnoreCase(val1);
		});

		// 3. Pagination
		int fromIndex = currentPage * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, filtered.size());
		paginatedList = filtered.subList(fromIndex, toIndex);
	}

	public List<Doctors> getDoctorList() {
		if (!initialized) {
			init();
		}
		if (paginatedList == null) {
			applyFilterSortAndPagination();
		}
		return paginatedList;
	}

	public void nextPage() {
		currentPage++;
		applyFilterSortAndPagination();
	}

	public void prevPage() {
		currentPage--;
		applyFilterSortAndPagination();
	}

	public boolean isHasNextPage() {
		if (fullList == null || fullList.isEmpty())
			return false;
		int totalFiltered = (int) fullList.stream().filter(this::matchesFilter).count();
		return (currentPage + 1) * pageSize < totalFiltered;
	}

	public boolean isHasPrevPage() {
		return currentPage > 0;
	}

	public void setSortField(String field) {
		if (sortField.equals(field)) {
			ascending = !ascending;
		} else {
			sortField = field;
			ascending = true;
		}
		currentPage = 0;
		applyFilterSortAndPagination();
	}

	private boolean matchesFilter(Doctors d) {
		String fieldValue = "";
		if ("doctor_name".equals(searchField)) {
			fieldValue = d.getDoctor_name();
		} else if ("specialization".equals(searchField)) {
			fieldValue = d.getSpecialization();
		}
		return fieldValue != null && fieldValue.toLowerCase().contains(searchKeyword.toLowerCase());
	}

	public void search() {
		currentPage = 0;
		applyFilterSortAndPagination();
	}

	// Getters and Setters

	public String getSortField() {
		return sortField;
	}

	public boolean isAscending() {
		return ascending;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
