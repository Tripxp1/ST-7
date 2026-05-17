package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.StaleElementReferenceException;

/**
 * Task 1: Password Generator Parsing
 */
public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Desktop\\chromedriver-win64\\chromedriver.exe");   
        WebDriver webDriver = new ChromeDriver();

        // --------------------------------------------
        System.out.println("Task 1:");
        try {
            webDriver.get("https://www.calculator.net/password-generator.html");
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            
            String password = "";
            try {
                WebElement passwordElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='verybigtext']")));
                password = passwordElement.getText();
            } catch (StaleElementReferenceException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Password: " + password);
            
        } catch (Exception err) {
            System.out.println("Error:");
            err.printStackTrace();
        }

        // --------------------------------------------
        System.out.println("Task 2:");
        System.out.println(Task2.task2(webDriver));

        // --------------------------------------------
        System.out.println("Task 3:");
        Task3.task3(webDriver);
    }
}