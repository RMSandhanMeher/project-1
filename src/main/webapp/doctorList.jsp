<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<f:view>
	<html>
<head>
<title>Doctor List</title>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
</head>
<body class="bg-gray-50">
	<div class="container mx-auto px-4 py-8">
		<h:form>
			<div class="bg-white rounded-lg shadow-md p-6">

				<!-- Page Title -->
				<div class="flex justify-between items-center mb-6">
					<h1 class="text-2xl font-bold text-blue-800">
						Doctors for Provider ID:
						<h:outputText value="#{sessionScope.selectedProviderId}"
							styleClass="ml-2 text-gray-700" />
					</h1>
				</div>

				<!-- Search Section -->
				<div class="flex flex-col md:flex-row md:items-center gap-4 mb-6">
					<h:selectOneMenu value="#{doctorController.searchField}"
						styleClass="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full md:w-1/4">
						<f:selectItem itemLabel="By Name" itemValue="doctor_name" />
						<f:selectItem itemLabel="By Specialization"
							itemValue="specialization" />
					</h:selectOneMenu>

					<h:inputText value="#{doctorController.searchKeyword}"
						styleClass="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full md:w-1/2" />

					<h:commandButton value="Search" action="#{doctorController.search}"
						styleClass="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500" />
				</div>

				<!-- Doctors Table -->
				<div class="overflow-x-auto">
					<h:dataTable value="#{doctorController.doctorList}" var="doc"
						styleClass="min-w-full text-sm text-left"
						headerClass="bg-gradient-to-r from-blue-600 to-blue-800 text-white font-bold"
						rowClasses="bg-white even:bg-blue-50 odd:bg-blue-100">

						<h:column>
							<f:facet name="header">
								<h:commandLink
									action="#{doctorController.setSortField('doctor_id')}"
									styleClass="flex items-center justify-between px-4 py-2">
                                    ID
                                    <h:outputText
										value="#{doctorController.sortField eq 'doctor_id' ? (doctorController.ascending ? ' ▲' : ' ▼') : ''}" />
								</h:commandLink>
							</f:facet>
							<h:commandLink value="#{doc.doctor_id}"
								action="#{doctorController.selectDoctor(doc.doctor_id)}"
								styleClass="text-blue-600 hover:underline" />

						</h:column>

						<h:column>
							<f:facet name="header">
								<h:commandLink
									action="#{doctorController.setSortField('doctor_name')}"
									styleClass="flex items-center justify-between px-4 py-2">
                                    Name
                                    <h:outputText
										value="#{doctorController.sortField eq 'doctor_name' ? (doctorController.ascending ? ' ▲' : ' ▼') : ''}" />
								</h:commandLink>
							</f:facet>
							<h:outputText value="#{doc.doctor_name}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:commandLink
									action="#{doctorController.setSortField('specialization')}"
									styleClass="flex items-center justify-between px-4 py-2">
                                    Specialization
                                    <h:outputText
										value="#{doctorController.sortField eq 'specialization' ? (doctorController.ascending ? ' ▲' : ' ▼') : ''}" />
								</h:commandLink>
							</f:facet>
							<h:outputText value="#{doc.specialization}" />
						</h:column>
					</h:dataTable>
				</div>
				<!-- Pagination -->
				<div class="flex justify-between items-center mt-6">
					<h:commandButton value="⟵ Previous"
						action="#{doctorController.prevPage}"
						rendered="#{doctorController.hasPrevPage}"
						styleClass="px-4 py-2 bg-gray-300 hover:bg-gray-400 rounded" />

					<h:outputText value="Page #{doctorController.currentPage}"
						styleClass="text-sm text-gray-700" />

					<h:commandButton value="Next ⟶"
						action="#{doctorController.nextPage}"
						rendered="#{doctorController.hasNextPage}"
						styleClass="px-4 py-2 bg-blue-500 text-white hover:bg-blue-600 rounded" />
				</div>
			</div>
		</h:form>
	</div>
</body>
	</html>
</f:view>