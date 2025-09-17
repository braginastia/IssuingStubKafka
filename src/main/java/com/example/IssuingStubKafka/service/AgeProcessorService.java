package com.example.IssuingStubKafka.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class AgeProcessorService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public AgeProcessorService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String processJsonAndSendToKafka(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            processAgeFields(rootNode);

            String processedJson = objectMapper.writeValueAsString(rootNode);

            kafkaTemplate.send(topicName, processedJson);

            return processedJson;

        } catch (Exception e) {
            throw new RuntimeException("Failed to process JSON", e);
        }
    }

    private void processAgeFields(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();
                JsonNode fieldValue = field.getValue();

                if ("age".equals(fieldName) && fieldValue.isNumber()) {
                    objectNode.put(fieldName, 96);
                } else if (fieldValue.isObject() || fieldValue.isArray()) {
                    processAgeFields(fieldValue);
                }
            }
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode element = arrayNode.get(i);
                if (element.isObject() || element.isArray()) {
                    processAgeFields(element);
                }
            }
        }
    }
}