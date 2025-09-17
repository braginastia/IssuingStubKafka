package com.example.IssuingStubKafka.dto;

public class AgeProcessingResponse {
    private String status;
    private String message;
    private String processedData;

    public AgeProcessingResponse(String status, String message, String processedData) {
        this.status = status;
        this.message = message;
        this.processedData = processedData;
    }

    public AgeProcessingResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getProcessedData() {
        return processedData;
    }
}