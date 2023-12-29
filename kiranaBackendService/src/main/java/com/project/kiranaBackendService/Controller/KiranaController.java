package com.project.kiranaBackendService.Controller;

import com.project.kiranaBackendService.Exception.NotFoundException;
import com.project.kiranaBackendService.Model.Currency;
import com.project.kiranaBackendService.Model.Transaction;
import com.project.kiranaBackendService.Model.TransactionRequest;
import com.project.kiranaBackendService.Model.TransactionType;
import com.project.kiranaBackendService.Service.KiranaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class KiranaController {




    @Autowired
    private KiranaService kiranaService;

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");





    @PostMapping(value = "/addTransaction")
    public Transaction addTransaction(@RequestBody TransactionRequest request) throws IOException {
        Transaction transaction = validateTransaction(request);
        return kiranaService.addTransaction(transaction);
    }



    @GetMapping(value = "/getTransaction")
    public List<Transaction> getTransaction(@RequestParam(required = false) String date,
                                            @RequestParam(required = false) String type){
        try {
            if(date!=null && type!=null){
                return kiranaService.getTransactionByDateAndType(LocalDate.parse(date), TransactionType.valueOf(type));
            }
            else if(date!=null){
                return kiranaService.getTransactionByDate(LocalDate.parse(date));
            }
            else if(type!=null){
                return kiranaService.getTransactionByType(TransactionType.valueOf(type));
            }
        } catch (IllegalArgumentException e) {
            throw new NotFoundException(e.getMessage());
        }
        return kiranaService.getTransaction();

    }
    @GetMapping(value = "/getTransaction/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable String id) {
        try{
            return kiranaService.getTransactionById(Long.valueOf(id));
        }
        catch (NumberFormatException e){
            throw new NotFoundException("Transaction Id is not valid");
        }
    }
    @PutMapping(value = "/updateTransaction")
    public Transaction updateTransaction(@RequestBody TransactionRequest request) throws IOException {
        Transaction transaction = validateTransaction(request);
        return kiranaService.updateTransaction(transaction);
    }
    @DeleteMapping(value = "/deleteTransaction/{id}")
    public void deleteTransaction(@PathVariable String id) {
        try{
            kiranaService.deleteTransaction(Long.valueOf(id));
        }
        catch (NumberFormatException e){
            throw new NotFoundException("Transaction Id is not valid");
        }
    }

    private Transaction validateTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDate.now());
        if(request.getTransId()==null) {
            throw new NotFoundException("Transaction Id is null");
        } else {
            try{
                transaction.setTransId(Long.valueOf(request.getTransId()));
            }
            catch (NumberFormatException e){
                throw new NotFoundException("Transaction Id is not valid");
            }
        }

        if(request.getAmount()==null) {
            throw new NotFoundException("Amount is null");
        } else {
            try{
                transaction.setAmount(Double.valueOf(request.getAmount()));
            }
            catch (NumberFormatException e){
                throw new NotFoundException("Amount is not valid");
            }
        }
        if(request.getCurrency()==null) {
            throw new NotFoundException("Currency is null");
        } else {
            try{
                transaction.setCurrency(Currency.valueOf(request.getCurrency()));
            }
            catch (Exception e){
                throw new NotFoundException("Currency is not valid");
            }
        }
        if(request.getType()==null) {
            throw new NotFoundException("Type is null");
        } else {
            try {
                transaction.setType(TransactionType.valueOf(request.getType()));
            } catch (Exception e) {
                throw new NotFoundException("Type is not valid");
            }
        }
            return transaction;

    }

}
