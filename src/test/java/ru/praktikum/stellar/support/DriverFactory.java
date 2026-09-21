package ru.praktikum.stellar.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.time.Duration;
import java.util.Map;

public final class DriverFactory {
    private DriverFactory() { }

    public static WebDriver create(String browser) {
        if (!browser.equals("chrome") && !browser.equals("yandex")) {
            throw new IllegalArgumentException("Поддерживаются chrome и yandex: " + browser);
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,1000");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false, "profile.password_manager_enabled", false));
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
        }
        String binary = System.getProperty(browser + "Binary");
        if (binary != null) {
            options.setBinary(binary);
        } else if (browser.equals("yandex")) {
            throw new IllegalArgumentException("Укажите путь к Яндекс Браузеру: -DyandexBinary=...");
        }
        String driverPath = System.getProperty(browser + "Driver");
        WebDriver driver;
        if (driverPath != null) {
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(driverPath)).usingAnyFreePort().build();
            driver = new ChromeDriver(service, options);
        } else {
            driver = new ChromeDriver(options);
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
        return driver;
    }
}
