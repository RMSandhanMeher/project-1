<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<f:view>
	<h:form
		styleClass="max-w-md mx-auto mt-10 p-6 border rounded shadow space-y-4">
		<h2 class="text-2xl font-semibold mb-4 text-center text-blue-600">
			Download Appointment PDF</h2>

		<div class="text-center mt-4">
			<h:commandButton value="Download PDF"
				action="#{appointmentPdfController.downloadAppointmentPdf}"
				styleClass="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded" />
		</div>
	</h:form>
</f:view>
