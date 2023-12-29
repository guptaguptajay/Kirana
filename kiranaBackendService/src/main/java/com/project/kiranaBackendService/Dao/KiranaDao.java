package com.project.kiranaBackendService.Dao;

import com.project.kiranaBackendService.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KiranaDao extends JpaRepository<Transaction, Long> {


}
