package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Duration;

public class Task3 {
    public static void task3(WebDriver wd) {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
        String filePath = "result/forecast.txt";

        try {
            wd.get(url);
            WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(10));

            WebElement preElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("pre")));
            String jsonText = preElement.getText();

            //Parse
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonText);

            JSONObject hourly = (JSONObject) jsonObject.get("hourly");
            JSONArray time = (JSONArray) hourly.get("time");
            JSONArray temperature2m = (JSONArray) hourly.get("temperature_2m");
            JSONArray rain = (JSONArray) hourly.get("rain");

            //Write
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                writer.println("+----+------------------+------------------+-------------+");
                writer.println("| №  | Дата/время       | Температура (°C) | Осадки (мм) |");
                writer.println("+----+------------------+------------------+-------------+");

                for (int i = 0; i < time.size(); i++) {
                    String dateTime = (String) time.get(i);
                    String temp = String.valueOf(temperature2m.get(i));
                    String rainValue = String.valueOf(rain.get(i));

                    writer.printf("| %-2d | %-16s | %-16s | %-11s |\n",
                            (i + 1), dateTime, temp, rainValue);
                }

                writer.println("+----+------------------+------------------+-------------+");
            }
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            wd.quit();
            System.out.println("Task3 succeeded");
        }
    }
}