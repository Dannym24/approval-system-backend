package com.prueba.approval_system.service;

import com.prueba.approval_system.dto.CreatePurchaseRequestDTO;
import com.prueba.approval_system.dto.PurchaseRequestResponseDTO;
import com.prueba.approval_system.model.Approval;
import com.prueba.approval_system.model.PurchaseRequest;
import com.prueba.approval_system.repository.PurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;

    public PurchaseRequestService(PurchaseRequestRepository purchaseRequestRepository) {
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    public PurchaseRequest getById(String id) {
        PurchaseRequest request = purchaseRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        request.getApprovals().size();

        return request;
    }

    public List<PurchaseRequest> getAll() {
        return purchaseRequestRepository.findAll();
    }

    public PurchaseRequestResponseDTO createRequest(CreatePurchaseRequestDTO dto) {

        // 1. Crear entidad principal
        PurchaseRequest request = buildPurchaseRequest(dto);

        // 2. Crear aprobaciones
        List<Approval> approvals = createApprovals(request);

        request.setApprovals(approvals);

        // 3. Guardar en BD
        PurchaseRequest saved = purchaseRequestRepository.save(request);

        // 4. Respuesta
        return buildResponse(saved);
    }

    // MÉTODOS PRIVADOS


    private PurchaseRequest buildPurchaseRequest(CreatePurchaseRequestDTO dto) {
        PurchaseRequest request = new PurchaseRequest();
        request.setTitle(dto.getTitle());
        request.setDescription(dto.getDescription());
        request.setAmount(dto.getAmount());
        request.setCreatedBy(dto.getCreatedBy());
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());
        return request;
    }

    private List<Approval> createApprovals(PurchaseRequest request) {

        List<Approval> approvals = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {

            Approval approval = new Approval();

            approval.setEmail("approver" + i + "@mail.com");
            approval.setStatus("PENDING");

            approval.setToken(generateToken());
            approval.setOtp(generateOtp());

            // AJUSTE: LINK DE APROBACIÓN (SIMULACIÓN EMAIL)
            String link = "http://localhost:3000/approve"
                    + "?approval_token=" + approval.getToken()
                    + "&approval_email=" + approval.getEmail();

            System.out.println("OTP generado: " + approval.getOtp());
            System.out.println("🔗 LINK APROBACIÓN: " + link);

            approval.setPurchaseRequest(request);

            approvals.add(approval);
        }

        return approvals;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private String generateOtp() {
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    private PurchaseRequestResponseDTO buildResponse(PurchaseRequest saved) {

        List<String> approvalIds = saved.getApprovals()
                .stream()
                .map(Approval::getId)
                .toList();

        return PurchaseRequestResponseDTO.builder()
                .requestId(saved.getId())
                .status(saved.getStatus())
                .message("Solicitud creada con aprobaciones")
                .approvalIds(approvalIds)
                .build();
    }
}