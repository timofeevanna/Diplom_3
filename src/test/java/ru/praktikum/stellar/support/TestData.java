package ru.praktikum.stellar.support;

import java.util.UUID;
import ru.praktikum.stellar.model.User;

public final class TestData {
    private TestData() { }

    public static User newUser() {
        return new User("diplom-" + UUID.randomUUID() + "@example.com", "Stellar123!", "Анна");
    }
}
