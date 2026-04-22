package com.prueba.approval_system.service;

import com.prueba.approval_system.dto.ApprovalRequestDTO;
import com.prueba.approval_system.dto.ApprovalResponseDTO;
import com.prueba.approval_system.model.Approval;
import com.prueba.approval_system.model.PurchaseRequest;
import com.prueba.approval_system.provider.PdfGeneratorProvider;
import com.prueba.approval_system.repository.ApprovalRepository;
import com.prueba.approval_system.repository.PurchaseRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ApprovalServiceTest {

    @Mock
    private ApprovalRepository approvalRepository;

    @Mock
    private PurchaseRequestRepository purchaseRequestRepository;

    @Mock
    private PdfGeneratorProvider pdfGeneratorProvider;

    @InjectMocks
    private ApprovalService service;

    @Test
    void shouldApproveWhenOtpIsValid() {

        Approval approval = new Approval();
        approval.setId("1");
        approval.setOtp("123456");
        approval.setStatus("PENDING");

        PurchaseRequest request = new PurchaseRequest();
        request.setId("REQ1");
        approval.setPurchaseRequest(request);

        ApprovalRequestDTO dto = new ApprovalRequestDTO();
        dto.setApprovalId("1"); // aunque no se use
        dto.setOtp("123456");
        dto.setDecision("APPROVED");

        // Ajuste: mock para findAll()
        when(approvalRepository.findAll())
                .thenReturn(List.of(approval));

        when(purchaseRequestRepository.findById("REQ1"))
                .thenReturn(Optional.of(request));

        when(approvalRepository.findByPurchaseRequestId("REQ1"))
                .thenReturn(List.of(approval));

        ApprovalResponseDTO response = service.processApproval(dto);

        assertEquals("APPROVED", response.getStatus());
    }
    @Test
    void shouldFailWhenOtpIsInvalid() {

        Approval approval = new Approval();
        approval.setId("1");
        approval.setOtp("999999");

        PurchaseRequest request = new PurchaseRequest();
        request.setId("REQ1");
        approval.setPurchaseRequest(request);

        ApprovalRequestDTO dto = new ApprovalRequestDTO();
        dto.setApprovalId("1");
        dto.setOtp("123456");
        dto.setDecision("APPROVED");

        when(purchaseRequestRepository.findById("REQ1"))
                .thenReturn(Optional.of(request));

        when(approvalRepository.findByPurchaseRequestId("REQ1"))
                .thenReturn(List.of(approval));
        assertThrows(RuntimeException.class,
                () -> service.processApproval(dto));
    }
}