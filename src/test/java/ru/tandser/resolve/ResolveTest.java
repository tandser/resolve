package ru.tandser.resolve;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

public class ResolveTest {

    @Test
    public void test() {
        System.out.println(Period.between(LocalDate.now(), LocalDate.now()).getYears());
        System.out.println(LocalDate.class.getName());
    }
}