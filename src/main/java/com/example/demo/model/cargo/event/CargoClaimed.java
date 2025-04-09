package com.example.demo.model.cargo.event;

import com.example.demo.model.port.PortId;

import java.time.LocalDateTime;

public record CargoClaimed(
        CargoId cargoId,
        PortId portId,
        LocalDateTime timestamp) implements CargoEvent {

    @Override
    public CargoId getCargoId() {
        return cargoId;
    }

    @Override
    public PortId getPortId() {
        return portId;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
