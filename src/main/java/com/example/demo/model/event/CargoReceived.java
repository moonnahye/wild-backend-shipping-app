package com.example.demo.model.event;

import com.example.demo.model.cargo.CargoId;
import com.example.demo.model.port.PortId;

import java.time.LocalDateTime;

public class CargoReceived extends DeliveryEvent {
    public CargoReceived(CargoId cargoId, PortId portId, LocalDateTime timestamp) {
        super(cargoId, portId, timestamp);
    }
}
