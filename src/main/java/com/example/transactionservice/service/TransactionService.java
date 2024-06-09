package com.example.transactionservice.service;


import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.exception.StudentNotFoundException;
import com.example.transactionservice.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public Transaction saveTransaction(Transaction transaction) {
        try {
            logger.info("Saving transaction: {}", transaction);
            return transactionRepository.save(transaction);
        }
        catch (Exception e) {
            logger.error("Error occurred while saving transaction: {}", e.getMessage());
            throw e;
        }
    }

    public Map<Long, Double> getFeeByStudentId(Long studentId) {
        try {
            List<Transaction> transactions = transactionRepository.findByStudentId(studentId);
            if (transactions.isEmpty()) {
                String errorMessage = "Student with ID " + studentId + " not found";
                logger.error(errorMessage);
                throw new StudentNotFoundException(errorMessage);
            }
            Double totalFee = 0.0;
            for (Transaction transaction : transactions) {
                totalFee += transaction.getFee();
            }
            Map<Long, Double> result = new HashMap<>();
            logger.info("Total fee for student ID {}: {}", studentId, totalFee);
            result.put(studentId, totalFee);
            return result;
        }
        catch (Exception e) {
            logger.error("Error occurred while fetching fee for student ID {}: {}", studentId, e.getMessage());
            throw e; // Rethrow the exception for the caller to handle
        }
    }
}
