package com.example.demo.model.event;

import java.util.UUID;

public record DeliveryEventId(
        String id
) {
    public static DeliveryEventId newId() {
        return new DeliveryEventId(UUID.randomUUID().toString());
    }
}
