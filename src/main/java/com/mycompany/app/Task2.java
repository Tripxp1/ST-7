package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Task2 {
    public static String task2(WebDriver wd) {
        String ip = "";

        try {
            wd.get("https://api.ipify.org/?format=json");
            WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(10));

            WebElement preElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("pre")));
            String jsonText = preElement.getText();

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonText);
            ip = (String) jsonObject.get("ip");


        } catch (Exception err) {
            err.printStackTrace();
        }

        return  "IP: " + ip;
    }
}