package com.prueba.approval_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalRequestDTO {

    private String approvalId;

    private String otp;

    private String decision; // APPROVED o REJECTED
}