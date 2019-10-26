package com.sbab.trafiklab.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BussStopPointsDTO {

    private int lineNumber;
    private String stopName;

    public BussStopPointsDTO(int lineNumber, String stopName) {
        this.lineNumber = lineNumber;
        this.stopName = stopName;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
