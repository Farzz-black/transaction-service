package com.example.transactionservice.controller;

import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.exception.StudentNotFoundException;
import com.example.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping
    public ResponseEntity<Transaction> saveTransaction(@Valid @RequestBody Transaction transaction) {
        try {
            logger.info("Creating transaction: {}", transaction);
            Transaction createdTransaction = transactionService.saveTransaction(transaction);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while creating transaction: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fee/{studentId}")

    public ResponseEntity<Map<Long, Double>> getFeeByStudentId(@PathVariable Long studentId) {
        try {
            Map<Long, Double> feeMap = transactionService.getFeeByStudentId(studentId);
            return new ResponseEntity<>(feeMap, HttpStatus.OK);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with ID " + studentId + " not found", e);
        } catch (Exception e) {
            logger.error("Error occurred while fetching fee for student ID {}: {}", studentId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
