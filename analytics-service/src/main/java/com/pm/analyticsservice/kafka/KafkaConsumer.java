package com.pm.analyticsservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

@Service
public class KafkaConsumer {


    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytic-service")
    public void consumeEvent(Byte[] event){

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Byte[] wrapperArray = event;

            byte[] primitiveArray = new byte[wrapperArray.length];
            for (int i = 0; i < wrapperArray.length; i++) {
                primitiveArray[i] = wrapperArray[i];  // auto-unboxing
            }

            String json = new String(primitiveArray, StandardCharsets.UTF_8);

            PatientEvent patientEvent =
                    objectMapper.readValue(json, PatientEvent.class);

            log.info("Received Patient Event: [PatientId={}, PatientName={}, PatientEmail={}]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail());

//        PatientEvent patientEvent = PatientEvent.parseFrom(event);
        } catch (Exception ex){
            log.error("Error deserializing event {}", ex.getMessage());
        }


    }
}
