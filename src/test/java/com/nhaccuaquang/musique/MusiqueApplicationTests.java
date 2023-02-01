package com.nhaccuaquang.musique;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MusiqueApplicationTests {
    Calculator underTest = new Calculator();

    @Test
    void itShouldAddNumbers() {
        int num1 = 10;
        int num2 = 30;

        int result = underTest.add(num1, num2);

        assertThat(result).isEqualTo(40);
    }

    class Calculator{
        int add(int a, int b){
            return a+b;
        }
    }

    @Test
    void contextLoads() {
    }

}
