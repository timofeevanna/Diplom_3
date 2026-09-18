package ru.praktikum.stellar.support;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import ru.praktikum.stellar.client.UserClient;
import ru.praktikum.stellar.model.Credentials;
import ru.praktikum.stellar.model.User;
import ru.praktikum.stellar.pages.MainPage;
import ru.praktikum.stellar.pages.ProfilePage;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public abstract class BaseUiTest {
    @Parameterized.Parameter
    public String browser;
    protected WebDriver driver;
    protected User user;
    protected final UserClient userClient = new UserClient();
    private String accessToken;
    protected boolean registrationAttempted;

    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.stream(System.getProperty("browsers", "chrome").split(","))
                .map(value -> new Object[] {value.trim()}).collect(Collectors.toList());
    }

    @Before
    public void setUp() {
        user = TestData.newUser();
        driver = DriverFactory.create(browser);
        Allure.label("browser", browser);
    }

    @Rule
    public TestWatcher browserLifecycle = new TestWatcher() {
        @Override
        protected void failed(Throwable error, Description description) {
            if (driver != null) {
                Allure.addAttachment("Скриншот при ошибке", "image/png", new ByteArrayInputStream(
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)), ".png");
                Allure.addAttachment("Адрес страницы", driver.getCurrentUrl());
            }
        }

        @Override
        protected void finished(Description description) {
            if (driver != null) {
                driver.quit();
            }
        }
    };

    @Step("Создать пользователя через API для проверки входа")
    protected void createTestUser() {
        Response response = userClient.create(user);
        accessToken = response.path("accessToken");
        response.then().statusCode(200).body("success", equalTo(true));
    }

    @Step("Проверить успешный вход и данные профиля")
    protected void checkLoggedIn(MainPage mainPage) {
        Assert.assertTrue("После входа доступно оформление заказа", mainPage.isOrderButtonVisible());
        ProfilePage profile = mainPage.openProfile();
        Assert.assertEquals(user.getEmail(), profile.email());
        Assert.assertEquals(user.getName(), profile.name());
    }

    @After
    @Step("Удалить пользователя, созданного для UI-теста")
    public void cleanUpUser() {
        if (accessToken == null && registrationAttempted) {
            Response login = userClient.login(Credentials.from(user));
            if (login.statusCode() == 401) {
                login.then().body("success", equalTo(false));
                return;
            }
            login.then().statusCode(200).body("success", equalTo(true));
            accessToken = login.path("accessToken");
        }
        if (accessToken != null) {
            userClient.delete(accessToken).then().statusCode(202).body("success", equalTo(true));
        }
    }
}
