package com.example.demo.model.cargo.event;

import java.util.UUID;

public record CargoId(
        String id
) {

    public static CargoId newId() {
        return new CargoId(UUID.randomUUID().toString());
    }
}
