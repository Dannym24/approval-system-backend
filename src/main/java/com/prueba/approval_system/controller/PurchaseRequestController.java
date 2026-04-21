package com.prueba.approval_system.controller;

import com.prueba.approval_system.dto.ApprovalRequestDTO;
import com.prueba.approval_system.dto.ApprovalResponseDTO;
import com.prueba.approval_system.dto.CreatePurchaseRequestDTO;
import com.prueba.approval_system.dto.PurchaseRequestResponseDTO;
import com.prueba.approval_system.model.PurchaseRequest;
import com.prueba.approval_system.service.PurchaseRequestService;
import com.prueba.approval_system.service.ApprovalService; // 👈 FALTABA
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/purchase-requests")
public class PurchaseRequestController {

    private final PurchaseRequestService purchaseRequestService;
    private final ApprovalService approvalService;

    public PurchaseRequestController(
            PurchaseRequestService purchaseRequestService,
            ApprovalService approvalService // 👈 FALTABA
    ) {
        this.purchaseRequestService = purchaseRequestService;
        this.approvalService = approvalService; // 👈 FALTABA
    }

    @PostMapping
    public ResponseEntity<PurchaseRequestResponseDTO> createRequest(
            @RequestBody CreatePurchaseRequestDTO dto
    ) {
        PurchaseRequestResponseDTO response = purchaseRequestService.createRequest(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approval")
    public ResponseEntity<ApprovalResponseDTO> approveRequest(
            @RequestBody ApprovalRequestDTO dto
    ) {
        ApprovalResponseDTO response = approvalService.processApproval(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/evidencia.pdf")
    public ResponseEntity<String> downloadEvidence(@PathVariable String id) {

        PurchaseRequest request = purchaseRequestService.getById(id);

        if (request.getEvidencePdfPath() == null) {
            return ResponseEntity
                    .badRequest()
                    .body("El PDF aún no ha sido generado. El proceso no está COMPLETADO.");
        }

        return ResponseEntity.ok(request.getEvidencePdfPath());
    }
}