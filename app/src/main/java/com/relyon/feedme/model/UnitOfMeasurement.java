package com.relyon.feedme.model;

public class UnitOfMeasurement {

    private String id;
    private Long timesUsed;
    private String unitName;

    public UnitOfMeasurement() {
    }

    public UnitOfMeasurement(String id, Long timesUsed, String unitName) {
        this.id = id;
        this.timesUsed = timesUsed;
        this.unitName = unitName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(Long timesUsed) {
        this.timesUsed = timesUsed;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}