package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {

    private static final String CHROME_DRIVER_PATH = "C:\\WebDrivers\\chromedriver.exe";
    private static final String CALCULATOR_PAGE =
        "https://www.calculator.net/password-generator.html";

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        WebDriver driver = new ChromeDriver();
        try {
            runTasks(driver);
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        } finally {
            driver.quit();
        }
    }

    private static void runTasks(WebDriver driver) {
        driver.get(CALCULATOR_PAGE);
        String password = readPassword(driver);
        System.out.println("Generated password: " + password);

        String ipAddress = Task2.getClientIp(driver);
        System.out.println("Client IP: " + ipAddress);

        String forecast = Task3.getForecastTable(driver);
        System.out.println(forecast);
        Task3.saveForecastTable(forecast);
    }

    private static String readPassword(WebDriver driver) {
        WebElement field = locatePasswordField(driver);
        String value = field.getAttribute("value");
        if (value == null || value.isEmpty()) {
            value = field.getText();
        }
        return value;
    }

    private static WebElement locatePasswordField(WebDriver driver) {
        try {
            return driver.findElement(By.id("resultid"));
        } catch (Exception ignored) {
            return driver.findElement(By.name("result"));
        }
    }
}
