package com.example.demo.model.cargo;

public record Size (
        double width,   // 가로
        double length,  // 세로
        double height   // 높이
){
    public Size {
        if(width <= 0 || length <= 0 || height <= 0) {
            throw new IllegalArgumentException("길이는 0보다 커야합니다.");
        }
    }

    public double volume() {
        return width * length * height;
    }
}
