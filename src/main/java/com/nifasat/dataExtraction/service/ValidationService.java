package com.nifasat.dataExtraction.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationService {
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("\\$?\\d+(\\.\\d{2})?|\\d+(\\.\\d{2})? (USD|EUR|GBP)");
    private static final Pattern BANK_KEYWORDS = Pattern.compile("(?i)(transaction|purchase|payment|debit|credit|card|bank|account|balance|transfer|withdrawal|deposit|charge|paid|fee|spent|received)");

    public boolean isBankMessage(String message) {
        // Check if message contains typical bank message keywords
        boolean containsBankKeywords = BANK_KEYWORDS.matcher(message).find();

        // Check if message contains an amount pattern
        boolean containsAmount = AMOUNT_PATTERN.matcher(message).find();

        // A valid bank message should contain both bank keywords and an amount pattern
        return containsBankKeywords && containsAmount;
    }
}
