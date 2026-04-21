package com.prueba.approval_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ApprovalResponseDTO {

    private String approvalId;

    private String status;

    private String message;
}
