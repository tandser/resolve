package ru.tandser.resolve;

import java.util.stream.IntStream;

public class Resolve {

    public static void main(String[] args) {
        IntStream.range(0, 25).forEachOrdered(i -> System.out.println(i * i));
    }
}