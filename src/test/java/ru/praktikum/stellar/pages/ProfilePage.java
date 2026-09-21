package ru.praktikum.stellar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {
    private final By name = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By email = By.xpath("//label[text()='Логин']/following-sibling::input");

    public ProfilePage(WebDriver driver) { super(driver); }

    public ProfilePage waitForOpen() {
        wait.until(ExpectedConditions.urlContains("/account/profile"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        return this;
    }

    public String email() { return driver.findElement(email).getAttribute("value"); }
    public String name() { return driver.findElement(name).getAttribute("value"); }
}
