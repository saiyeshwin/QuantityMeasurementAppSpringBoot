package com.seveneleven.quantitymeasurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.seveneleven.quantitymeasurement.model.MeasurementType;
import com.seveneleven.quantitymeasurement.model.OperationType;
import com.seveneleven.quantitymeasurement.model.QuantityMeasurementEntity;

import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(OperationType operation);

    List<QuantityMeasurementEntity> findByFromMeasurementType(MeasurementType type);

    List<QuantityMeasurementEntity> findByIsError(boolean isError);

    long countByOperation(OperationType operation);
}