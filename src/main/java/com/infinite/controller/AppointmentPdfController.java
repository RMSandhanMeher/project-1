package com.infinite.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;

public class AppointmentPdfController {

	public void downloadAppointmentPdf() throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=appointment.pdf");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4, 50, 50, 50, 50); // Add margin
		PdfWriter.getInstance(document, baos);
		document.open();

		// Define fonts
		Font titleFont = new Font(Font.HELVETICA, 22, Font.BOLD, new Color(33, 150, 243)); // Tailwind blue-500
		Font subtitleFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(55, 65, 81)); // Gray-700
		Font normalFont = new Font(Font.HELVETICA, 12);
		Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY);

		// Title
		Paragraph title = new Paragraph("Appointment Confirmation", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingAfter(10f);
		document.add(title);

		// Divider line
		LineSeparator separator = new LineSeparator();
		separator.setLineColor(new Color(229, 231, 235)); // Tailwind gray-200
		document.add(separator);
		document.add(Chunk.NEWLINE);

		// Card-style background
		PdfPTable card = new PdfPTable(1);
		card.setWidthPercentage(100);
		PdfPCell cardCell = new PdfPCell();
		cardCell.setBackgroundColor(new Color(250, 250, 250)); // gray-50
		cardCell.setPadding(20f);
		cardCell.setBorder(Rectangle.BOX);
		cardCell.setBorderColor(new Color(209, 213, 219)); // gray-300
		cardCell.setBorderWidth(1.5f);

		// Inner table
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);

		table.addCell(getCell("Doctor:", subtitleFont, true));
		table.addCell(getCell("Dr. Alok Nair", normalFont, false));

		table.addCell(getCell("Provider:", subtitleFont, true));
		table.addCell(getCell("AIIMS Delhi", normalFont, false));

		table.addCell(getCell("Date:", subtitleFont, true));
		table.addCell(getCell("2025-06-18", normalFont, false));

		table.addCell(getCell("Status:", subtitleFont, true));
		table.addCell(getCell("Pending", normalFont, false));

		cardCell.addElement(table);
		card.addCell(cardCell);
		document.add(card);

		// Footer
		document.add(Chunk.NEWLINE);
		Paragraph footer = new Paragraph("Thank you for using HealthSure. This is a system-generated confirmation.",
				footerFont);
		footer.setAlignment(Element.ALIGN_CENTER);
		document.add(footer);

		document.close();

		ServletOutputStream out = response.getOutputStream();
		baos.writeTo(out);
		out.flush();
		facesContext.responseComplete();
	}

	private PdfPCell getCell(String text, Font font, boolean isLabel) {
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setPadding(10f);
		cell.setBackgroundColor(isLabel ? new Color(243, 244, 246) : Color.WHITE); // Label = gray-100
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
}
