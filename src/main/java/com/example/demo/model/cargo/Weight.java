package com.example.demo.model.cargo;

public record Weight(
        double value,
        WeightUnit unit
) {
    public Weight {
        if (value <= 0) {
            throw new IllegalArgumentException("무게는 0보다 커야 합니다.");
        }
        if (unit == null) {
            throw new IllegalArgumentException("무게 단위를 지정해야 합니다.");
        }
    }
}
