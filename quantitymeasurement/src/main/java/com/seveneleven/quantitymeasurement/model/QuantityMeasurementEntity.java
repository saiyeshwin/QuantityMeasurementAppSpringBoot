package com.seveneleven.quantitymeasurement.model;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityMeasurementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getFromValue() {
		return fromValue;
	}

	public void setFromValue(double fromValue) {
		this.fromValue = fromValue;
	}

	public Unit getFromUnit() {
		return fromUnit;
	}

	public void setFromUnit(Unit fromUnit) {
		this.fromUnit = fromUnit;
	}

	public MeasurementType getFromMeasurementType() {
		return fromMeasurementType;
	}

	public void setFromMeasurementType(MeasurementType fromMeasurementType) {
		this.fromMeasurementType = fromMeasurementType;
	}

	public double getToValue() {
		return toValue;
	}

	public void setToValue(double toValue) {
		this.toValue = toValue;
	}

	public Unit getToUnit() {
		return toUnit;
	}

	public void setToUnit(Unit toUnit) {
		this.toUnit = toUnit;
	}

	public MeasurementType getToMeasurementType() {
		return toMeasurementType;
	}

	public void setToMeasurementType(MeasurementType toMeasurementType) {
		this.toMeasurementType = toMeasurementType;
	}

	public OperationType getOperation() {
		return operation;
	}

	public void setOperation(OperationType operation) {
		this.operation = operation;
	}

	public double getResultValue() {
		return resultValue;
	}

	public void setResultValue(double resultValue) {
		this.resultValue = resultValue;
	}

	public Unit getResultUnit() {
		return resultUnit;
	}

	public void setResultUnit(Unit resultUnit) {
		this.resultUnit = resultUnit;
	}

	public MeasurementType getResultMeasurementType() {
		return resultMeasurementType;
	}

	public void setResultMeasurementType(MeasurementType resultMeasurementType) {
		this.resultMeasurementType = resultMeasurementType;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	private double fromValue;

	@Enumerated(EnumType.STRING)
	private Unit fromUnit;

	@Enumerated(EnumType.STRING)
	private MeasurementType fromMeasurementType;

	private double toValue;

	@Enumerated(EnumType.STRING)
	private Unit toUnit;

	@Enumerated(EnumType.STRING)
	private MeasurementType toMeasurementType;

	@Enumerated(EnumType.STRING)
	private OperationType operation;

	private double resultValue;

	@Enumerated(EnumType.STRING)
	private Unit resultUnit;

	@Enumerated(EnumType.STRING)
	private MeasurementType resultMeasurementType;

	private boolean isError;
	private String errorMessage;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String comparisonResult;

	public String getComparisonResult() {
	    return comparisonResult;
	}

	public void setComparisonResult(String comparisonResult) {
	    this.comparisonResult = comparisonResult;
	}
	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}