package com.disagn.seleniumTest;


import org.disagn.cli.io.Printer;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WebTest {
    //Selenium test Dissan Uddin Ahmed
    @Test
    void webTest() {
        System.out.println(Objects.requireNonNull(getClass().getResource("chromedriverWindows.exe")).getPath());
        System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(getClass().getResource("chromedriverWindows.exe")).getPath());
        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();
        // Navigate to the Xe currency website for Dollar converter
        driver.get("https://www.google.com/search?q=amd+stocks&sxsrf=AJOqlzW8IlwAA8WWPKt-_oWbdjpEUbciAQ%3A1676198651112&ei=-8LoY8nFBoq1sAeFpZaoAw&ved=0ahUKEwiJr7DJ5o_9AhWKGuwKHYWSBTUQ4dUDCA8&uact=5&oq=amd+stocks&gs_lcp=Cgxnd3Mtd2l6LXNlcnAQAzIFCAAQgAQyCggAEIAEEBQQhwIyCAgAEIAEEMsBMggIABCABBDLATIICAAQgAQQywEyCggAEIAEEAoQywEyCAgAEIAEEMsBMggIABCABBDLAToHCCMQsAMQJzoKCAAQRxDWBBCwAzoHCAAQsAMQQzoNCAAQ5AIQ1gQQsAMYAToSCC4QxwEQ0QMQyAMQsAMQQxgCOhUILhDHARDRAxDUAhDIAxCwAxBDGAI6BAgjECc6DQgAEIAEEBQQhwIQsQM6BwgAELEDEEM6BAgAEEM6CAgAEIAEELEDOgoIABCxAxCDARBDSgQIQRgASgQIRhgBUIAGWMYaYMsbaAFwAXgAgAFgiAGSBJIBATeYAQCgAQHIARPAAQHaAQYIARABGAnaAQYIAhABGAg&sclient=gws-wiz-serp");

        WebElement accept = driver.findElement(By.xpath("//*[@id=\"L2AGLb\"]/div"));
        accept.click();
        assertNotNull(accept);
        // Get the Xpath elem for the searched value of USD
        WebElement amd = driver.findElement(By.xpath("//*[@id=\"knowledge-finance-wholepage__entity-summary\"]/div[3]/g-card-section/div/g-card-section/div[2]/div[1]/span[1]/span/span[1]"));
        assertNotNull(amd);
        double amdValue = Double.parseDouble(parseText(amd.getText()));
        driver.close();
        driver.quit();


        driver = new ChromeDriver();

        driver.get("https://www.google.com/search?q=intel+stocks&ei=M8XoY5XSOM6Qxc8Po6CawAo&oq=intel+S&gs_lcp=Cgxnd3Mtd2l6LXNlcnAQAxgAMgkIABBDEEYQ-gEyCwgAEIAEELEDEIMBMgUIABCABDILCAAQgAQQsQMQgwEyCwgAEIAEELEDEIMBMgUIABCABDIECAAQQzIECAAQQzIFCAAQgAQyBQgAEIAEOgoIABBHENYEELADOgcIABCwAxBDOgoILhDHARDRAxBDOhEILhCABBCxAxCDARDHARDRAzoOCC4QsQMQgwEQxwEQ0QM6CwguEIAEEMcBENEDOgoIABCxAxCDARBDOhEILhCDARDHARCxAxDRAxCABDoLCC4QgwEQsQMQgAQ6CAgAEIAEELEDOg0ILhCxAxDHARDRAxBDOggIABCxAxCDAUoECEEYAEoECEYYAFDtCFjTEWC6HGgBcAF4AIABY4gBrASSAQE3mAEAoAEByAEKwAEB&sclient=gws-wiz-serp");
        accept = driver.findElement(By.xpath("//*[@id=\"L2AGLb\"]/div"));
        accept.click();
        WebElement intel = driver.findElement(By.xpath("//*[@id=\"knowledge-finance-wholepage__entity-summary\"]/div[3]/g-card-section/div/g-card-section/div[2]/div[1]/span[1]/span/span[1]"));
        assertNotNull(intel);
        double intelValue = Double.parseDouble(parseText(intel.getText()));
        driver.close();
        driver.quit();

        assertTrue(amdValue > intelValue);

        Printer.print("AMD: " + amdValue + " EUR > INTEL: "+ intelValue + " EUR");
    }

    private String parseText(String text) {
        String[] parser = text.split(",");
        return parser[0] + "." + parser[1];

    }
}
