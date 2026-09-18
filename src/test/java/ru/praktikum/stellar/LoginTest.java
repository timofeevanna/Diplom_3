package ru.praktikum.stellar;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.stellar.pages.*;
import ru.praktikum.stellar.support.BaseUiTest;

public class LoginTest extends BaseUiTest {
    @Before
    public void registerUser() {
        createTestUser();
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void shouldLoginFromMainPage() {
        LoginPage login = new MainPage(driver).open().clickLogin();
        checkLoggedIn(login.login(user.getEmail(), user.getPassword()));
    }

    @Test
    @DisplayName("Вход через «Личный кабинет»")
    public void shouldLoginFromPersonalAccount() {
        LoginPage login = new MainPage(driver).open().clickAccountForLogin();
        checkLoggedIn(login.login(user.getEmail(), user.getPassword()));
    }

    @Test
    @DisplayName("Вход через ссылку в форме регистрации")
    public void shouldLoginFromRegistration() {
        LoginPage login = new MainPage(driver).open().clickLogin().openRegistration().clickLogin();
        checkLoggedIn(login.login(user.getEmail(), user.getPassword()));
    }

    @Test
    @DisplayName("Вход через ссылку в форме восстановления пароля")
    public void shouldLoginFromPasswordReset() {
        LoginPage login = new MainPage(driver).open().clickLogin().openResetPassword().clickLogin();
        checkLoggedIn(login.login(user.getEmail(), user.getPassword()));
    }
}
