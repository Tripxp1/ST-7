package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {

    private static final String IPIFY_ENDPOINT =
        "https://api.ipify.org/?format=json";

    private Task2() {
    }

    public static String getClientIp(WebDriver driver) {
        try {
            driver.get(IPIFY_ENDPOINT);
            JSONObject response = parseJson(getPageText(driver));
            String ip = (String) response.get("ip");
            System.out.println(ip);
            return ip;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot read client IP", e);
        }
    }

    static String getPageText(WebDriver driver) {
        try {
            WebElement pre = driver.findElement(By.tagName("pre"));
            return pre.getText();
        } catch (Exception ignored) {
            return driver.findElement(By.tagName("body")).getText();
        }
    }

    private static JSONObject parseJson(String raw) throws Exception {
        return (JSONObject) new JSONParser().parse(raw);
    }
}
