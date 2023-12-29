package com.project.kiranaBackendService.Service;

import com.project.kiranaBackendService.Model.Transaction;
import com.project.kiranaBackendService.Model.TransactionType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface KiranaService  {


    public Transaction addTransaction(Transaction transaction) throws IOException;
    public List<Transaction> getTransaction();
    public Optional<Transaction> getTransactionById(Long id);
    public Transaction updateTransaction(Transaction transaction) throws IOException;
    public void deleteTransaction(Long id);

    List<Transaction> getTransactionByDateAndType(LocalDate parse, TransactionType transactionType);

    List<Transaction> getTransactionByDate(LocalDate parse);

    List<Transaction> getTransactionByType(TransactionType transactionType);
}
