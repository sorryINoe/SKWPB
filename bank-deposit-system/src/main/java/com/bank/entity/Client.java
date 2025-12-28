package com.bank.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(unique = true, nullable = false, length = 50)
    private String passport;

    @Column(length = 30)
    private String phone;

    private String address;

    @Column(name = "birth_date")
    private LocalDate birthDate;
}