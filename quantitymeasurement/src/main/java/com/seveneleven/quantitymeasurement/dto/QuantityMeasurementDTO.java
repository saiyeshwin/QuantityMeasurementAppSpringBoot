package com.seveneleven.quantitymeasurement.dto;

import java.time.LocalDateTime;

public class QuantityMeasurementDTO {

    public double fromValue;
    public String fromUnit;
    public String fromMeasurementType;

    public double toValue;
    public String toUnit;
    public String toMeasurementType;

    public String operation;

    public double resultValue;
    public String resultUnit;
    public String resultMeasurementType;

    public String comparisonResult;
    public boolean isError;
    public String errorMessage;

    public LocalDateTime createdAt;
}