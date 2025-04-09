package com.example.demo.model;

import com.example.demo.model.port.PortId;
import com.example.demo.model.route.Route;
import com.example.demo.model.route.RouteItem;
import com.example.demo.model.route.RouteItemType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RouteTest {

    @Test
    @DisplayName("출발지와 목적지가 주어지면 경유지(Balboa)가 포함된 루트가 생성된다.")
    void createRoute() {

        PortId origin = new PortId("KRSEL");
        PortId destination = new PortId("USNYC");

        Route route = Route.createRoute(origin, destination);

        assertThat(route.getOriginPortId()).isEqualTo(origin);
        assertThat(route.getTransitPortIds()).containsExactly(new PortId("Balboa"));
        assertThat(route.getDestinationPortId()).isEqualTo(destination);
    }

    @Test
    @DisplayName("출발지 항목이 없으면 예외를 발생시킨다.")
    void throwExceptionWhenNoOriginPortId() {

        List<RouteItem> routeItems = List.of(
                new RouteItem(new PortId("KRSEL"), RouteItemType.TRANSIT),
                new RouteItem(new PortId("USNYC"), RouteItemType.DESTINATION)
        );

        Route route = new Route(routeItems);

        assertThatThrownBy(route::getOriginPortId)
                .isInstanceOf(IllegalStateException.class);

    }

    @Test
    @DisplayName("도착지 항목이 없으면 예외를 발생시킨다.")
    void throwExceptionWhenNoDestinationPortId() {

        List<RouteItem> routeItems = List.of(
                new RouteItem(new PortId("KRSEL"), RouteItemType.ORIGIN),
                new RouteItem(new PortId("USNYC"), RouteItemType.TRANSIT)
        );

        Route route = new Route(routeItems);

        assertThatThrownBy(route::getDestinationPortId)
                .isInstanceOf(IllegalStateException.class);
    }

}
