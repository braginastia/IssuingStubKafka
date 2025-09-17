package com.example.IssuingStubKafka.controller;

import com.example.IssuingStubKafka.dto.AgeProcessingResponse;
import com.example.IssuingStubKafka.service.AgeProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgeProcessingController {
    private final AgeProcessorService ageProcessorService;

    public AgeProcessingController(AgeProcessorService ageProcessorService) {
        this.ageProcessorService = ageProcessorService;
    }

    @PostMapping("/process-age")
    public ResponseEntity<AgeProcessingResponse> processAge(@RequestBody String jsonPayload) {
        try {
            String processedJson = ageProcessorService.processJsonAndSendToKafka(jsonPayload);

            AgeProcessingResponse response = new AgeProcessingResponse(
                    "processed",
                    "All age values replaced with 96 and sent to Kafka",
                    processedJson
            );

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new AgeProcessingResponse("error", "Failed to process request: " + e.getMessage()));
        }
    }
}