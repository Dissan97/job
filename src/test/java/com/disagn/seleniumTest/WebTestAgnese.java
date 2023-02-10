package com.disagn.seleniumTest;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;

class WebTestAgnese {
    @Test
    void webTest(){
        System.out.println(Objects.requireNonNull(getClass().getResource("chromedriverWindows")).getPath());
        System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(getClass().getResource("chromedriverWindows")).getPath());
        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();
    }
}
