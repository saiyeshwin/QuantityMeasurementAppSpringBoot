package com.seveneleven.quantitymeasurement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seveneleven.quantitymeasurement.dto.*;
import com.seveneleven.quantitymeasurement.model.*;
import com.seveneleven.quantitymeasurement.repository.QuantityMeasurementRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuantityMeasurementService {
	private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementService.class);
	@Autowired
	private QuantityMeasurementRepository repo;

	public QuantityMeasurementDTO add(QuantityInputDTO input) {
		QuantityDTO q1 = input.thisQuantityDTO;
		QuantityDTO q2 = input.thatQuantityDTO;
		QuantityMeasurementDTO response = new QuantityMeasurementDTO();
		try {
			validateSameType(q1, q2);
			double resultBase = q1.getUnit().toBase(q1.getValue()) + q2.getUnit().toBase(q2.getValue());
			double result = q1.getUnit().fromBase(resultBase);
			populateResponse(response, q1, q2, result, q1.getUnit(), OperationType.ADD);
			saveEntity(q1, q2, result, q1.getUnit(), OperationType.ADD, false, null,null);
			logger.info("Sucessfully added");
		} 
		catch (Exception e) {
			handleError(response, q1, q2, OperationType.ADD, e);
		}
		return response;
	}
	

	public QuantityMeasurementDTO addWithTargetUnit(QuantityInputDTO input) {
		QuantityDTO q1 = input.thisQuantityDTO;
		QuantityDTO q2 = input.thatQuantityDTO;
		Unit targetUnit = input.targetUnit;
		QuantityMeasurementDTO response = new QuantityMeasurementDTO();
		try {
			validateSameType(q1, q2);
			validateTargetUnit(q1, targetUnit);
			double resultBase = q1.getUnit().toBase(q1.getValue()) + q2.getUnit().toBase(q2.getValue());
			double result = targetUnit.fromBase(resultBase);
			populateResponse(response, q1, q2, result, targetUnit, OperationType.ADD);
			saveEntity(q1, q2, result, targetUnit, OperationType.ADD, false, null,null);
			logger.info("Sucessfully added with target unit");
		} 
		catch (Exception e) {
			handleError(response, q1, q2, OperationType.ADD, e);
		}
		return response;
	}


	public QuantityMeasurementDTO subtract(QuantityInputDTO input) {
		QuantityDTO q1 = input.thisQuantityDTO;
		QuantityDTO q2 = input.thatQuantityDTO;
		QuantityMeasurementDTO response = new QuantityMeasurementDTO();
		try {
			validateSameType(q1, q2);
			double resultBase = q1.getUnit().toBase(q1.getValue()) - q2.getUnit().toBase(q2.getValue());
			double result = q1.getUnit().fromBase(resultBase);
			populateResponse(response, q1, q2, result, q1.getUnit(), OperationType.SUBTRACT);
			saveEntity(q1, q2, result, q1.getUnit(), OperationType.SUBTRACT, false, null,null);
		} 
		catch (Exception e) {
			handleError(response, q1, q2, OperationType.SUBTRACT, e);
		}
		return response;
	}

	public QuantityMeasurementDTO subtractWithTargetUnit(QuantityInputDTO input) {
		QuantityDTO q1 = input.thisQuantityDTO;
		QuantityDTO q2 = input.thatQuantityDTO;
		Unit targetUnit = input.targetUnit;
		QuantityMeasurementDTO response = new QuantityMeasurementDTO();
		try {
			validateSameType(q1, q2);
			validateTargetUnit(q1, targetUnit);
			double resultBase = q1.getUnit().toBase(q1.getValue()) - q2.getUnit().toBase(q2.getValue());
			double result = targetUnit.fromBase(resultBase);
			populateResponse(response, q1, q2, result, targetUnit, OperationType.SUBTRACT);
			saveEntity(q1, q2, result, targetUnit, OperationType.SUBTRACT, false, null,null);
			logger.info("Sucessfully subtracted");
		} 
		catch (Exception e) {
			handleError(response, q1, q2, OperationType.SUBTRACT, e);
		}
		return response;
	}


	public QuantityMeasurementDTO divide(QuantityInputDTO input) {
		QuantityDTO q1 = input.thisQuantityDTO;
		QuantityDTO q2 = input.thatQuantityDTO;
		QuantityMeasurementDTO response = new QuantityMeasurementDTO();
		try {
			double base1 = q1.getUnit().toBase(q1.getValue());
			double base2 = q2.getUnit().toBase(q2.getValue());
			if (base2 == 0) throw new RuntimeException("Division by zero");
			double result = base1 / base2;

			response.fromValue = q1.getValue();
			response.fromUnit = q1.getUnit().name();
			response.fromMeasurementType = q1.getUnit().getType().name();
			response.toValue = q2.getValue();
			response.toUnit = q2.getUnit().name();
			response.toMeasurementType = q2.getUnit().getType().name();
			response.resultValue = result;
			response.resultUnit = "DIMENSIONLESS";
			response.resultMeasurementType = "DIMENSIONLESS";
			response.operation = OperationType.DIVIDE.name();

			saveEntity(q1, q2, result, null, OperationType.DIVIDE, false, null,null);
			logger.info("Sucessfully subtracted");

		} 
		catch (Exception e) {
			handleError(response, q1, q2, OperationType.DIVIDE, e);
		}
		return response;
	}

	public List<QuantityMeasurementDTO> getHistory() {
		return repo.findAll().stream().map(this::toSummary).collect(Collectors.toList());
	}

	public List<QuantityMeasurementDTO> getHistoryByType(MeasurementType type) {
		return repo.findByFromMeasurementType(type).stream().map(this::toSummary).collect(Collectors.toList());
	}

	public List<QuantityMeasurementDTO> getHistoryByOperation(OperationType operation) {
		return repo.findByOperation(operation).stream().map(this::toSummary).collect(Collectors.toList());
	}

	public long countByOperation(OperationType operation) {
		return repo.countByOperation(operation);
	}

	public List<QuantityMeasurementDTO> getErroredHistory() {
		return repo.findByIsError(true).stream().map(this::toSummary).collect(Collectors.toList());
	}

	private void validateSameType(QuantityDTO q1, QuantityDTO q2) {
		if (q1.getUnit().getType() != q2.getUnit().getType()) {
			throw new RuntimeException("Incompatible measurement types: "
					+ q1.getUnit().getType() + " and " + q2.getUnit().getType());
		}
	}

	private void validateTargetUnit(QuantityDTO q1, Unit targetUnit) {
		if (targetUnit == null) {
			throw new RuntimeException("Target unit must not be null");
		}
		if (targetUnit.getType() != q1.getUnit().getType()) {
			throw new RuntimeException("Target unit type " + targetUnit.getType()
			+ " is incompatible with input type " + q1.getUnit().getType());
		}
	}

	private void populateResponse(QuantityMeasurementDTO response,QuantityDTO q1, QuantityDTO q2,
			double result, Unit resultUnit,OperationType op) {
		response.fromValue = q1.getValue();
		response.fromUnit = q1.getUnit().name();
		response.fromMeasurementType = q1.getUnit().getType().name();
		response.toValue = q2.getValue();
		response.toUnit = q2.getUnit().name();
		response.toMeasurementType = q2.getUnit().getType().name();
		response.resultValue = result;
		response.resultUnit = resultUnit.name();
		response.resultMeasurementType = resultUnit.getType().name();
		response.operation = op.name();
	}

	private void handleError(QuantityMeasurementDTO response,QuantityDTO q1, QuantityDTO q2,
		OperationType op, Exception e) {
		response.isError = true;
		response.errorMessage = e.getMessage();
		saveEntity(q1, q2, 0, null, op, true, e.getMessage(), null);
	}

	private void saveEntity(QuantityDTO q1, QuantityDTO q2,double result, Unit resultUnit,
			OperationType op, boolean isError, String errorMsg,String comparisonResult) {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
		entity.setFromValue(q1.getValue());
		entity.setFromUnit(q1.getUnit());
		entity.setFromMeasurementType(q1.getUnit().getType());
		entity.setToValue(q2.getValue());
		entity.setToUnit(q2.getUnit());
		entity.setToMeasurementType(q2.getUnit().getType());
		entity.setResultValue(result);
		entity.setResultUnit(resultUnit);
		entity.setResultMeasurementType(resultUnit != null ? resultUnit.getType() : null);
		entity.setOperation(op);
		entity.setError(isError);
		entity.setErrorMessage(errorMsg);
		entity.setComparisonResult(comparisonResult);
		repo.save(entity);
	}

	private QuantityMeasurementDTO toSummary(QuantityMeasurementEntity e) {
		QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
		dto.operation = e.getOperation() != null ? e.getOperation().name() : null;
		dto.fromValue = e.getFromValue();
		dto.fromUnit = e.getFromUnit() != null ? e.getFromUnit().name() : null;
		dto.fromMeasurementType = e.getFromMeasurementType() != null ? e.getFromMeasurementType().name() : null;
		dto.toValue = e.getToValue();
		dto.toUnit = e.getToUnit() != null ? e.getToUnit().name() : null;
		dto.toMeasurementType = e.getToMeasurementType() != null ? e.getToMeasurementType().name() : null;
		dto.resultValue = e.getResultValue();
		dto.resultUnit = e.getResultUnit() != null ? e.getResultUnit().name() : null;
		dto.resultMeasurementType = e.getResultMeasurementType() != null ? e.getResultMeasurementType().name() : null;
		dto.isError = e.isError();
		dto.errorMessage = e.getErrorMessage();
		dto.createdAt = e.getCreatedAt();
		return dto;
	}

	public QuantityMeasurementDTO compare(QuantityInputDTO input) {
		QuantityDTO q1 = input.thisQuantityDTO;
		QuantityDTO q2 = input.thatQuantityDTO;
		QuantityMeasurementDTO response = new QuantityMeasurementDTO();
		try {
			validateSameType(q1, q2);
			double base1 = q1.getUnit().toBase(q1.getValue());
			double base2 = q2.getUnit().toBase(q2.getValue());
			String comparisonResult;
			if (base1 > base2) comparisonResult = "GREATER_THAN";
			else if (base1 < base2)  comparisonResult = "LESS_THAN";
			else comparisonResult = "EQUAL";
			response.fromValue = q1.getValue();
			response.fromUnit = q1.getUnit().name();
			response.fromMeasurementType = q1.getUnit().getType().name();
			response.toValue = q2.getValue();
			response.toUnit = q2.getUnit().name();
			response.toMeasurementType = q2.getUnit().getType().name();
			response.comparisonResult = comparisonResult;
			response.operation = OperationType.COMPARE.name();
			saveEntity(q1, q2, 0, null, OperationType.COMPARE, false, null, comparisonResult);
			logger.info("Sucessfully compared");
		} 
		catch (Exception e) {
			handleError(response, q1, q2, OperationType.COMPARE, e);
		}
		return response;
	}

	public QuantityMeasurementDTO convert(QuantityInputDTO input) {
		QuantityDTO q1 = input.thisQuantityDTO;
		Unit targetUnit = input.targetUnit;
		QuantityMeasurementDTO response = new QuantityMeasurementDTO();
		QuantityDTO q2 = new QuantityDTO();
		q2.setValue(0);
		q2.setUnit(targetUnit);
		try {
			validateTargetUnit(q1, targetUnit);
			double base = q1.getUnit().toBase(q1.getValue());
			double result = targetUnit.fromBase(base);
			response.fromValue = q1.getValue();
			response.fromUnit = q1.getUnit().name();
			response.fromMeasurementType = q1.getUnit().getType().name();
			response.resultValue = result;
			response.resultUnit = targetUnit.name();
			response.resultMeasurementType = targetUnit.getType().name();
			response.operation = OperationType.CONVERT.name();
			saveEntity(q1, q2, result, targetUnit, OperationType.CONVERT, false, null, null);
			logger.info("Sucessfully converted");
		} 
		catch (Exception e) {
			handleError(response, q1, q2, OperationType.CONVERT, e);
		}
		return response;
	}
	public QuantityMeasurementDTO multiply(QuantityInputDTO input) {
		QuantityDTO q1 = input.thisQuantityDTO;
		QuantityDTO q2 = input.thatQuantityDTO;
		QuantityMeasurementDTO response = new QuantityMeasurementDTO();
		try {
			validateSameType(q1, q2);
			double base1 = q1.getUnit().toBase(q1.getValue());
			double base2 = q2.getUnit().toBase(q2.getValue());
			double resultBase = base1 * base2;
			double result = q1.getUnit().fromBase(resultBase);
			populateResponse(response, q1, q2, result, q1.getUnit(), OperationType.MULTIPLY);
			saveEntity(q1, q2, result, q1.getUnit(), OperationType.MULTIPLY, false, null, null);
			logger.info("Sucessfully multiplied");
		} 
		catch (Exception e) {
			handleError(response, q1, q2, OperationType.MULTIPLY, e);
		}
		return response;
	}
}