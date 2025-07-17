<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<f:view>
	<html>
<head>
<title>Approved Providers</title>
<!-- Tailwind CSS CDN -->
<script src="https://cdn.tailwindcss.com"></script>
<!-- Font Awesome CDN for icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
.sortable-header {
	transition: all 0.2s ease;
}

.sortable-header:hover {
	background-color: #f3f4f6;
}

.pagination-btn:disabled {
	opacity: 0.5;
	cursor: not-allowed;
}
</style>
</head>
<body class="bg-gray-50">
	<div class="container mx-auto px-4 py-8">
		<h:form>
			<div class="bg-white rounded-lg shadow-md p-6">
				<!-- Search Section -->
				<div class="flex flex-col md:flex-row md:items-center gap-4 mt-4">
					<h:selectOneMenu value="#{providerController.searchField}"
						styleClass="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full md:w-1/4">
						<f:selectItem itemValue="all" itemLabel="All" />
						<f:selectItem itemValue="provider_name" itemLabel="By Name" />
						<f:selectItem itemValue="hospital_name" itemLabel="By Hospital" />
						<f:selectItem itemValue="city" itemLabel="By City" />
						<f:selectItem itemValue="state" itemLabel="By State" />
					</h:selectOneMenu>

					<h:inputText value="#{providerController.searchText}"
						styleClass="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full md:w-1/2" />
					<h:commandButton value="Search"
						action="#{providerController.searchProviders}"
						styleClass="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500" />
				</div>

				<!-- Header Section -->
				<div
					class="flex flex-col md:flex-row md:items-center md:justify-between mb-6">
					<h1 class="text-2xl font-bold text-gray-800">
						<h:outputText
							value="Approved Providers - Page #{providerController.currentPage}" />
					</h1>

					<!-- Page Size Dropdown -->
					<div class="mt-4 md:mt-0 flex items-center">
						<h:outputLabel for="pageSize" value="Page Size:"
							styleClass="mr-2 text-gray-700" />
						<h:selectOneMenu id="pageSize"
							value="#{providerController.pageSize}"
							styleClass="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
							onchange="submit()">
							<f:selectItem itemValue="5" itemLabel="5" />
							<f:selectItem itemValue="10" itemLabel="10" />
							<f:selectItem itemValue="20" itemLabel="20" />
						</h:selectOneMenu>
					</div>
				</div>

				<!-- Providers Table -->
				<div class="overflow-x-auto">
					<h:dataTable
						value="#{providerController.paginatedApprovedProviders}" var="p"
						styleClass="min-w-full">
						<!-- Header row with colorful styling -->
						<f:facet name="header">
							<tr class="bg-gradient-to-r from-blue-600 to-blue-800">
								<th colspan="8"
									class="px-6 py-4 text-left text-sm font-bold text-white uppercase tracking-wider">
									Approved Providers List</th>
							</tr>
						</f:facet>



						<h:column>
							<f:facet name="header">
								<h:commandLink
									action="#{providerController.sortBy('provider_name')}"
									styleClass="flex items-center justify-between px-6 py-3 text-left text-xs font-bold text-white uppercase tracking-wider bg-blue-500 hover:bg-blue-600">
									<h:outputText value="Name" />
									<i class="fas fa-sort ml-2"></i>
								</h:commandLink>
							</f:facet>
							<div
								class="px-6 py-4 whitespace-nowrap text-sm text-gray-800 bg-blue-50 odd:bg-blue-100">
								<h:outputText value="#{p.provider_name}" />
							</div>
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:commandLink
									action="#{providerController.sortBy('hospital_name')}"
									styleClass="flex items-center justify-between px-6 py-3 text-left text-xs font-bold text-white uppercase tracking-wider bg-blue-500 hover:bg-blue-600">
									<h:outputText value="Hospital" />
									<i class="fas fa-sort ml-2"></i>
								</h:commandLink>
							</f:facet>
							<div
								class="px-6 py-4 whitespace-nowrap text-sm text-gray-800 bg-blue-50 odd:bg-blue-100">
								<h:outputText value="#{p.hospital_name}" />
							</div>
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:commandLink action="#{providerController.sortBy('email')}"
									styleClass="flex items-center justify-between px-6 py-3 text-left text-xs font-bold text-white uppercase tracking-wider bg-blue-500 hover:bg-blue-600">
									<h:outputText value="Email" />
									<i class="fas fa-sort ml-2"></i>
								</h:commandLink>
							</f:facet>
							<div
								class="px-6 py-4 whitespace-nowrap text-sm text-gray-800 bg-blue-50 odd:bg-blue-100">
								<h:outputText value="#{p.email}" />
							</div>
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:commandLink action="#{providerController.sortBy('city')}"
									styleClass="flex items-center justify-between px-6 py-3 text-left text-xs font-bold text-white uppercase tracking-wider bg-blue-500 hover:bg-blue-600">
									<h:outputText value="City" />
									<i class="fas fa-sort ml-2"></i>
								</h:commandLink>
							</f:facet>
							<div
								class="px-6 py-4 whitespace-nowrap text-sm text-gray-800 bg-blue-50 odd:bg-blue-100">
								<h:outputText value="#{p.city}" />
							</div>
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:commandLink action="#{providerController.sortBy('state')}"
									styleClass="flex items-center justify-between px-6 py-3 text-left text-xs font-bold text-white uppercase tracking-wider bg-blue-500 hover:bg-blue-600">
									<h:outputText value="State" />
									<i class="fas fa-sort ml-2"></i>
								</h:commandLink>
							</f:facet>
							<div
								class="px-6 py-4 whitespace-nowrap text-sm text-gray-800 bg-blue-50 odd:bg-blue-100">
								<h:outputText value="#{p.state}" />
							</div>
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:commandLink action="#{providerController.sortBy('zip_code')}"
									styleClass="flex items-center justify-between px-6 py-3 text-left text-xs font-bold text-white uppercase tracking-wider bg-blue-500 hover:bg-blue-600">
									<h:outputText value="Zip Code" />
									<i class="fas fa-sort ml-2"></i>
								</h:commandLink>
							</f:facet>
							<div
								class="px-6 py-4 whitespace-nowrap text-sm text-gray-800 bg-blue-50 odd:bg-blue-100">
								<h:outputText value="#{p.zip_code}" />
							</div>
						</h:column>
						<!-- Column headers with colorful styling -->
						<h:column>
							<!-- Stylish Header with Label and Sort Icon -->
							<f:facet name="header">
								<h:commandLink
									styleClass="flex items-center justify-between px-4 py-2 text-xs font-bold text-white uppercase tracking-wide bg-blue-600 hover:bg-blue-700 rounded">
									<h:outputText value="Select The Provider" />

								</h:commandLink>
							</f:facet>

							<!-- Row with a clean Select action -->
							<div class="text-center py-2">
								<h:commandLink value="Select"
									action="#{providerController.goToDoctorList(p.provider_id)}"
									styleClass="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600 transition duration-150 text-sm" />
							</div>
						</h:column>

					</h:dataTable>
				</div>

				<!-- Pagination Controls -->
				<div class="flex items-center justify-between mt-6">
					<h:commandButton value="⟵ Previous"
						styleClass="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 pagination-btn"
						action="#{providerController.prevPage}"
						rendered="#{providerController.hasPrevPage}" />

					<h:outputText value="Page #{providerController.currentPage}"
						styleClass="text-sm text-gray-700" />

					<h:commandButton value="Next ⟶"
						styleClass="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 pagination-btn"
						action="#{providerController.nextPage}"
						rendered="#{providerController.hasNextPage}" />
				</div>
			</div>
		</h:form>
	</div>
</body>
	</html>
</f:view>