<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>

<f:view>
	<html>
<head>
<title>Recipient Appointments</title>
<style>
.slots-table-component>tbody>tr>td {
	text-align: center;
}
</style>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen p-6">
	<div class="max-w-5xl mx-auto bg-white p-6 rounded shadow">

		<h1 class="text-2xl font-bold mb-6 text-blue-700">My Appointments</h1>

		<h:form id="appointmentForm">

			<!-- Loading overlay -->
			<div id="loadingOverlay"
				style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(255, 255, 255, 0.8); z-index: 9999; justify-content: center; align-items: center;">
				<div class="text-center">
					<svg class="animate-spin h-10 w-10 text-blue-500 mx-auto"
						xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
				<circle class="opacity-25" cx="12" cy="12" r="10"
							stroke="currentColor" stroke-width="4"></circle>
				<path class="opacity-75" fill="currentColor"
							d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"></path>
			</svg>
					<p class="mt-2 text-gray-700">Cancelling your appointment...</p>
				</div>
			</div>

			<!-- Filter Menu -->
			<div class="mb-4 flex items-center space-x-4">
				<label class="text-gray-700 font-medium">Filter:</label>
				<h:selectOneMenu
					value="#{recipientAppointmentController.filterType}"
					styleClass="border px-2 py-1 rounded"
					onchange="this.form.submit();">
					<f:selectItem itemLabel="Upcoming" itemValue="upcoming" />
					<f:selectItem itemLabel="Past" itemValue="past" />
				</h:selectOneMenu>
			</div>

			<!-- Table -->
			<h:dataTable
				value="#{recipientAppointmentController.filteredAppointments}"
				var="appt"
				styleClass="slots-table-component min-w-full table-auto border border-gray-300 text-sm mb-6 "
				rowClasses="bg-white even:bg-gray-50"
				columnClasses="px-4 py-2 border">

				<h:column>
					<f:facet name="header">
						<h:outputText value="Appointment Id" />
					</f:facet>
					<h:outputText value="#{appt.appointment_id}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="Doctor name" />
					</f:facet>
					<h:outputText
						value="#{appt.doctor != null ? appt.doctor.doctor_name : 'N/A'}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="Appointment on " />
					</f:facet>
					<h:outputText value="#{appt.start}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="Status" />
					</f:facet>
					<h:outputText value="#{appt.status}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="Notes " />
					</f:facet>
					<h:outputText value="#{empty appt.notes ? 'None' : appt.notes}" />
				</h:column>

				<h:column>
					<h:commandButton value="Cancel"
						rendered="#{appt.status eq 'BOOKED' || appt.status eq 'PENDING'}"
						onclick="return showLoadingAndConfirm();"
						action="#{recipientAppointmentController.cancelAppointment}"
						styleClass="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">
						<f:setPropertyActionListener
							target="#{recipientAppointmentController.selectedAppointment}"
							value="#{appt}" />
					</h:commandButton>
				</h:column>
			</h:dataTable>

			<!-- Empty State Message -->
			<h:panelGroup
				rendered="#{empty recipientAppointmentController.filteredAppointments}">
				<div class="text-gray-500 italic mt-4">No appointments found
					for this filter.</div>
			</h:panelGroup>

			<!-- Messages -->
			<h:messages globalOnly="true"
				infoClass="p-3 bg-green-100 text-green-700 border border-green-300 rounded mb-4"
				errorClass="p-3 bg-red-100 text-red-700 border border-red-300 rounded mb-4" />

		</h:form>

	</div>
</body>
<script>
	function showLoadingAndConfirm() {
		const confirmCancel = confirm('Are you sure you want to cancel this appointment?');
		if (confirmCancel) {
			document.getElementById("loadingOverlay").style.display = "flex";
			return true;
		}
		return false;
	}
</script>

	</html>
</f:view>
