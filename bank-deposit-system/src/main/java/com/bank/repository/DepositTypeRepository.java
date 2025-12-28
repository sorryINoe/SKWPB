package com.bank.repository;

import com.bank.entity.DepositType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositTypeRepository extends JpaRepository<DepositType, Long> {
}