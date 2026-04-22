package com.prueba.approval_system.controller;

import com.prueba.approval_system.dto.ApprovalRequestDTO;
import com.prueba.approval_system.dto.ApprovalResponseDTO;
import com.prueba.approval_system.dto.CreatePurchaseRequestDTO;
import com.prueba.approval_system.dto.PurchaseRequestResponseDTO;
import com.prueba.approval_system.model.PurchaseRequest;
import com.prueba.approval_system.service.PurchaseRequestService;
import com.prueba.approval_system.service.ApprovalService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/purchase-requests")
public class PurchaseRequestController {

    private final PurchaseRequestService purchaseRequestService;
    private final ApprovalService approvalService;

    public PurchaseRequestController(
            PurchaseRequestService purchaseRequestService,
            ApprovalService approvalService
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

    @GetMapping
    public ResponseEntity<List<PurchaseRequest>> getAll() {
        return ResponseEntity.ok(purchaseRequestService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseRequest> getById(@PathVariable String id) {
        return ResponseEntity.ok(purchaseRequestService.getById(id));
    }

    @GetMapping("/{id}/evidencia.pdf")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String id) throws IOException {

        PurchaseRequest request = purchaseRequestService.getById(id);

        Path path = Paths.get(request.getEvidencePdfPath());

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=evidencia.pdf")
                .body(resource);
    }
}