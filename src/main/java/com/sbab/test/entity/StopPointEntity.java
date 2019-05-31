package com.sbab.test.entity;

import com.sbab.test.pojo.StopPointResults;

import javax.persistence.*;

@Entity
@Table(name = "StopPoint")
public class StopPointEntity {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "stopPointNumber")
    private String stopPointNumber;
    @Column(name = "stopPointName")
    private String stopPointName;
    @Column(name = "stopAreaNumber")
    private String stopAreaNumber;
    @Column(name = "locationNorthingCoordinate")
    private String locationNorthingCoordinate;
    @Column(name = "locationEastingCoordinate")
    private String locationEastingCoordinate;
    @Column(name = "zoneShortName")
    private String zoneShortName;
    @Column(name = "stopAreaTypeCode")
    private String stopAreaTypeCode;

    public StopPointEntity(String stopPointNumber, String stopPointName, String stopAreaNumber, String locationNorthingCoordinate, String locationEastingCoordinate, String zoneShortName, String stopAreaTypeCode) {
        this.stopPointNumber = stopPointNumber;
        this.stopPointName = stopPointName;
        this.stopAreaNumber = stopAreaNumber;
        this.locationNorthingCoordinate = locationNorthingCoordinate;
        this.locationEastingCoordinate = locationEastingCoordinate;
        this.zoneShortName = zoneShortName;
        this.stopAreaTypeCode = stopAreaTypeCode;
    }

    public StopPointEntity(StopPointResults results) {
        this.stopPointNumber = results.getStopPointNumber();
        this.stopPointName = results.getStopPointName();
        this.stopAreaNumber = results.getStopAreaNumber();
        this.locationNorthingCoordinate = results.getLocationNorthingCoordinate();
        this.locationEastingCoordinate = results.getLocationEastingCoordinate();
        this.zoneShortName = results.getZoneShortName();
        this.stopAreaTypeCode = results.getStopAreaTypeCode();
    }

    public StopPointEntity() {
        //Default no arguments constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
