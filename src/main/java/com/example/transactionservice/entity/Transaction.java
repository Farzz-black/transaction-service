package com.example.transactionservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_id")
    private Long transactionId;

    @NotNull
    private Long studentId;

    @NotNull
    private Double fee;

    @NotNull
    private String term;
}
