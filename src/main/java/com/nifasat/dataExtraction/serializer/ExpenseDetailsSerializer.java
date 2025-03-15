package com.nifasat.dataExtraction.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class ExpenseDetailsSerializer implements Serializer {
    @Override
    public byte[] serialize(String topic, Object data) {
        byte[] result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
