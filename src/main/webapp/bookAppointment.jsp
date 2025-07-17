<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:view>
	<html>
<head>
<title>Book Appointment</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
	<div class="container mx-auto py-10 px-6">
		<h:form prependId="false">
			<div
				class="max-w-xl mx-auto bg-white rounded-lg shadow p-6 space-y-6">

				<!-- Page Title -->
				<h2 class="text-2xl font-bold text-center text-blue-700 mb-4">
					Book Appointment</h2>

				<!-- Availability ID Display -->
				<div>
					<label class="block text-gray-600 mb-1">Availability ID</label>
					<h:inputText value="#{appointmentController.availabilityId}"
						readonly="true"
						styleClass="w-full px-3 py-2 border border-gray-300 rounded" />
				</div>

				<!-- Load Available Slots Button -->
				<div>
					<h:commandButton value="Load Slots"
						action="#{appointmentController.loadAvailableSlots}"
						styleClass="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded" />
				</div>

				<!-- Slot Dropdown -->
				<div>
					<label class="block text-gray-600 mb-1">Select Slot</label>
					<h:selectOneMenu value="#{appointmentController.selectedSlot}"
						styleClass="w-full px-3 py-2 border border-gray-300 rounded">
						<f:selectItem itemLabel="-- Select --" itemValue="0" />
						<f:selectItems value="#{appointmentController.availableSlots}"
							var="slot" itemValue="#{slot.slotNumber}"
							itemLabel="#{slot.displayLabel}" />
					</h:selectOneMenu>

				</div>

				<!-- Success / Error Message -->
				<h:panelGroup rendered="#{not empty appointmentController.message}">
					<h:panelGroup layout="block"
						styleClass="#{appointmentController.success 
                                                  ? 'bg-green-100 text-green-800 border border-green-400' 
                                                  : 'bg-red-100 text-red-800 border border-red-400'} 
                                                  px-4 py-2 rounded mb-2">
						<h:outputText value="#{appointmentController.message}" />
					</h:panelGroup>
				</h:panelGroup>

				<!-- Book Button -->
				<div class="text-center pt-2">
					<h:commandButton value="Book Appointment"
						action="#{appointmentController.bookAppointment}"
						styleClass="bg-green-600 hover:bg-green-700 text-white px-6 py-2 rounded" />
				</div>
			</div>
		</h:form>
	</div>
</body>
	</html>
</f:view>