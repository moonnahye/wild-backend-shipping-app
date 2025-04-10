package com.example.demo.model;

import com.example.demo.model.cargo.Cargo;
import com.example.demo.model.cargo.Size;
import com.example.demo.model.cargo.Weight;
import com.example.demo.model.cargo.WeightUnit;
import com.example.demo.model.event.CargoLoaded;
import com.example.demo.model.event.CargoReceived;
import com.example.demo.model.event.CargoUnLoaded;
import com.example.demo.model.event.DeliveryEvent;
import com.example.demo.model.port.PortId;
import com.example.demo.model.route.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CargoTest {

    @DisplayName("화물을 생성하면 식별자가 주어지고, 경로는 설정되있지 않다.")
    @Test
    void createCargo() {
        Cargo cargo = createSampleCargo();

        assertThat(cargo.getId()).isNotNull();
        assertThat(cargo.getRoute()).isNull();
    }

    @DisplayName("화물은 유효한 경로를 할당한다.")
    @Test
    void assignValidRoute() {
        // given
        Cargo cargo = createSampleCargo();
        Route route = Route.createRoute(cargo.getOrigin(), cargo.getDestination());

        // when
        cargo.assignRoute(route);

        // then
        assertThat(cargo.getRoute()).isEqualTo(route);
    }

    @DisplayName("화물의 출발지와 다른 경로가 할당되면 예외가 발생한다.")
    @Test
    void assignedWrongOrigin() {
        Cargo cargo = createSampleCargo();
        PortId wrongOrigin = new PortId("XXX");
        Route route = Route.createRoute(wrongOrigin, cargo.getDestination());

        assertThatThrownBy(() -> cargo.assignRoute(route))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("화물의 목적지와 다른 경로가 할당되면 예외가 발생한다.")
    @Test
    void assignedWrongDestination() {
        Cargo cargo = createSampleCargo();
        PortId wrongDestination = new PortId("XXX");
        Route route = Route.createRoute(cargo.getOrigin(), wrongDestination);

        assertThatThrownBy(() -> cargo.assignRoute(route))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("경로가 이미 할당되어 있을때, 또 경로가 할당되면 예외가 발생한다.")
    @Test
    void assignRouteWhenAlreadyAssigned() {
        // given
        Cargo cargo = createSampleCargo();
        Route route = Route.createRoute(cargo.getOrigin(), cargo.getDestination());
        cargo.assignRoute(route);

        // when & then
        assertThatThrownBy(() -> cargo.assignRoute(route))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("지정된 항구에서 적재(LOAD) 이벤트를 생성할수있다.")
    @Test
    void createLoadEvent() {
        // given
        Cargo cargo = createSampleCargo();
        PortId port = new PortId("KRSEL");
        LocalDateTime eventTime = LocalDateTime.now();

        // when
        DeliveryEvent event = cargo.createLoadEvent(port, eventTime);

        // then
        assertThat(event).isInstanceOf(CargoLoaded.class);
        assertThat(event.getCargoId()).isEqualTo(cargo.getId());
        assertThat(event.getPortId()).isEqualTo(port);
        assertThat(event.getTimestamp()).isEqualTo(eventTime);
    }

    @DisplayName("지정된 항구에서 하역(UNLOAD) 이벤트를 생성할수있다.")
    @Test
    void createUnloadEvent() {
        // given
        Cargo cargo = createSampleCargo();
        PortId port = new PortId("KRSEL");
        LocalDateTime eventTime = LocalDateTime.now();

        // when
        DeliveryEvent event = cargo.createUnloadEvent(port, eventTime);

        // then
        assertThat(event).isInstanceOf(CargoUnLoaded.class);
        assertThat(event.getCargoId()).isEqualTo(cargo.getId());
        assertThat(event.getPortId()).isEqualTo(port);
        assertThat(event.getTimestamp()).isEqualTo(eventTime);
    }

    @DisplayName("지정된 항구에서 수령(RECEIVED) 이벤트를 생성할수있다.")
    @Test
    void createReceiveEvent() {
        // given
        Cargo cargo = createSampleCargo();
        PortId port = new PortId("KRSEL");
        LocalDateTime eventTime = LocalDateTime.now();

        // when
        DeliveryEvent event = cargo.createReceiveEvent(port, eventTime);

        // then
        assertThat(event).isInstanceOf(CargoReceived.class);
        assertThat(event.getCargoId()).isEqualTo(cargo.getId());
        assertThat(event.getPortId()).isEqualTo(port);
        assertThat(event.getTimestamp()).isEqualTo(eventTime);
    }

    @DisplayName("화물의 전체 배송 상황을 등록된 시간 순서대로 알수있다.")
    @Test
    void getTrackingInfo() {
        // given
        Cargo cargo = createSampleCargo();
        PortId port1 = new PortId("KRSEL");
        PortId port2 = new PortId("BALBOA");

        LocalDateTime eventTime1 = LocalDateTime.of(2025, 4, 5, 14, 0);
        LocalDateTime eventTime2 = LocalDateTime.of(2025, 4, 3, 9, 0);
        LocalDateTime eventTime3 = LocalDateTime.of(2025, 4, 7, 12, 0);

        DeliveryEvent event1 = new CargoLoaded(cargo.getId(), port1, eventTime1);
        DeliveryEvent event2 = new CargoUnLoaded(cargo.getId(), port2, eventTime2);
        DeliveryEvent event3 = new CargoReceived(cargo.getId(), port2, eventTime3);

        List<DeliveryEvent> events = List.of(event1, event2, event3);

        // when
        List<DeliveryEvent> sortedEvents = cargo.getTrackingInfo(events);

        // then
        assertThat(sortedEvents).containsExactly(event2, event1, event3);
    }

    @DisplayName("화물의 현재 위치(Port)를 알 수 있다.")
    @Test
    void getCurrentPort() {
        // given
        Cargo cargo = createSampleCargo();
        PortId port1 = new PortId("KRSEL");
        PortId port2 = new PortId("BALBOA");
        PortId port3 = new PortId("USNYC");

        LocalDateTime eventTime1 = LocalDateTime.of(2025, 4, 1, 9, 0);
        LocalDateTime eventTime2 = LocalDateTime.of(2025, 4, 3, 10, 0);
        LocalDateTime eventTime3 = LocalDateTime.of(2025, 4, 4, 8, 0);
        LocalDateTime eventTime4 = LocalDateTime.of(2025, 4, 8, 14, 0);

        List<DeliveryEvent> events = List.of(
                new CargoLoaded(cargo.getId(), port1, eventTime1),
                new CargoUnLoaded(cargo.getId(), port2, eventTime2),
                new CargoLoaded(cargo.getId(), port2, eventTime3),
                new CargoUnLoaded(cargo.getId(), port3, eventTime4)
        );

        // when
        PortId currentPort = cargo.getCurrentPort(events);

        // then
        assertThat(currentPort).isEqualTo(port3);
    }

    private Cargo createSampleCargo() {
        return new Cargo(
                new PortId("KRSEL"),
                new PortId("USNYC"),
                LocalDateTime.of(2025, 5, 10, 10, 0),
                new Size(10.0, 20.0, 30.0),
                new Weight(100.0, WeightUnit.KILOGRAM),
                "전자기기"
        );
    }
}
