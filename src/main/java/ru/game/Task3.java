package ru.game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.Locale;

public class Task3 {

    public static String fetchWeather(WebDriver driver) {
        StringBuilder table = new StringBuilder();
        String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";

        try {
            driver.get(url);
            String jsonStr = driver.findElement(By.tagName("pre")).getText();

            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(jsonStr);
            JSONObject hourly = (JSONObject) root.get("hourly");

            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");


            table.append(String.format(Locale.US, "| %-3s | %-13s | %-11s | %-12s |\n", "№", "Дата/время", "Температура", "Осадки (мм)"));
            table.append("|-----|---------------|-------------|--------------|\n");

            for (int i = 0; i < times.size(); i++) {
                String rawTime = (String) times.get(i);
                String formattedTime = rawTime.replace("T", " ").substring(5); 

                double temp = ((Number) temps.get(i)).doubleValue();
                double rain = ((Number) rains.get(i)).doubleValue();


                table.append(String.format(Locale.US, "| %-3d | %-13s | %-11.1f | %-12.2f |\n",
                        (i + 1), formattedTime, temp, rain));
            }

        } catch (Exception e) {
            return "Ошибка при формировании таблицы: " + e.getMessage();
        }
        return table.toString();
    }
}