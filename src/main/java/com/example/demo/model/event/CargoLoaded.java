package com.example.demo.model.event;

import com.example.demo.model.cargo.CargoId;
import com.example.demo.model.port.PortId;

import java.time.LocalDateTime;

public class CargoLoaded extends DeliveryEvent {
    public CargoLoaded(CargoId cargoId, PortId portId, LocalDateTime eventTime) {
        super(cargoId, portId, eventTime);
    }
}
