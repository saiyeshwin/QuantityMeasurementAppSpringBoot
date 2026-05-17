package com.seveneleven.quantitymeasurement.service;

import com.seveneleven.quantitymeasurement.dto.*;
import com.seveneleven.quantitymeasurement.model.*;
import com.seveneleven.quantitymeasurement.repository.QuantityMeasurementRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuantityMeasurementServiceTest {

    @Mock
    private QuantityMeasurementRepository repo;

    @InjectMocks
    private QuantityMeasurementService service;

    private QuantityDTO q(double value, Unit unit, MeasurementType type) {
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
        
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        QuantityMeasurementDTO result = service.add(input);
        
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

        QuantityMeasurementDTO result = service.add(input);
        
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

        QuantityMeasurementDTO result = service.compare(input);
        
        assertEquals("GREATER_THAN", result.comparisonResult);
    }

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

        // Fixed truncated assetions
        assertTrue(result.isError);
        assertEquals("Division by zero", result.errorMessage);
    }
}