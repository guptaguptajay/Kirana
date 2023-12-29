package com.project.kiranaBackendService.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @NonNull
    private Long transId;
    private LocalDate date;
    private double amount;
    private Currency currency;
    private TransactionType type;
}
