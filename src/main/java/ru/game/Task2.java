package ru.game;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {


    public static String fetchIp(WebDriver driver) {
        try {

            driver.get("https://api.ipify.org/?format=json");


            WebElement elem = driver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();


            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(jsonStr);


            return (String) jsonObj.get("ip");

        } catch (Exception e) {
            return "Ошибка в Задании 2: " + e.getMessage();
        }
    }
}