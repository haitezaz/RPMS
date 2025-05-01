package backend;


import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfWriter;

@SuppressWarnings("unused")
public class ReportPDFGenerator {

    public void generatePatientReport(Patient patient, String filePath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            System.out.println("Document opened successfully");


            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
            Paragraph title = new Paragraph("Patient Report", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Patient Details
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Name: " + patient.getfirstName() + " " + patient.getlastName(), boldFont));
            document.add(new Paragraph("Email: " + patient.getEmail(), boldFont));
            document.add(new Paragraph("Phone: " + patient.getPhoneNumber(), boldFont));
            document.add(new Paragraph("Gender: " + patient.getGender(), boldFont));
            document.add(new Paragraph("Age: " + patient.getAge(), boldFont));
            document.add(new Paragraph("Address: " + patient.getAddress(), boldFont));
            document.add(new Paragraph("\n"));

            // Medical History
            document.add(new Paragraph("Medical History:", boldFont));
            for (Feedback record : patient.getMedicalHistory().getFeedbackRecords()) {
                document.add(new Paragraph(record.toString(), boldFont));
            }
            document.add(new Paragraph("\n"));

            // Vitals
            document.add(new Paragraph("Vitals:", boldFont));
            for (VitalSign vital : patient.getVitals().getVitals()) {
                document.add(new Paragraph("Blood Pressure: " + vital.getBloodPressure(), boldFont));
                document.add(new Paragraph("Temperature: " + vital.getTemperature(), boldFont));
                document.add(new Paragraph("Heart Rate: " + vital.getHeartRate(), boldFont));
                document.add(new Paragraph("\n"));
            }

        } catch (DocumentException | IOException e) {
            System.out.println("Error occurred while generating PDF: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            document.close();
            System.out.println("Document closed successfully");
        }
    }
}
