package ru.praktikum.stellar.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {
    private final By heading = By.xpath("//h1[text()='Соберите бургер']");
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By accountLink = By.xpath("//a[@href='/account']");
    private final By orderButton = By.xpath("//button[text()='Оформить заказ']");
    private final By ingredientCard = By.cssSelector("a[href^='/ingredient/']");
    private final By ingredientImages = By.cssSelector("a[href^='/ingredient/'] img");
    private final By activeTab = By.cssSelector("div[class*='tab_tab_type_current']");

    public MainPage(WebDriver driver) { super(driver); }

    @Step("Открыть главную страницу")
    public MainPage open() {
        driver.get(BASE_URL + "/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        wait.until(ExpectedConditions.presenceOfElementLocated(ingredientCard));
        return this;
    }

    @Step("Перейти ко входу по кнопке «Войти в аккаунт»")
    public LoginPage clickLogin() {
        click(loginButton);
        return new LoginPage(driver).waitForOpen();
    }

    @Step("Перейти ко входу через личный кабинет")
    public LoginPage clickAccountForLogin() {
        click(accountLink);
        return new LoginPage(driver).waitForOpen();
    }

    @Step("Проверить кнопку оформления заказа после входа")
    public boolean isOrderButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderButton)).isDisplayed();
    }

    @Step("Открыть профиль авторизованного пользователя")
    public ProfilePage openProfile() {
        click(accountLink);
        return new ProfilePage(driver).waitForOpen();
    }

    private By tab(String section) {
        return By.xpath("//div[contains(@class,'tab_tab__')][span[text()='" + section + "']]");
    }

    @Step("Перейти к разделу «{section}»")
    public MainPage selectSection(String section) {
        waitForIngredientImages();
        click(tab(section));
        isSectionAtTop(section);
        wait.until(ExpectedConditions.attributeContains(tab(section), "class", "tab_tab_type_current"));
        return this;
    }

    @Step("Получить активный раздел конструктора")
    public String activeSection() {
        return driver.findElement(activeTab).getText();
    }

    @Step("Дождаться загрузки изображений ингредиентов перед прокруткой")
    private void waitForIngredientImages() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ingredientImages));
        wait.until(d -> d.findElements(ingredientImages).stream().allMatch(image ->
                "true".equals(image.getDomProperty("complete"))
                        && Integer.parseInt(image.getDomProperty("naturalWidth")) > 0));
    }

    @Step("Проверить прокрутку к разделу «{section}»")
    public boolean isSectionAtTop(String section) {
        By sectionHeading = By.xpath("//h2[text()='" + section + "']");
        // После прокрутки заголовок выбранной группы расположен сразу под вкладками.
        return wait.until(d -> {
            int tabsBottom = d.findElement(tab(section)).getRect().getY()
                    + d.findElement(tab(section)).getRect().getHeight();
            int headingTop = d.findElement(sectionHeading).getRect().getY();
            return headingTop >= tabsBottom - 5 && headingTop <= tabsBottom + 80;
        });
    }
}
