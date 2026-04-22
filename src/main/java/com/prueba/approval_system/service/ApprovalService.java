package com.prueba.approval_system.service;

import com.prueba.approval_system.dto.ApprovalRequestDTO;
import com.prueba.approval_system.dto.ApprovalResponseDTO;
import com.prueba.approval_system.model.Approval;
import com.prueba.approval_system.model.PurchaseRequest;
import com.prueba.approval_system.provider.PdfGeneratorProvider;
import com.prueba.approval_system.repository.ApprovalRepository;
import com.prueba.approval_system.repository.PurchaseRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final PdfGeneratorProvider pdfGeneratorProvider;

    public ApprovalService(
            ApprovalRepository approvalRepository,
            PurchaseRequestRepository purchaseRequestRepository,
            PdfGeneratorProvider pdfGeneratorProvider
    ) {
        this.approvalRepository = approvalRepository;
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.pdfGeneratorProvider = pdfGeneratorProvider;
    }

    @Transactional
    public ApprovalResponseDTO processApproval(ApprovalRequestDTO dto) {

        // 1. Buscar approval
        Approval approval = approvalRepository.findAll()
                .stream()
                .filter(a -> a.getOtp().equals(dto.getOtp()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Approval no encontrado"));

        // 2. Validar OTP
        validateOtp(approval, dto.getOtp());

        // 3. Actualizar estado del approval
        updateApprovalStatus(approval, dto.getDecision());

        // 4. Guardar approval
        approvalRepository.save(approval);

        // 5. Actualizar estado de la solicitud
        PurchaseRequest request = purchaseRequestRepository
                .findById(approval.getPurchaseRequest().getId())
                .orElseThrow(() -> new RuntimeException("Request no encontrado"));

        updatePurchaseRequestStatus(request);

        // 6. Respuesta
        return buildResponse(approval);
    }

    // MÉTODOS PRIVADOS

    private void validateOtp(Approval approval, String otp) {
        if (!approval.getOtp().equals(otp)) {
            throw new RuntimeException("OTP inválido");
        }
    }

    private void updateApprovalStatus(Approval approval, String decision) {
        approval.setStatus(decision);
        approval.setActionAt(LocalDateTime.now());
    }

    private void updatePurchaseRequestStatus(PurchaseRequest request) {

        List<Approval> approvals = approvalRepository
                .findByPurchaseRequestId(request.getId());

        boolean allApproved = true;
        boolean anyRejected = false;

        for (Approval a : approvals) {
            if ("REJECTED".equals(a.getStatus())) {
                anyRejected = true;
            }
            if (!"APPROVED".equals(a.getStatus())) {
                allApproved = false;
            }
        }

        if (anyRejected) {
            request.setStatus("REJECTED");
        } else if (allApproved) {

            request.setStatus("COMPLETED");

            String pdfPath = pdfGeneratorProvider.generateEvidencePdf(request);
            request.setEvidencePdfPath(pdfPath);

        } else {
            request.setStatus("PENDING");
        }

        purchaseRequestRepository.save(request);
    }

    private ApprovalResponseDTO buildResponse(Approval approval) {
        return new ApprovalResponseDTO(
                approval.getId(),
                approval.getStatus(),
                "Decisión registrada correctamente"
        );
    }
}