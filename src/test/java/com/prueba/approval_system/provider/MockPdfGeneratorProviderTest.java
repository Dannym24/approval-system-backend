package com.prueba.approval_system.provider;

import com.prueba.approval_system.model.Approval;
import com.prueba.approval_system.model.PurchaseRequest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MockPdfGeneratorProviderTest {

    private final MockPdfGeneratorProvider pdfProvider = new MockPdfGeneratorProvider();

    @Test
    void shouldGeneratePdfFile() {
        PurchaseRequest request = new PurchaseRequest();
        request.setId("TEST123");
        request.setTitle("Compra Test");
        request.setDescription("Descripción Test");
        request.setAmount(500.0);
        request.setStatus("COMPLETED");
        request.setCreatedAt(LocalDateTime.now());

        Approval approval1 = new Approval();
        approval1.setEmail("user1@test.com");
        approval1.setStatus("APPROVED");
        approval1.setActionAt(LocalDateTime.now());

        Approval approval2 = new Approval();
        approval2.setEmail("user2@test.com");
        approval2.setStatus("PENDING");

        request.setApprovals(List.of(approval1, approval2));

        String path = pdfProvider.generateEvidencePdf(request);

        assertNotNull(path, "La ruta del PDF no debe ser null");

        File file = new File(path);
        assertTrue(file.exists(), "El archivo PDF debe existir");

        file.delete();
    }
}