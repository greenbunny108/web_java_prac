package ru.cs.msu.web_java_prac;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {

    private final String rootTitle = "Главная страница";
    private final String peopleTitle = "Люди";
    private final String addressTitle = "Места";
    private final String addressInfo = "Информация о месте";

    @Test
    void MainPage() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        assertEquals(rootTitle, driver.getTitle());
        driver.quit();
    }

    @Test
    void HeaderTest() {
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(2048,1512));
        driver.get("http://localhost:8080/");

        WebElement peopleButton = driver.findElement(By.id("peopleListLink"));
        peopleButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(peopleTitle, driver.getTitle());

        WebElement rootButton = driver.findElement(By.id("rootLink"));
        rootButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(rootTitle, driver.getTitle());

        WebElement placesButton = driver.findElement(By.id("addrsListLink"));
        placesButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(addressTitle, driver.getTitle());

        rootButton = driver.findElement(By.id("rootLink"));
        rootButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(rootTitle, driver.getTitle());

        driver.quit();
    }

    @Test
    void testAddress() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/addresses");
        assertEquals(addressTitle, driver.getTitle());
        WebElement addPlace = driver.findElement(By.id("addAddrButton"));
        addPlace.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        String editPlaceTitle = "Редактировать место";
        assertEquals(editPlaceTitle, driver.getTitle());


        driver.findElement(By.id("street_address")).sendKeys("Тестовое место");
        driver.findElement(By.id("city")).sendKeys("Тестовый город");
        driver.findElement(By.id("state_province")).sendKeys("Тестовая область");
        driver.findElement(By.id("country")).sendKeys("Тестовая страна");

        driver.findElement(By.id("submitButton")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(addressInfo, driver.getTitle());
        WebElement placeInfo = driver.findElement(By.id("addressInfo"));
        List<WebElement> cells = placeInfo.findElements(By.tagName("p"));
        assertEquals(cells.get(0).getText(), "Расположено в: Тестовый город, Тестовая область, Тестовая страна");

        WebElement deleteButton = driver.findElement(By.id("deleteButton"));
        deleteButton.click();

        driver.quit();
    }

    @Test
    void getPersonInfo() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/person?personId=7");
        String personTitle = "Информация о человеке";
        assertEquals(personTitle, driver.getTitle());

        WebElement personInfo = driver.findElement(By.id("personInfo"));
        List<WebElement> cells = personInfo.findElements(By.tagName("p"));

        assertEquals(cells.get(0).getText(), "Имя: Александр III");
        assertEquals(cells.get(1).getText(), "Пол: male");
        assertEquals(cells.get(2).getText(), "Дата рождения: 1845-02-26T00:00");
        assertEquals(cells.get(3).getText(), "Дата смерти: 1894-11-01T00:00");
        assertEquals(cells.get(4).getText(), "Краткая характеристика: Император Всероссийский, проводил консервативную политику." +
                " Главная его заслуга в том, что он не развязал ни одной войны при своём правлении, за что его прозвали Миротворец");

        driver.quit();
    }
}
