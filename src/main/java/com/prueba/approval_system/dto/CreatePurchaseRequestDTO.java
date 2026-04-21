package com.prueba.approval_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseRequestDTO {

    private String title;
    private String description;
    private Double amount;
    private String createdBy;
}