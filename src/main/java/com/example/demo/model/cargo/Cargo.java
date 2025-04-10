package com.example.demo.model.cargo;

import com.example.demo.model.event.DeliveryEvent;
import com.example.demo.model.event.DeliveryEventFactory;
import com.example.demo.model.port.PortId;
import com.example.demo.model.route.Route;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class Cargo {

    private CargoId id;
    private Route route;
    private PortId origin;
    private PortId destination;
    private LocalDateTime arrivalDeadline;

    private Size size;
    private Weight weight;
    private String description;

    public Cargo(PortId origin,
                 PortId destination,
                 LocalDateTime arrivalDeadline,
                 Size size, Weight weight, String description) {
        this.id = CargoId.newId();
        this.origin = origin;
        this.destination = destination;
        this.arrivalDeadline = arrivalDeadline;
        this.size = size;
        this.weight = weight;
        this.description = description;
    }

    public void assignRoute(Route route) {
        if (this.route != null) {
            throw new IllegalStateException("이미 경로가 할당된 화물입니다.");
        }

        if (!route.getOrigin().equals(this.origin)) {
            throw new IllegalArgumentException("화물의 출발지와 경로의 출발지가 일치하지 않습니다.");
        }

        if (!route.getDestination().equals(this.destination)) {
            throw new IllegalArgumentException("화물의 목적지와 경로의 목적지가 일치하지 않습니다.");
        }
        this.route = route;
    }

    public DeliveryEvent createLoadEvent(PortId portId, LocalDateTime eventTime) {
        return DeliveryEventFactory.createLoadEvent(this.id, portId, eventTime);
    }

    public DeliveryEvent createUnloadEvent(PortId portId, LocalDateTime eventTime) {
        return DeliveryEventFactory.createUnloadEvent(this.id, portId, eventTime);
    }

    public DeliveryEvent createReceiveEvent(PortId portId, LocalDateTime eventTime) {
        return DeliveryEventFactory.createReceivedEvent(id, portId, eventTime);
    }

    public List<DeliveryEvent> getTrackingInfo(List<DeliveryEvent> events) {
        return events.stream()
                .sorted(Comparator.comparing(DeliveryEvent::getTimestamp))
                .toList();
    }

    public PortId getCurrentPort(List<DeliveryEvent> events) {
        return events.stream()
                .max(Comparator.comparing(DeliveryEvent::getTimestamp))
                .map(DeliveryEvent::getPortId)
                .orElseThrow(() -> new IllegalStateException("이벤트가 없습니다."));
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

    public PortId getOrigin() {
        return origin;
    }

    public PortId getDestination() {
        return destination;
    }
}
