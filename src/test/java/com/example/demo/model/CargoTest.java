package com.example.demo.model;

import com.example.demo.model.cargo.Cargo;
import com.example.demo.model.cargo.event.CargoClaimed;
import com.example.demo.model.cargo.event.CargoEvent;
import com.example.demo.model.cargo.event.CargoLoaded;
import com.example.demo.model.cargo.event.CargoReceived;
import com.example.demo.model.cargo.event.CargoUnLoaded;
import com.example.demo.model.cargo.Size;
import com.example.demo.model.cargo.Weight;
import com.example.demo.model.cargo.WeightUnit;
import com.example.demo.model.port.PortId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CargoTest {

    private PortId originPortId;
    private PortId destinationPortId;
    private Size size;
    private Weight weight;
    private String description;

    @BeforeEach
    void setUp() {
        originPortId = new PortId("KRSEL");
        destinationPortId = new PortId("USNYC");
        size = new Size(10.0, 15.0, 30.0);
        weight = new Weight(100.0, WeightUnit.KILOGRAM);
        description = "의류 상자";
    }

    @Test
    @DisplayName("화물을 생성하면 경로, 무게, 크기, 설명이 올바르게 설정된다.")
    void createCargoWithRoute() {

        Cargo cargo = new Cargo(originPortId, destinationPortId, size, weight, description);

        assertThat(cargo.getId()).isNotNull();
        assertThat(cargo.getSize()).isEqualTo(size);
        assertThat(cargo.getWeight()).isEqualTo(weight);
        assertThat(cargo.getDescription()).isEqualTo(description);

        assertThat(cargo.getRoute().routeItems()).hasSize(3);
        assertThat(cargo.getRoute().getOriginPortId()).isEqualTo(originPortId);
        assertThat(cargo.getRoute().getDestinationPortId()).isEqualTo(destinationPortId);
    }

    @DisplayName("화물은 각 항구에서 이벤트 발생시킨다.")
    @Test
    void occurredDeliveryEvents() {
        // given
        PortId port = new PortId("USNYC");
        Cargo cargo = new Cargo(originPortId, destinationPortId, size, weight, description);

        // when
        cargo.loadAt(port);
        cargo.unloadAt(port);
        cargo.claimAt(port);
        cargo.receiveAt(port);

        // then
        List<CargoEvent> events = cargo.getDomainEvents();
        assertThat(events).hasSize(4);

        assertThat(events)
                .extracting("class")
                .containsExactly(
                        CargoLoaded.class,
                        CargoUnLoaded.class,
                        CargoClaimed.class,
                        CargoReceived.class
                );
    }

    @DisplayName("만들어진 도메인 이벤트를 제거할수있다.")
    @Test
    void clearDeliveryEvents() {
        // given
        PortId port = new PortId("USNYC");
        Cargo cargo = new Cargo(originPortId, destinationPortId, size, weight, description);

        cargo.loadAt(port);
        assertThat(cargo.getDomainEvents()).hasSize(1);

        // when
        cargo.clearDomainEvents();

        // then
        assertThat(cargo.getDomainEvents()).isEmpty();
    }
}
