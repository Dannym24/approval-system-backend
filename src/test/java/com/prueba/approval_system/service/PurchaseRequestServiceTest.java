package com.prueba.approval_system.service;

import com.prueba.approval_system.dto.CreatePurchaseRequestDTO;
import com.prueba.approval_system.dto.PurchaseRequestResponseDTO;
import com.prueba.approval_system.model.Approval;
import com.prueba.approval_system.model.PurchaseRequest;
import com.prueba.approval_system.repository.PurchaseRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

        // 🔥 FIX IMPORTANTE: simular 3 approvals
        List<Approval> approvals = new ArrayList<>();

        Approval a1 = new Approval();
        a1.setId("1");

        Approval a2 = new Approval();
        a2.setId("2");

        Approval a3 = new Approval();
        a3.setId("3");

        approvals.add(a1);
        approvals.add(a2);
        approvals.add(a3);

        saved.setApprovals(approvals);

        when(repository.save(any())).thenReturn(saved);

        PurchaseRequestResponseDTO response = service.createRequest(dto);

        assertNotNull(response);
        assertEquals("123", response.getRequestId());

        // ✔ ahora sí valida los 3 approvals
        assertEquals(3, response.getApprovalIds().size());
        assertNotNull(response.getApprovalIds());
    }
}