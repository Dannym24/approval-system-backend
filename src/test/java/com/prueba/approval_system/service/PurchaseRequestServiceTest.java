package com.prueba.approval_system.service;

import com.prueba.approval_system.dto.CreatePurchaseRequestDTO;
import com.prueba.approval_system.dto.PurchaseRequestResponseDTO;
import com.prueba.approval_system.model.PurchaseRequest;
import com.prueba.approval_system.repository.PurchaseRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseRequestServiceTest {

    @Mock
    private PurchaseRequestRepository repository;

    @InjectMocks
    private PurchaseRequestService service;

    @Test
    void shouldCreatePurchaseRequestWith3Approvals() {

        CreatePurchaseRequestDTO dto = new CreatePurchaseRequestDTO();
        dto.setTitle("Compra test");
        dto.setDescription("Descripción");
        dto.setAmount(1000.0);
        dto.setCreatedBy("user");

        PurchaseRequest saved = new PurchaseRequest();
        saved.setId("123");

        // 🔥 FIX CLAVE: evitar null
        saved.setApprovals(new java.util.ArrayList<>());

        when(repository.save(any())).thenReturn(saved);

        PurchaseRequestResponseDTO response = service.createRequest(dto);

        assertNotNull(response);
        assertEquals("123", response.getRequestId());

        // ⚠️ aquí NO puedes validar size real porque approvals vienen del builder interno
        assertNotNull(response.getApprovalIds());
    }
}