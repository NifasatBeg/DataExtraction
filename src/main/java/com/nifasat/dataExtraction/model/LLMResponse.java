package com.nifasat.dataExtraction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LLMResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Message message;
        @JsonProperty("finish_reason")
        private String finishReason;
        private int index;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}
