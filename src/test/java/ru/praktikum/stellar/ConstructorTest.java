package ru.praktikum.stellar;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.praktikum.stellar.pages.MainPage;
import ru.praktikum.stellar.support.BaseUiTest;
import static org.junit.Assert.*;

public class ConstructorTest extends BaseUiTest {
    @Test
    @DisplayName("Переход к булкам из другого раздела")
    public void shouldSwitchToBuns() {
        MainPage main = new MainPage(driver).open().selectSection("Начинки");
        assertEquals("Начинки", main.activeSection());
        main.selectSection("Булки");
        assertEquals("Булки", main.activeSection());
        assertTrue(main.isSectionAtTop("Булки"));
    }

    @Test
    @DisplayName("Переход к соусам")
    public void shouldSwitchToSauces() {
        MainPage main = new MainPage(driver).open().selectSection("Соусы");
        assertEquals("Соусы", main.activeSection());
        assertTrue(main.isSectionAtTop("Соусы"));
    }

    @Test
    @DisplayName("Переход к начинкам")
    public void shouldSwitchToFillings() {
        MainPage main = new MainPage(driver).open().selectSection("Начинки");
        assertEquals("Начинки", main.activeSection());
        assertTrue(main.isSectionAtTop("Начинки"));
    }
}
