package ru.tandser.resolve;

import ru.tandser.resolve.model.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class DataGenerator {

    private DataGenerator() {}

    public static User[] userArray(int n) {
        return IntStream.range(0, n)
                        .mapToObj(i -> new User(randomAlphabetic(3, 10).toLowerCase(),
                                                randomAlphabetic(3, 10).toLowerCase(),
                                                randomLocalDate()))
                        .toArray(User[]::new);
    }

    public static Collection<User> userCollection(int n) {
        return Arrays.asList(userArray(n));
    }

    public static LocalDate randomLocalDate() {
        long minDay = LocalDate.of(1950, Month.JANUARY, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();

        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        return LocalDate.ofEpochDay(randomDay);
    }
}