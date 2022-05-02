package com.kmvpsolutions.backend;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BackendApplicationTests {

    @Test
    void itShouldAddTwoNumbers() {
        Calculator calc = new Calculator();

        //given
        int value01 = 5;
        int value02 = 10;

        //when
        int result = calc.add(value01, value02);

        //then
        assertThat(result).isEqualTo(15);
    }

    class Calculator {
        int add(int x, int y) {
            return x + y;
        }
    }

}
