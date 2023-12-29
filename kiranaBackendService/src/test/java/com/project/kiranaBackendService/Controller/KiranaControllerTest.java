package com.project.kiranaBackendService.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.kiranaBackendService.Exception.NotFoundException;
import com.project.kiranaBackendService.Model.Currency;
import com.project.kiranaBackendService.Model.Transaction;
import com.project.kiranaBackendService.Model.TransactionRequest;
import com.project.kiranaBackendService.Model.TransactionType;
import com.project.kiranaBackendService.Service.KiranaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KiranaControllerTest {

    @Mock
    private KiranaService kiranaService;

    @InjectMocks
    private KiranaController kiranaController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testAddTransaction() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setTransId("1");
        request.setAmount("100.00");
        request.setCurrency("USD");
        request.setType("Debit");

        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setType(TransactionType.Debit);

        when(kiranaService.addTransaction(any(Transaction.class))).thenReturn(transaction);
        Transaction txn =  kiranaController.addTransaction(request);
        assertEquals(txn, transaction);

    }

    @Test
    void testAddTransactionWithInvalidCurrency() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setTransId("1");
        request.setAmount("100.00");
        request.setCurrency("abc");
        request.setType("Debit");
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> kiranaController.addTransaction(request));
        assertEquals("Currency is not valid", thrown.getMessage());

    }

    @Test
    void testAddTransactionWithInvalidType() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setTransId("1");
        request.setAmount("100.00");
        request.setCurrency("USD");
        request.setType("abc");
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> kiranaController.addTransaction(request));
        assertEquals("Type is not valid", thrown.getMessage());

    }

    @Test
    void testAddTransactionWithInvalidAmount() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setTransId("1");
        request.setAmount("abc");
        request.setCurrency("USD");
        request.setType("Debit");
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> kiranaController.addTransaction(request));
        assertEquals("Amount is not valid", thrown.getMessage());

    }

    @Test
    void testAddTransactionWithInvalidId() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setTransId("abc");
        request.setAmount("100.00");
        request.setCurrency("USD");
        request.setType("Debit");
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> kiranaController.addTransaction(request));
        assertEquals("Transaction Id is not valid", thrown.getMessage());

    }
    @Test
    void testAddTransactionWithNullId() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setTransId(null);
        request.setAmount("100.00");
        request.setCurrency("USD");
        request.setType("Debit");
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> kiranaController.addTransaction(request));
        assertEquals("Transaction Id is null", thrown.getMessage());

    }
    @Test
    void testAddTransactionWithNullAmount() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setTransId("1");
        request.setAmount(null);
        request.setCurrency("USD");
        request.setType("Debit");
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> kiranaController.addTransaction(request));
        assertEquals("Amount is null", thrown.getMessage());

    }

    @Test
    void testAddTransactionWithNullCurrency() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setTransId("1");
        request.setAmount("100.00");
        request.setCurrency(null);
        request.setType("Debit");
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> kiranaController.addTransaction(request));
        assertEquals("Currency is null", thrown.getMessage());

    }

    @Test
    void testAddTransactionWithNullType() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setTransId("1");
        request.setAmount("100.00");
        request.setCurrency("USD");
        request.setType(null);
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> kiranaController.addTransaction(request));
        assertEquals("Type is null", thrown.getMessage());

    }



    @Test
    void testGetTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setType(TransactionType.Debit);

        when(kiranaService.getTransaction()).thenReturn(java.util.List.of(transaction));
        kiranaController.getTransaction(null, null);
        assertEquals(java.util.List.of(transaction), kiranaController.getTransaction(null, null));
    }

    @Test
    void testGetTransactionById() {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setType(TransactionType.Debit);

        when(kiranaService.getTransactionById(1L)).thenReturn(java.util.Optional.of(transaction));
        assertEquals(java.util.Optional.of(transaction), kiranaController.getTransactionById("1"));
    }

    @Test
    void updateTransaction() throws IOException {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setType(TransactionType.Debit);

        TransactionRequest request = new TransactionRequest();
        request.setTransId("1");
        request.setAmount("100.00");
        request.setCurrency("USD");
        request.setType("Debit");

        when(kiranaService.updateTransaction(any(Transaction.class))).thenReturn(transaction);
        assertEquals(transaction, kiranaController.updateTransaction(request));
    }

    @Test
    void deleteTransaction() {
        doNothing().when(kiranaService).deleteTransaction(1L);
        kiranaController.deleteTransaction("1");
        verify(kiranaService, times(1)).deleteTransaction(1L);
    }
}