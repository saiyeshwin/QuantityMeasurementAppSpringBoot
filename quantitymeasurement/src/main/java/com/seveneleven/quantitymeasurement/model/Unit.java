package com.seveneleven.quantitymeasurement.model;

public enum Unit {
    KILOMETER(MeasurementType.LENGTH, 1.0),
    METER(MeasurementType.LENGTH, 0.001),
    CENTIMETER(MeasurementType.LENGTH, 0.00001),
    MILLIMETER(MeasurementType.LENGTH, 0.000001),
    MILE(MeasurementType.LENGTH, 1.60934),
    YARD(MeasurementType.LENGTH, 0.0009144),
    FEET(MeasurementType.LENGTH, 0.0003048),

    KILOGRAM(MeasurementType.WEIGHT, 1.0),
    GRAM(MeasurementType.WEIGHT, 0.001),
    TONNE(MeasurementType.WEIGHT, 1000),
    POUND(MeasurementType.WEIGHT, 0.453592),
    OUNCE(MeasurementType.WEIGHT, 0.0283495),

    LITER(MeasurementType.VOLUME, 1.0),
    MILLILITER(MeasurementType.VOLUME, 0.001),
    GALLON(MeasurementType.VOLUME, 3.78541),
    CUBICMETER(MeasurementType.VOLUME, 1000),

    CELSIUS(MeasurementType.TEMPERATURE, 1.0),
    FAHRENHEIT(MeasurementType.TEMPERATURE, 1.0),
    KELVIN(MeasurementType.TEMPERATURE, 1.0);

    private final MeasurementType type;
    private final double conversionFactor;

    Unit(MeasurementType type, double conversionFactor) {
        this.type = type;
        this.conversionFactor = conversionFactor;
    }

    public MeasurementType getType() {
        return type;
    }

    public double toBase(double value) {
        if (type == MeasurementType.TEMPERATURE) {
            switch (this) {
                case FAHRENHEIT:
                    return (value - 32) * 5 / 9;
                case KELVIN:
                    return value - 273.15;
                default:
                    return value;
            }
        }

        return value * conversionFactor;
    }

    public double fromBase(double baseValue) {
        if (type == MeasurementType.TEMPERATURE) {
            switch (this) {
                case FAHRENHEIT:
                    return (baseValue * 9 / 5) + 32;
                case KELVIN:
                    return baseValue + 273.15;
                default:
                    return baseValue;
            }
        }
        return baseValue / conversionFactor;
    }
}