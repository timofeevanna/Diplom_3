package ru.praktikum.stellar.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private final By heading = By.xpath("//h2[text()='Вход']");
    private final By email = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By password = By.name("Пароль");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registrationLink = By.linkText("Зарегистрироваться");
    private final By resetPasswordLink = By.linkText("Восстановить пароль");

    public LoginPage(WebDriver driver) { super(driver); }

    public LoginPage waitForOpen() {
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/login"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return this;
    }

    @Step("Выполнить вход через форму")
    public MainPage login(String userEmail, String userPassword) {
        fill(email, userEmail);
        fill(password, userPassword);
        click(loginButton);
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/"));
        return new MainPage(driver);
    }

    @Step("Открыть форму регистрации")
    public RegistrationPage openRegistration() {
        click(registrationLink);
        return new RegistrationPage(driver).waitForOpen();
    }

    @Step("Открыть форму восстановления пароля")
    public ResetPasswordPage openResetPassword() {
        click(resetPasswordLink);
        return new ResetPasswordPage(driver).waitForOpen();
    }

    public boolean isOpen() {
        return driver.getCurrentUrl().equals(BASE_URL + "/login") && driver.findElement(heading).isDisplayed();
    }
}
