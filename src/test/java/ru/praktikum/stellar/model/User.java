package ru.praktikum.stellar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private final String email;
    private final String password;
    private final String name;
}
