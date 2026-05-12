package com.seveneleven.quantitymeasurement.service;

import com.seveneleven.quantitymeasurement.dto.*;
import com.seveneleven.quantitymeasurement.model.*;
import com.seveneleven.quantitymeasurement.repository.QuantityMeasurementRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuantityMeasurementServiceTe
    QuantityMeasurementRepository repo = mock(QuantityMeasurementRepository.class);
    QuantityMeasurementService service;

    @BeforeEach
    void setUp() throws Exception {
        service = new QuantityMeasurementService();
        var field = QuantityMeasurementService.class.getDeclaredField("repo");
        field.setAccessible(true);
        field.set(service, repo);
    }

    QuantityDTO q(double value, Unit unit, MeasurementType type) {
        QuantityDTO q = new QuantityDTO();
        q.setValue(value);
        q.setUnit(unit);
        q.setMeasurementType(type);
        return q;
    }

    @Test
    void add_returnsCorrectSum() {
        QuantityInputDTO input = new QuantityInputDTO(
            q(1.0, Unit.KILOMETER, MeasurementType.LENGTH),
            q(500.0, Unit.METER, MeasurementType.LENGTH)
        );
        when(repo.save(any())).thenAnswer(inv -> inv.g
                
                tityMeasurementDTO result = service.add(inpu
        assertFalse(result.isError);
        assertEquals(1.5, result.resultValue, 0.0001);
        assertEquals("KILOMETER", result.resultUnit);
    }

    @Test
    void add_incompatibleTypes_returnsError() {
        QuantityInputDTO input = new QuantityInputDTO(
            q(1.0, Unit.KILOMETER, MeasurementType.LENGTH),
            q(5.0, Unit.KILOGRAM, MeasurementType.WEIGHT)
        );
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

                tityMeasurementDTO result = service.add(input);
                assertTrue(result.isError);
        assertTrue(result.errorMessage.contains("Incompatible"));
    }

    @Test
    void compare_returnsGreaterThan() {
        QuantityInputDTO input = new QuantityInputDTO(
            q(2.0, Unit.KILOMETER, MeasurementType.LENGTH),
            q(500.0, Unit.METER, MeasurementType.LENGTH)
        );
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        QuantityMeasurementDTO result = service.compar
                
                rtEquals("GREATER_THAN", result.comparisonRe

    @Test
    void convert_kmToMeter_returnsCorrectValue() {
        QuantityInputDTO input = new QuantityInputDTO();
        input.thisQuantityDTO = q(2.0, Unit.KILOMETER, MeasurementType.LENGTH);
        input.targetUnit = Unit.METER;
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        QuantityMeasurementDTO result = service.convert(input);

        assertFalse(result.isError);
        assertEquals(2000.0, result.resultValue, 0.0001);
    }

    @Test
    void divide_byZero_returnsError() {
        QuantityInputDTO input = new QuantityInputDTO(
            q(10.0, Unit.KILOGRAM, MeasurementType.WEIGHT),
            q(0.0, Unit.KILOGRAM, MeasurementType.WEIGHT)
        );
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        QuantityMeasurementDTO result = service.divide(input);

                rtTrue(result.isError);
                rtEquals("Division by zero", result.errorMess
}