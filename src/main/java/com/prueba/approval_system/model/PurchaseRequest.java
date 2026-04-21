package com.prueba.approval_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PurchaseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;
    private String description;
    private Double amount;

    private String status; // PENDING, APPROVED, REJECTED, COMPLETED

    private LocalDateTime createdAt;

    private String createdBy;
    private String evidencePdfPath;
    @OneToMany(mappedBy = "purchaseRequest", cascade = CascadeType.ALL)
    private List<Approval> approvals;
}