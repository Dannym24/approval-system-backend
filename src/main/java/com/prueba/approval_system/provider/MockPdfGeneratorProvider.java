package com.prueba.approval_system.provider;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.prueba.approval_system.model.Approval;
import com.prueba.approval_system.model.PurchaseRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class MockPdfGeneratorProvider implements PdfGeneratorProvider {

    @Override
    public String generateEvidencePdf(PurchaseRequest request) {

        try {
            String path = "mock/pdfs/request-" + request.getId() + ".pdf";

            File folder = new File("mock/pdfs/");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));

            document.open();

            document.add(new Paragraph("SOLICITUD DE COMPRA"));
            document.add(new Paragraph("----------------------------"));

            document.add(new Paragraph("Título: " + request.getTitle()));
            document.add(new Paragraph("Descripción: " + request.getDescription()));
            document.add(new Paragraph("Monto: " + request.getAmount()));
            document.add(new Paragraph("Estado: " + request.getStatus()));
            document.add(new Paragraph("Fecha: " + request.getCreatedAt()));

            document.add(new Paragraph(" "));

            document.add(new Paragraph("APROBADORES:"));

            for (Approval a : request.getApprovals()) {
                document.add(new Paragraph(
                        a.getEmail() + " - " + a.getStatus() +
                                " - " + (a.getActionAt() != null ? a.getActionAt() : "Pendiente")
                ));
            }

            document.close();

            System.out.println("PDF REAL generado en: " + path);

            return path;

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }
}