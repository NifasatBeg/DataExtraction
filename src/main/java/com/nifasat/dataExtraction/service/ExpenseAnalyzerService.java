package com.nifasat.dataExtraction.service;

import com.nifasat.dataExtraction.model.ExpenseDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExpenseAnalyzerService {
    private final LLMService llmService;
    private final ValidationService validationService;

    @Autowired
    public ExpenseAnalyzerService(LLMService llmService, ValidationService validationService){
        this.llmService = llmService;
        this.validationService = validationService;
    }

    public ExpenseDetails analyseExpense(String message){
        boolean isValidBankMessage = validationService.isBankMessage(message);

        if (!isValidBankMessage) {
            ExpenseDetails errorResponse = ExpenseDetails.builder()
                    .isValidBankMessage(false)
                    .build();
            return errorResponse;
        }

        ExpenseDetails expenseDetails = llmService.analyzeExpense(message);
        expenseDetails.setIsValidBankMessage(true);

        return expenseDetails;
    }

}
