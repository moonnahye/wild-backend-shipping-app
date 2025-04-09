package com.example.demo.model.deliveryevent;

import com.example.demo.model.port.PortId;
import com.example.demo.model.cargo.event.CargoEvent;
import com.example.demo.model.cargo.event.CargoId;

import java.time.LocalDateTime;

public class DeliveryEvent {

    private DeliveryEventId id;
    private CargoId cargoId;

    private PortId portId;
    private LocalDateTime timestamp;

    public DeliveryEvent(CargoId cargoId, PortId portId, LocalDateTime timestamp) {
        this.id = DeliveryEventId.newId();
        this.cargoId = cargoId;
        this.portId = portId;
        this.timestamp = timestamp;
    }

    public static DeliveryEvent from(CargoEvent event) {
        return new DeliveryEvent(event.getCargoId(), event.getPortId(), event.getTimestamp());
    }

    public DeliveryEventId getId() {
        return id;
    }

    public CargoId getCargoId() {
        return cargoId;
    }

    public PortId getPortId() {
        return portId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
