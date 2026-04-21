package com.prueba.approval_system.provider;

import com.prueba.approval_system.model.PurchaseRequest;

public interface PdfGeneratorProvider {
    String generateEvidencePdf(PurchaseRequest request);
}
