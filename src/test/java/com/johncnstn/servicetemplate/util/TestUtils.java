package com.johncnstn.servicetemplate.util;

import com.github.javafaker.Faker;
import com.johncnstn.servicetemplate.model.User;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public final class TestUtils {

    private static final Faker FAKE = Faker.instance(Locale.US, ThreadLocalRandom.current());

    public static User user() {
        return User.builder()
                .email(FAKE.internet().emailAddress())
                .firstName(FAKE.name().firstName())
                .lastName(FAKE.name().lastName())
                .description(FAKE.hobbit().quote())
                .build();
    }
}
