package com.mycompany.app;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;

public class Task3 {

    private static final String METEO_API =
        "https://api.open-meteo.com/v1/forecast"
        + "?latitude=56&longitude=44"
        + "&hourly=temperature_2m,rain"
        + "&current=cloud_cover"
        + "&timezone=Europe%2FMoscow"
        + "&forecast_days=1"
        + "&wind_speed_unit=ms";

    private static final Path OUTPUT_FILE = Paths.get("result", "forecast.txt");

    private static final String TABLE_HEADER =
        "\u2116    Date/time    Temperature    Rain (mm)";

    private Task3() {
    }

    public static String getForecastTable(WebDriver driver) {
        try {
            driver.get(METEO_API);
            JSONObject root = parseJson(Task2.getPageText(driver));
            JSONObject hourly = (JSONObject) root.get("hourly");

            JSONArray timestamps = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            return buildTable(timestamps, temps, rains);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot read weather forecast", e);
        }
    }

    public static void saveForecastTable(String table) {
        try {
            Files.createDirectories(OUTPUT_FILE.getParent());
            Files.write(OUTPUT_FILE, table.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException("Cannot save weather forecast", e);
        }
    }

    private static JSONObject parseJson(String raw) throws Exception {
        return (JSONObject) new JSONParser().parse(raw);
    }

    private static String buildTable(JSONArray times, JSONArray temps, JSONArray rains) {
        String lineSep = System.lineSeparator();
        StringBuilder sb = new StringBuilder(TABLE_HEADER).append(lineSep);

        for (int hour = 0; hour < times.size(); hour++) {
            sb.append(String.format(
                "%d    %s    %s    %s%s",
                hour + 1,
                times.get(hour),
                temps.get(hour),
                rains.get(hour),
                lineSep
            ));
        }

        return sb.toString();
    }
}
