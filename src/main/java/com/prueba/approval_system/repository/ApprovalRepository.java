package com.prueba.approval_system.repository;

import com.prueba.approval_system.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, String> {
    List<Approval> findByPurchaseRequestId(String purchaseRequestId);
}
