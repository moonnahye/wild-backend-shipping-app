package com.example.demo.model.event;

import com.example.demo.model.cargo.CargoId;
import com.example.demo.model.port.PortId;

import java.time.LocalDateTime;

public class DeliveryEventFactory {
    public static CargoLoaded createLoadEvent(CargoId cargoId, PortId portId, LocalDateTime eventTime) {
        return new CargoLoaded(cargoId, portId, eventTime);
    }

    public static CargoUnLoaded createUnloadEvent(CargoId cargoId, PortId portId, LocalDateTime eventTime) {
        return new CargoUnLoaded(cargoId, portId, eventTime);
    }

    public static CargoReceived createReceivedEvent(CargoId cargoId, PortId portId, LocalDateTime eventTime) {
        return new CargoReceived(cargoId, portId, eventTime);
    }
}
