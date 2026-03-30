package com.seveneleven.quantitymeasurement.dto;

import com.seveneleven.quantitymeasurement.model.MeasurementType;
import com.seveneleven.quantitymeasurement.model.Unit;


public class QuantityDTO {

    private double value;
    private MeasurementType measurementType;
    private Unit unit;
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public MeasurementType getMeasurementType() {
		return measurementType;
	}
	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
    
}