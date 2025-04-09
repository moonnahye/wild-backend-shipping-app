package com.example.demo.model.cargo;

import com.example.demo.model.cargo.event.CargoClaimed;
import com.example.demo.model.cargo.event.CargoEvent;
import com.example.demo.model.cargo.event.CargoId;
import com.example.demo.model.cargo.event.CargoLoaded;
import com.example.demo.model.cargo.event.CargoReceived;
import com.example.demo.model.cargo.event.CargoUnLoaded;
import com.example.demo.model.port.PortId;
import com.example.demo.model.route.Route;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cargo {

    private CargoId id;
    private Route route;

    private Size size;
    private Weight weight;
    private String description;

    private final List<CargoEvent> domainEvents = new ArrayList<>();

    public Cargo(PortId origin, PortId destination, Size size, Weight weight, String description) {
        this.id = CargoId.newId();
        this.route = Route.createRoute(origin, destination);
        this.size = size;
        this.weight = weight;
        this.description = description;
    }

    public void loadAt(PortId portId) {
        domainEvents.add(new CargoLoaded(id, portId, LocalDateTime.now()));
    }

    public void unloadAt(PortId portId) {
        domainEvents.add(new CargoUnLoaded(id, portId, LocalDateTime.now()));
    }

    public void claimAt(PortId portId) {
        domainEvents.add(new CargoClaimed(id, portId, LocalDateTime.now()));

    }

    public void receiveAt(PortId portId) {
        domainEvents.add(new CargoReceived(id, portId, LocalDateTime.now()));

    }

    public List<CargoEvent> getDomainEvents() {
        return List.copyOf(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public CargoId getId() {
        return id;
    }

    public Route getRoute() {
        return route;
    }

    public Size getSize() {
        return size;
    }

    public Weight getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }
}
