package com.nifasat.dataExtraction.controller;

import com.nifasat.dataExtraction.model.ExpenseDetails;
import com.nifasat.dataExtraction.model.MessageRequest;
import com.nifasat.dataExtraction.producers.ExpenseProducer;
import com.nifasat.dataExtraction.service.ExpenseAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/extract/v1")
public class ExpenseAnalyserController {

    @Autowired
    private ExpenseAnalyzerService expenseAnalyzerService;

    @Autowired
    private ExpenseProducer expenseProducer;

    @PostMapping("/analyze")
    public ResponseEntity<ExpenseDetails> analyzeExpense(@RequestBody MessageRequest request){
        String message = request.getMessage();
        ExpenseDetails analysedExpense = expenseAnalyzerService.analyseExpense(message);
        if(!analysedExpense.getIsValidBankMessage()) {
            return ResponseEntity.badRequest().body(analysedExpense);
        }
        expenseProducer.sendEventToKafka(analysedExpense);
        return ResponseEntity.ok(analysedExpense);
    }

}
