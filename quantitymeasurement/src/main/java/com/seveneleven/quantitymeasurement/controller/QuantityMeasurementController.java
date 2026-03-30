package com.seveneleven.quantitymeasurement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.seveneleven.quantitymeasurement.dto.*;
import com.seveneleven.quantitymeasurement.service.QuantityMeasurementService;


//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.seveneleven.quantitymeasurement.dto.*;
import com.seveneleven.quantitymeasurement.model.MeasurementType;
import com.seveneleven.quantitymeasurement.model.OperationType;
import com.seveneleven.quantitymeasurement.service.QuantityMeasurementService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuantityMeasurementController {

    @Autowired
    private QuantityMeasurementService service;
    @PostMapping("/add")
    public QuantityMeasurementDTO add(@RequestBody QuantityInputDTO input) {
        return service.add(input);
    }
    
    @PostMapping("/subtract")
    public QuantityMeasurementDTO subtract(@RequestBody QuantityInputDTO input) {
        return service.subtract(input);
    }
    
    @PostMapping("/add-with-target-unit")
    public QuantityMeasurementDTO addWithTargetUnit(@RequestBody QuantityInputDTO input) {
        return service.addWithTargetUnit(input);
    }

    @PostMapping("/subtract-with-target-unit")
    public QuantityMeasurementDTO subtractWithTargetUnit(@RequestBody QuantityInputDTO input) {
        return service.subtractWithTargetUnit(input);
    }
    @PostMapping("/divide")
    public QuantityMeasurementDTO divide(@RequestBody QuantityInputDTO input) {
        return service.divide(input);
    }

    @GetMapping("/history")
    public List<QuantityMeasurementDTO> history() {
        return service.getHistory();
    }

    @GetMapping("/history/type/{type}")
    public List<QuantityMeasurementDTO> historyByType(@PathVariable MeasurementType type) {
        return service.getHistoryByType(type);
    }

    @GetMapping("/history/operation/{operation}")
    public List<QuantityMeasurementDTO> historyByOperation(@PathVariable OperationType operation) {
        return service.getHistoryByOperation(operation);
    }
    
    @GetMapping("/count/{operation}")
    public long countByOperation(@PathVariable OperationType operation) {
        return service.countByOperation(operation);
    }

    @GetMapping("/history/errored")
    public List<QuantityMeasurementDTO> historyErrored() {
        return service.getErroredHistory();
    }
    @PostMapping("/compare")
    public QuantityMeasurementDTO compare(@RequestBody QuantityInputDTO input) {
        return service.compare(input);
    }

    @PostMapping("/convert")
    public QuantityMeasurementDTO convert(@RequestBody QuantityInputDTO input) {
        return service.convert(input);
    }
    @PostMapping("/multiply")
    public QuantityMeasurementDTO multiply(@RequestBody QuantityInputDTO input) {
        return service.multiply(input);
    }
}