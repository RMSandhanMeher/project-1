<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<f:view>
	<html>
<head>
<title>Book Appointment</title>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
<style>
.form-input:focus {
	box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.3);
}

.btn-primary {
	transition: all 0.2s ease;
}

.btn-primary:hover {
	transform: translateY(-1px);
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
</style>
</head>
<body class="bg-gray-50">
	<div class="container mx-auto px-4 py-8">
		<h:form prependId="false">
			<div
				class="max-w-2xl mx-auto bg-white rounded-xl shadow-md overflow-hidden">

				<!-- Header -->
				<div class="bg-gradient-to-r from-blue-600 to-blue-800 px-6 py-4">
					<h2 class="text-2xl font-bold text-white text-center">
						<i class="fas fa-calendar-check mr-2"></i> Book Your Appointment
					</h2>
				</div>

				<!-- Global Messages -->
				<h:messages globalOnly="true"
					styleClass="mb-4 p-3 bg-red-100 text-red-700 rounded border border-red-300" />

				<div class="p-6 space-y-6">

					<!-- Doctor Info -->
					<div class="bg-blue-50 rounded-lg p-4 border border-blue-100">
						<div class="flex items-center mb-2">
							<i class="fas fa-user-md text-blue-600 mr-2 text-xl"></i>
							<h3 class="text-lg font-semibold text-blue-700">Doctor
								Information</h3>
						</div>
						<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
							<div>
								<label class="block text-sm font-medium text-gray-500">Name</label>
								<p class="mt-1 text-sm text-gray-900 font-medium">
									<h:outputText
										value="#{doctorProviderController.doctor.doctor_name}" />
								</p>
							</div>
							<div>
								<label class="block text-sm font-medium text-gray-500">Specialization</label>
								<p class="mt-1 text-sm text-gray-900 font-medium">
									<h:outputText
										value="#{doctorProviderController.doctor.specialization}" />
								</p>
							</div>
						</div>
					</div>

					<!-- Appointment Date -->
					<div>
						<label for="customDate"
							class="block text-sm font-medium text-gray-700 mb-1"> <i
							class="fas fa-calendar-day text-blue-500 mr-1"></i> Appointment
							Date (yyyy-MM-dd)
						</label>
						<div class="flex gap-4 items-center">
							<h:inputText id="customDate"
								value="#{doctorProviderController.customDate}"
								styleClass="form-input w-full border border-gray-300 rounded-md"
								required="true" requiredMessage="Please enter a date" />
							<h:commandButton value="Find Slots"
								action="#{doctorProviderController.findAvailabilityForDate}"
								styleClass="btn-primary px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700" />
						</div>
					</div>

					<!-- Slot Selection -->
					<div>
						<label for="selectedAvailabilityId"
							class="block text-sm font-medium text-gray-700 mb-1"> <i
							class="fas fa-clock text-blue-500 mr-1"></i> Select Time Slot
						</label>
						<h:selectOneMenu id="selectedAvailabilityId"
							value="#{doctorProviderController.selectedAvailabilityId}"
							styleClass="form-input block w-full border border-gray-300 rounded-md">
							<f:selectItem itemLabel="-- Select Slot --" itemValue="" />
							<f:selectItems value="#{doctorProviderController.slotOptions}" />
						</h:selectOneMenu>
					</div>

					<!-- Notes -->
					<div>
						<label for="notes"
							class="block text-sm font-medium text-gray-700 mb-1"> <i
							class="fas fa-notes-medical text-blue-500 mr-1"></i> Additional
							Notes (optional)
						</label>
						<h:inputTextarea id="notes"
							value="#{doctorProviderController.notes}" rows="4"
							styleClass="form-input block w-full px-3 py-2 border border-gray-300 rounded-md" />
					</div>

					<!-- Action Buttons -->
					<div class="flex justify-between pt-4">
						<h:commandButton value="Cancel" action="index?faces-redirect=true"
							styleClass="btn-primary px-4 py-2 bg-gray-500 text-white rounded-md hover:bg-gray-600" />
						<h:commandButton value="Book Appointment"
							action="#{doctorProviderController.bookAppointment}"
							styleClass="btn-primary px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700" />
					</div>

				</div>
			</div>
		</h:form>
	</div>
</body>
	</html>
</f:view>
