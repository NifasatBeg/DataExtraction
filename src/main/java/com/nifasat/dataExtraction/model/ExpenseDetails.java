package com.nifasat.dataExtraction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDetails {
    private Double amount;
    private String merchant;
    private String currency;
    private Boolean isValidBankMessage;
    private String userId;
}
