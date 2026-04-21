package com.prueba.approval_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;

    private String status; // PENDING, APPROVED, REJECTED

    private String otp;

    private String token;

    private LocalDateTime createdAt;

    private LocalDateTime actionAt;

    @ManyToOne
    @JoinColumn(name = "purchase_request_id")
    private PurchaseRequest purchaseRequest;
}