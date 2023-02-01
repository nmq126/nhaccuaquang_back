package com.nhaccuaquang.musique.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DateTimeHandlerTest {

    private DateTimeHandler underTest;

    @BeforeEach
    void setUp() {
        underTest = new DateTimeHandler();
    }

    @ParameterizedTest
    @ValueSource(strings = {"2021-10-03"})
    void itShouldReturnALocalDate(String date) {
        //when
        LocalDate localDate = underTest.convertStringToLocalDate(date);

        LocalDate expected =  LocalDate.of(2021, 10, 3);

        //then
        assertThat(localDate).isEqualTo(expected);
    }
}