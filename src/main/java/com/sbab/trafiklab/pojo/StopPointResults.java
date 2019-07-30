package com.sbab.trafiklab.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StopPointResults {

    //Stop points atttributes
    @JsonProperty(value = "StopPointNumber")
    private String stopPointNumber;
    @JsonProperty(value = "StopPointName")
    private String stopPointName;
    @JsonProperty(value = "StopAreaNumber")
    private String stopAreaNumber;
    @JsonProperty(value = "LocationNorthingCoordinate")
    private String locationNorthingCoordinate;
    @JsonProperty(value = "LocationEastingCoordinate")
    private String locationEastingCoordinate;
    @JsonProperty(value = "ZoneShortName")
    private String zoneShortName;
    @JsonProperty(value = "StopAreaTypeCode")
    private String stopAreaTypeCode;

    public StopPointResults() {
    }

    public StopPointResults(String stopPointNumber, String stopPointName, String stopAreaNumber, String locationNorthingCoordinate, String locationEastingCoordinate, String zoneShortName, String stopAreaTypeCode) {
        this.stopPointNumber = stopPointNumber;
        this.stopPointName = stopPointName;
        this.stopAreaNumber = stopAreaNumber;
        this.locationNorthingCoordinate = locationNorthingCoordinate;
        this.locationEastingCoordinate = locationEastingCoordinate;
        this.zoneShortName = zoneShortName;
        this.stopAreaTypeCode = stopAreaTypeCode;
    }

    public String getStopPointNumber() {
        return stopPointNumber;
    }

    public void setStopPointNumber(String stopPointNumber) {
        this.stopPointNumber = stopPointNumber;
    }

    public String getStopPointName() {
        return stopPointName;
    }

    public void setStopPointName(String stopPointName) {
        this.stopPointName = stopPointName;
    }

    public String getStopAreaNumber() {
        return stopAreaNumber;
    }

    public void setStopAreaNumber(String stopAreaNumber) {
        this.stopAreaNumber = stopAreaNumber;
    }

    public String getLocationNorthingCoordinate() {
        return locationNorthingCoordinate;
    }

    public void setLocationNorthingCoordinate(String locationNorthingCoordinate) {
        this.locationNorthingCoordinate = locationNorthingCoordinate;
    }

    public String getLocationEastingCoordinate() {
        return locationEastingCoordinate;
    }

    public void setLocationEastingCoordinate(String locationEastingCoordinate) {
        this.locationEastingCoordinate = locationEastingCoordinate;
    }

    public String getZoneShortName() {
        return zoneShortName;
    }

    public void setZoneShortName(String zoneShortName) {
        this.zoneShortName = zoneShortName;
    }

    public String getStopAreaTypeCode() {
        return stopAreaTypeCode;
    }

    public void setStopAreaTypeCode(String stopAreaTypeCode) {
        this.stopAreaTypeCode = stopAreaTypeCode;
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
