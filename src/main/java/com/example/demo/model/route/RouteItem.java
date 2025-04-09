package com.example.demo.model.route;

import com.example.demo.model.port.PortId;

public record RouteItem(
        PortId portId,
        RouteItemType type
) {
}
