package com.example.demo.model;

import com.example.demo.model.cargo.Size;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SizeTest {

    @Test
    @DisplayName("가로, 세로, 높이가 모두 0보다 클때 Size가 생성된다.")
    void validSize() {
        double width = 10.5;
        double length = 20.5;
        double height = 30.0;

        Size size = new Size(width, length, height);

        assertThat(size.width()).isEqualTo(width);
        assertThat(size.length()).isEqualTo(length);
        assertThat(size.height()).isEqualTo(height);
    }

    @Test
    @DisplayName("길이가 0인 경우 예외가 발생한다.")
    void invalidSize() {
        assertThatThrownBy(() -> new Size(0, 2.0, 3.0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("부피를 정확히 계산할수 있다.")
    void calculateVolume() {
        double width = 2.0;
        double length = 3.0;
        double height = 4.0;
        Size size = new Size(width, length, height);

        assertThat(size.volume()).isEqualTo(width * length * height);
    }

}
