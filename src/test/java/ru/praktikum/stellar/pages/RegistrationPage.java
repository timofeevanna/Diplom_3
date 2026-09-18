package ru.praktikum.stellar.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationPage extends BasePage {
    private final By heading = By.xpath("//h2[text()='Регистрация']");
    private final By name = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By email = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By password = By.name("Пароль");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.linkText("Войти");
    private final By passwordError = By.xpath("//p[text()='Некорректный пароль']");

    public RegistrationPage(WebDriver driver) { super(driver); }

    public RegistrationPage waitForOpen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return this;
    }

    @Step("Заполнить и отправить форму регистрации")
    public void register(String userName, String userEmail, String userPassword) {
        fill(name, userName);
        fill(email, userEmail);
        fill(password, userPassword);
        click(registerButton);
    }

    @Step("Получить ошибку некорректного пароля")
    public String passwordErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).getText();
    }

    public boolean isOpen() {
        return driver.getCurrentUrl().equals(BASE_URL + "/register") && driver.findElement(heading).isDisplayed();
    }

    @Step("Перейти ко входу из формы регистрации")
    public LoginPage clickLogin() {
        click(loginLink);
        return new LoginPage(driver).waitForOpen();
    }
}
