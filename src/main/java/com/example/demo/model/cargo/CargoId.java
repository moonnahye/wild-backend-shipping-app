package com.example.demo.model.cargo;

import java.util.UUID;

public record CargoId(
        String id
) {

    public static CargoId newId() {
        return new CargoId(UUID.randomUUID().toString());
    }
}
