package com.project.kiranaBackendService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private String transId;
    private String amount;
    private String currency;
    private String type;
}
