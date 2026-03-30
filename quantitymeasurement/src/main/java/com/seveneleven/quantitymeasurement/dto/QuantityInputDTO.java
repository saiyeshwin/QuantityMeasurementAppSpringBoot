package com.seveneleven.quantitymeasurement.dto;

import com.seveneleven.quantitymeasurement.model.Unit;

public class QuantityInputDTO {

    public QuantityDTO thisQuantityDTO;
    public QuantityDTO thatQuantityDTO;
    public Unit targetUnit; // optional — only used by *-with-target-unit endpoints

    public QuantityInputDTO() {}

    public QuantityInputDTO(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        this.thisQuantityDTO = thisQuantityDTO;
        this.thatQuantityDTO = thatQuantityDTO;
    }

    public QuantityInputDTO(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, Unit targetUnit) {
        this.thisQuantityDTO = thisQuantityDTO;
        this.thatQuantityDTO = thatQuantityDTO;
        this.targetUnit = targetUnit;
    }
}