package com.sbab.trafiklab.entity;

import com.sbab.trafiklab.dto.StopPointResults;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "StopPoint")
@Getter
@Setter
@NoArgsConstructor
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
}
