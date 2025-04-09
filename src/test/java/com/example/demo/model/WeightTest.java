package com.example.demo.model;

import com.example.demo.model.cargo.Weight;
import com.example.demo.model.cargo.WeightUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WeightTest {

    @Test
    @DisplayName("올바른 무게와 단위를 입력하면 정상적으로 생성된다.")
    void validWeight() {
        Weight weight = new Weight(10.0, WeightUnit.KILOGRAM);

        assertThat(weight.value()).isEqualTo(10.0);
        assertThat(weight.unit()).isEqualTo(WeightUnit.KILOGRAM);
    }

    @Test
    @DisplayName("무게 값이 0 이하이면 예외가 발생한다.")
    void weightValueMustBePositive() {
        assertThatThrownBy(() -> new Weight(0, WeightUnit.KILOGRAM))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("무게 단위가 null이면 예외가 발생한다.")
    void weightUnitMustNotBeNull() {
        assertThatThrownBy(() -> new Weight(5, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
