package com.nifasat.dataExtraction.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageRequest {
    @NotBlank(message = "Message cannot be empty")
    @Size(min = 5, message = "Message must be at least 5 characters long")
    private String message;
}
