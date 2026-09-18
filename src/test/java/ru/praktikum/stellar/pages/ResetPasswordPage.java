package ru.praktikum.stellar.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ResetPasswordPage extends BasePage {
    private final By heading = By.xpath("//h2[text()='Восстановление пароля']");
    private final By loginLink = By.linkText("Войти");

    public ResetPasswordPage(WebDriver driver) { super(driver); }

    public ResetPasswordPage waitForOpen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return this;
    }

    @Step("Перейти ко входу из формы восстановления пароля")
    public LoginPage clickLogin() {
        click(loginLink);
        return new LoginPage(driver).waitForOpen();
    }
}
