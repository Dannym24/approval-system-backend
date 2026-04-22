package com.prueba.approval_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String status;

    private String otp;

    private String token;

    private LocalDateTime createdAt;

    private LocalDateTime actionAt;

    @ManyToOne
    @JoinColumn(name = "purchase_request_id")
    @JsonIgnore
    private PurchaseRequest purchaseRequest;
}