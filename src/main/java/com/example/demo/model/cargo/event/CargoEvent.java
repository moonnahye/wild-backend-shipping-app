package com.example.demo.model.cargo.event;

import com.example.demo.model.port.PortId;

import java.time.LocalDateTime;

public interface CargoEvent {
    CargoId getCargoId();
    PortId getPortId();
    LocalDateTime getTimestamp();
}
