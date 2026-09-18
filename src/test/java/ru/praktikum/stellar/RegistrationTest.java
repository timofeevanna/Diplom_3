package ru.praktikum.stellar;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.praktikum.stellar.model.User;
import ru.praktikum.stellar.pages.*;
import ru.praktikum.stellar.support.BaseUiTest;
import static org.junit.Assert.*;

public class RegistrationTest extends BaseUiTest {
    @Test
    @DisplayName("Успешная регистрация с обычным паролем")
    public void shouldRegisterUser() {
        registerAndCheckLogin();
    }

    @Test
    @DisplayName("Успешная регистрация с паролем ровно из 6 символов")
    public void shouldRegisterWithMinimumPassword() {
        user = new User(user.getEmail(), "Abc123", user.getName());
        registerAndCheckLogin();
    }

    private void registerAndCheckLogin() {
        RegistrationPage registration = new MainPage(driver).open().clickLogin().openRegistration();
        registrationAttempted = true;
        registration.register(user.getName(), user.getEmail(), user.getPassword());
        LoginPage login = new LoginPage(driver).waitForOpen();
        assertTrue("После регистрации открывается вход", login.isOpen());
        checkLoggedIn(login.login(user.getEmail(), user.getPassword()));
    }

    @Test
    @DisplayName("Пароль из 5 символов вызывает ошибку")
    public void shouldRejectFiveCharacterPassword() {
        checkShortPassword("Abc12");
    }

    @Test
    @DisplayName("Пароль из 1 символа вызывает ошибку")
    public void shouldRejectOneCharacterPassword() {
        checkShortPassword("A");
    }

    private void checkShortPassword(String password) {
        user = new User(user.getEmail(), password, user.getName());
        RegistrationPage registration = new MainPage(driver).open().clickLogin().openRegistration();
        registrationAttempted = true;
        registration.register(user.getName(), user.getEmail(), user.getPassword());
        assertEquals("Некорректный пароль", registration.passwordErrorText());
        assertTrue("Форма регистрации остаётся открытой", registration.isOpen());
    }
}
