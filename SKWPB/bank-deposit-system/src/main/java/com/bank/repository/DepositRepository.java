package com.bank.repository;

import com.bank.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

    // Найти вклады по ID клиента
    List<Deposit> findByClientId(Long clientId);

    // Найти вклады по статусу
    List<Deposit> findByStatus(String status);

    // Получить общую сумму всех вкладов
    @Query("SELECT SUM(d.amount) FROM Deposit d")
    BigDecimal getTotalAmount();
}