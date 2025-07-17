<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<f:view>
	<html>
<head>
<title>Appointment Confirmation</title>
<!-- Tailwind CSS CDN -->
<script src="https://cdn.tailwindcss.com"></script>
<!-- Font Awesome for icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
.confirmation-card {
	transition: all 0.3s ease;
	box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px
		rgba(0, 0, 0, 0.05);
}

.btn-primary {
	transition: all 0.2s ease;
}

.btn-primary:hover {
	transform: translateY(-1px);
	box-shadow: 0 7px 14px rgba(0, 0, 0, 0.1);
}
</style>
</head>
<body
	class="bg-gradient-to-br from-blue-50 to-gray-50 min-h-screen flex items-center">
	<div class="container mx-auto px-4">
		<div class="max-w-md mx-auto">
			<div class="confirmation-card bg-white rounded-xl overflow-hidden">
				<!-- Header with gradient background -->
				<div
					class="bg-gradient-to-r from-blue-600 to-blue-800 px-6 py-2 text-center">
					<div
						class="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-white/10 mb-2">
						<i class="fas fa-check-circle text-white text-2xl"></i>
					</div>
					<h1 class="text-2xl font-bold text-white">Appointment
						Confirmed!</h1>
				</div>

				<!-- Content area -->
				<div class="p-6 space-y-6">
					<!-- Success message -->
					<div
						class="bg-green-50 border-l-4 border-green-500 rounded-r px-4 py-3 flex items-start">
						<div class="flex-shrink-0 pt-1">
							<i class="fas fa-check-circle text-green-500"></i>
						</div>
						<div class="ml-3">
							<p class="text-sm font-medium text-green-800">
								<h:outputText
									value="#{sessionScope.confirmationMessage}" />
							</p>
						</div>
					</div>
					<!-- Additional information cards -->
					<div class="grid gap-4">
						<div class="bg-blue-50 rounded-lg p-4 border border-blue-100">
							<h3 class="text-sm font-medium text-blue-800 mb-2">What's
								Next?</h3>
							<ul class="list-disc list-inside text-sm text-gray-600 space-y-1">
								<li>You'll receive a confirmation email</li>
								<li>Arrive 15 minutes before your appointment</li>
								<li>Bring your ID and insurance card</li>
							</ul>
						</div>
						<div class="bg-gray-50 rounded-lg p-4 border border-gray-200">
							<h3 class="text-sm font-medium text-gray-700 mb-2">Need
								Help?</h3>
							<p class="text-sm text-gray-600">
								Contact our support team at <span class="text-blue-600"><h:outputText
										value="#{initParam['providerEmail']}" /></span> or call
								<h:outputText styleClass="font-medium"
									value="or call #{initParam['contact']}" />
							</p>
						</div>
					</div>
					<h:form>
						<!-- Action buttons -->
						<div class="flex flex-col sm:flex-row justify-center gap-3 ">
							<h:commandButton value="Book Another Appointment"
								action="doctorAvailabilityList"
								styleClass="btn-primary flex-1 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2" />

							<h:commandButton value="Back to Home" action="providerList"
								styleClass="flex-1 px-4 py-2 bg-white border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2" />
						</div>
					</h:form>
				</div>
			</div>
		</div>
	</div>
</body>
	</html>
</f:view>