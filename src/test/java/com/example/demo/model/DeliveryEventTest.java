package com.example.demo.model;

import com.example.demo.model.cargo.event.CargoId;
import com.example.demo.model.cargo.event.CargoLoaded;
import com.example.demo.model.deliveryevent.DeliveryEvent;
import com.example.demo.model.port.PortId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryEventTest {

    @Test
    void createDeliveryEvent() {
        CargoId cargoId = CargoId.newId();
        PortId portId = new PortId("KRSEL");
        LocalDateTime timestamp = LocalDateTime.now();

        DeliveryEvent deliveryEvent =
                new DeliveryEvent(cargoId, portId, timestamp);

        assertThat(deliveryEvent.getId()).isNotNull();
        assertThat(deliveryEvent.getCargoId()).isEqualTo(cargoId);
        assertThat(deliveryEvent.getPortId()).isEqualTo(portId);
        assertThat(deliveryEvent.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    @DisplayName("CargoEvent를 DeliveryEvent로 변환할 수 있어야 한다.")
    void shouldConvertCargoEventToDeliveryEvent() {
        // given
        CargoId cargoId = CargoId.newId();
        PortId portId = new PortId("KRSEL");
        LocalDateTime timestamp = LocalDateTime.now();

        CargoLoaded cargoLoaded = new CargoLoaded(cargoId, portId, timestamp);

        // when
        DeliveryEvent deliveryEvent = DeliveryEvent.from(cargoLoaded);

        // then
        assertThat(deliveryEvent).isNotNull();
        assertThat(deliveryEvent.getCargoId()).isEqualTo(cargoId);
        assertThat(deliveryEvent.getPortId()).isEqualTo(portId);
        assertThat(deliveryEvent.getTimestamp()).isEqualTo(timestamp);
        assertThat(deliveryEvent.getId()).isNotNull();
    }
}
