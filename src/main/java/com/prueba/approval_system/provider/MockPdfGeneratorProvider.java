package com.prueba.approval_system.provider;

import com.prueba.approval_system.model.PurchaseRequest;
import org.springframework.stereotype.Service;

@Service
public class MockPdfGeneratorProvider implements PdfGeneratorProvider {

    @Override
    public String generateEvidencePdf(PurchaseRequest request) {

        String path = "/mock/pdfs/request-" + request.getId() + ".pdf";

        System.out.println("📄 Generando PDF de evidencia...");
        System.out.println("Solicitud: " + request.getTitle());
        System.out.println("Guardado en: " + path);

        return path;
    }
}