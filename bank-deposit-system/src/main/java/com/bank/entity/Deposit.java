package com.bank.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "deposits")
@Data
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "deposit_type_id", nullable = false)
    private DepositType depositType;

    @Column(name = "account_number", unique = true, nullable = false, length = 30)
    private String accountNumber;

    @Column(name = "open_date", nullable = false)
    private LocalDate openDate = LocalDate.now();

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "current_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentBalance;

    @Column(length = 20)
    private String status = "ACTIVE";
}