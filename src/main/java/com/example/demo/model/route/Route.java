package com.example.demo.model.route;

import com.example.demo.model.port.PortId;

import java.util.ArrayList;
import java.util.List;

public record Route(
        List<RouteItem> routeItems
) {
    public static Route createRoute(PortId origin, PortId destination) {
        List<RouteItem> routeItems = new ArrayList<>();
        routeItems.add(new RouteItem(origin, RouteItemType.ORIGIN));
        routeItems.add(new RouteItem(new PortId("Balboa"), RouteItemType.TRANSIT));
        routeItems.add(new RouteItem(destination, RouteItemType.DESTINATION));

        return new Route(routeItems);
    }

    public PortId getOriginPortId() {
        return routeItems.stream()
                .filter(routeItem -> routeItem.type().equals(RouteItemType.ORIGIN))
                .findFirst()
                .map(RouteItem::portId)
                .orElseThrow(
                        () -> new IllegalStateException("Origin port not found")
                );
    }

    public List<PortId> getTransitPortIds() {
        return routeItems.stream()
                .filter(routeItem -> routeItem.type().equals(RouteItemType.TRANSIT))
                .map(RouteItem::portId)
                .toList();
    }

    public PortId getDestinationPortId() {
        return routeItems.stream()
                .filter(routeItem -> routeItem.type().equals(RouteItemType.DESTINATION))
                .findFirst()
                .map(RouteItem::portId)
                .orElseThrow(
                        () -> new IllegalStateException("Destination port not found")
                );
    }
}
