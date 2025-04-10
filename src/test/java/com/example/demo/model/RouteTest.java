package com.example.demo.model;

import com.example.demo.model.port.PortId;
import com.example.demo.model.route.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RouteTest {

    @Test
    @DisplayName("출발지와 목적지가 주어지면 경유지(Balboa)가 포함된 루트가 생성된다.")
    void createRoute() {
        PortId origin = new PortId("KRSEL");
        PortId destination = new PortId("USNYC");

        Route route = Route.createRoute(origin, destination);

        assertThat(route.getOrigin()).isEqualTo(origin);
        assertThat(route.getTransit()).containsExactly(new PortId("BALBOA"));
        assertThat(route.getDestination()).isEqualTo(destination);
    }

    @DisplayName("출발지가 비어있으면 루트가 생성되지않는다.")
    @Test
    void notCreateRouteWithNullOrigin() {
        PortId destination = new PortId("USNYC");

        assertThatThrownBy(() -> Route.createRoute(null, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("목적지가 비어있으면 루트가 생성되지않는다.")
    @Test
    void notCreateRouteWithNullDestination() {
        PortId origin = new PortId("KRSEL");

        assertThatThrownBy(() -> Route.createRoute(origin, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
