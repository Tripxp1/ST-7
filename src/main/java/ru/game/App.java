package ru.game;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

public class App {

    public static void main(String[] args) {
        WebDriver driver = initDriver();

        try {
            System.out.println("=== Запуск автоматизации ===\n");

            // --- ЗАДАНИЕ №1: Пароль ---
            System.out.println("[Задание 1] Получение пароля...");
            String password = fetchPassword(driver);
            System.out.println("Результат: " + password + "\n");

            // --- ЗАДАНИЕ №2: IP (вызов другого класса) ---
            System.out.println("[Задание 2] Получение IP-адреса...");
            String ip = Task2.fetchIp(driver);
            System.out.println("Результат: " + ip + "\n");

            // --- ЗАДАНИЕ №3: Погода ---
            System.out.println("[Задание 3] Получение прогноза погоды...");
            String weatherReport = Task3.fetchWeather(driver);
            System.out.println("Прогноз успешно получен.\n");
            System.out.println(weatherReport);

            saveReport(weatherReport);

        } catch (Exception e) {
            System.err.println("Критическая ошибка в главном цикле: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("=== Работа завершена, браузер закрыт ===");
            }
        }
    }

    private static WebDriver initDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\danak\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Работа без окна
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        return new ChromeDriver(options);
    }


    private static String fetchPassword(WebDriver driver) {
        try {
            driver.get("https://www.calculator.net/password-generator.html");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement passElem = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".verybigtext > b"))
            );
            return passElem.getText();
        } catch (Exception e) {
            return "Ошибка при получении пароля: " + e.getMessage();
        }
    }


    private static void saveReport(String content) {
        File dir = new File("result");
        if (!dir.exists()) dir.mkdir();

        try (FileWriter writer = new FileWriter("result/forecast.txt")) {
            writer.write(content);
            System.out.println("Отчет сохранен в result/forecast.txt");
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}