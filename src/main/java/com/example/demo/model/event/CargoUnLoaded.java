package com.example.demo.model.event;

import com.example.demo.model.cargo.CargoId;
import com.example.demo.model.port.PortId;

import java.time.LocalDateTime;

public class CargoUnLoaded extends DeliveryEvent {
    public CargoUnLoaded(CargoId cargoId, PortId portId, LocalDateTime eventTime) {
        super(cargoId, portId, eventTime);
    }
}
