package com.prueba.approval_system.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestResponseDTO {

    private String requestId;
    private String status;
    private String message;
    private List<String> approvalIds;
    private List<String> otpList;
}