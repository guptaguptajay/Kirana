package com.project.kiranaBackendService.Service;

import com.google.gson.JsonObject;
import com.project.kiranaBackendService.Client.FxRateClient;
import com.project.kiranaBackendService.Dao.KiranaDao;
import com.project.kiranaBackendService.Exception.NotFoundException;
import com.project.kiranaBackendService.Model.Currency;
import com.project.kiranaBackendService.Model.Transaction;
import com.project.kiranaBackendService.Model.TransactionType;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KiranaServiceImpl implements KiranaService{


    @Autowired
    private KiranaDao kiranaDao;

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");




    @Override
    public Transaction addTransaction(Transaction transaction) throws IOException {
        if (transaction.getCurrency().equals(Currency.USD)){
            double rate = getUSDToINR();
            String newAmount = decimalFormat.format(transaction.getAmount()*rate);
            transaction.setAmount(Double.parseDouble(newAmount));
            transaction.setCurrency(Currency.INR);
        }
        Long id = transaction.getTransId();
        Optional<Transaction> txn =  kiranaDao.findById(id);
        if (txn.isPresent()) {
            throw new NotFoundException("Transaction already exists for : " + id);
        } else {


            return kiranaDao.save(transaction);
        }

    }

    private double getUSDToINR() throws IOException {
        JsonObject jsObject = FxRateClient.getAllRates();
        return jsObject.get("rates").getAsJsonObject().get("INR").getAsDouble();
    }

    @Override
    public List<Transaction> getTransaction() {
        return kiranaDao.findAll();
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return findTxnById(id);


    }

    private Optional<Transaction> findTxnById(Long id) {
        Optional<Transaction> transaction =  kiranaDao.findById(id);
        if (transaction.isPresent()) {
            return transaction;
        } else {

            throw new NotFoundException("Transaction not found for the id: " + id);
        }
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) throws IOException {
        if (transaction.getCurrency().equals(Currency.USD)){
            double rate = getUSDToINR();
            transaction.setAmount(transaction.getAmount()*rate);
            transaction.setCurrency(Currency.INR);
        }
        findTxnById(transaction.getTransId());
        ;
        return kiranaDao.save(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        findTxnById(id);
        Transaction transaction = kiranaDao.getReferenceById(id);
        kiranaDao.delete(transaction);
    }

    @Override
    public List<Transaction> getTransactionByDateAndType(LocalDate parse, TransactionType transactionType) {
        List<Transaction> transactions = new ArrayList<>();
        kiranaDao.findAll().forEach(
                transaction -> {
                    if (transaction.getDate().equals(parse) && transaction.getType().equals(transactionType)){
                        transactions.add(transaction);
                    }
                }
        );
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionByDate(LocalDate parse) {
        List<Transaction> transactions = new ArrayList<>();
        kiranaDao.findAll().forEach(
                transaction -> {
                    if (transaction.getDate().equals(parse)){
                        transactions.add(transaction);
                    }
                }
        );
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionByType(TransactionType transactionType) {
        List<Transaction> transactions = new ArrayList<>();
        kiranaDao.findAll().forEach(
                transaction -> {
                    if (transaction.getType().equals(transactionType)){
                        transactions.add(transaction);
                    }
                }
        );
        return transactions;
    }
}
