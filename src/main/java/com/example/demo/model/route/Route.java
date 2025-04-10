package com.example.demo.model.route;

import com.example.demo.model.port.PortId;

import java.util.ArrayList;
import java.util.List;

public record Route(
        List<PortId> portIds
) {
    public static Route createRoute(PortId origin, PortId destination) {

        if (origin == null || destination == null) {
            throw new IllegalArgumentException("출발지나 도착지가 없습니다.");
        }

        List<PortId> portIds = new ArrayList<>();
        portIds.add(origin);
        portIds.add(new PortId("BALBOA"));
        portIds.add(destination);

        return new Route(portIds);
    }

    public PortId getOrigin() {
        return portIds.getFirst();
    }

    public List<PortId> getTransit() {
        return portIds.subList(1, portIds.size()-1)
                .stream()
                .toList();
    }

    public PortId getDestination() {
        return portIds.getLast();
    }
}
