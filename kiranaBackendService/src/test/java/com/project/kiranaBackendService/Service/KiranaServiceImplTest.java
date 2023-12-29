package com.project.kiranaBackendService.Service;

import com.project.kiranaBackendService.Dao.KiranaDao;
import com.project.kiranaBackendService.Model.Currency;
import com.project.kiranaBackendService.Model.Transaction;
import com.project.kiranaBackendService.Model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KiranaServiceImplTest {


    @Mock
    private KiranaDao kiranaDao;

    @InjectMocks
    private KiranaServiceImpl kiranaServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTransaction() throws IOException {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setType(TransactionType.Debit);

        when(kiranaDao.save(any(Transaction.class))).thenReturn(transaction);
        assertEquals(kiranaServiceImpl.addTransaction(transaction), transaction);

    }

    @Test
    void testGetTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setType(TransactionType.Debit);

        when(kiranaDao.findAll()).thenReturn(java.util.List.of(transaction));
        assertEquals(kiranaServiceImpl.getTransaction(), java.util.List.of(transaction));
    }

    @Test
    void testGetTransactionById() {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setType(TransactionType.Debit);

        when(kiranaDao.findById(anyLong())).thenReturn(java.util.Optional.of(transaction));
        assertEquals(kiranaServiceImpl.getTransactionById(1L), java.util.Optional.of(transaction));
    }

    @Test
    void testUpdateTransaction() throws IOException {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setType(TransactionType.Debit);

        when(kiranaDao.save(any(Transaction.class))).thenReturn(transaction);
        when(kiranaDao.findById(anyLong())).thenReturn(java.util.Optional.of(transaction));
        assertEquals(kiranaServiceImpl.updateTransaction(transaction), transaction);

    }

    @Test
    void testDeleteTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setType(TransactionType.Debit);

        doNothing().when(kiranaDao).delete(any(Transaction.class));
        when(kiranaDao.findById(anyLong())).thenReturn(java.util.Optional.of(transaction));
        when(kiranaDao.getReferenceById(anyLong())).thenReturn(transaction);
        kiranaServiceImpl.deleteTransaction(1L);
        verify(kiranaDao, times(1)).delete(any(Transaction.class));
    }

    @Test
    void testGetTransactionByDateAndType() {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setDate(LocalDate.now());
        transaction.setType(TransactionType.Debit);

            when(kiranaDao.findAll()).thenReturn(java.util.List.of(transaction));
            assertEquals( java.util.List.of(transaction), kiranaServiceImpl.getTransactionByDateAndType(LocalDate.now(),TransactionType.Debit));

    }

    @Test
    void testGetTransactionByDate() {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setDate(LocalDate.now());
        transaction.setType(TransactionType.Debit);

        when(kiranaDao.findAll()).thenReturn(java.util.List.of(transaction));
        assertEquals( java.util.List.of(transaction), kiranaServiceImpl.getTransactionByDate(LocalDate.now()));
    }

    @Test
    void getTransactionByType() {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setAmount(100.00);
        transaction.setCurrency(Currency.USD);
        transaction.setDate(LocalDate.now());
        transaction.setType(TransactionType.Debit);

        when(kiranaDao.findAll()).thenReturn(java.util.List.of(transaction));
        assertEquals( java.util.List.of(transaction), kiranaServiceImpl.getTransactionByType(TransactionType.Debit));
    }
}