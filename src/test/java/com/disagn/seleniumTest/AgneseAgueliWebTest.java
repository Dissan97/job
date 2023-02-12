package com.disagn.seleniumTest;

import org.disagn.cli.io.Printer;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AgneseAgueliWebTest {
    @Test
    void webTest(){

        System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(getClass().getResource("chromedriverWindows.exe")).getPath());
        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/search?q=numero+abitanti+murcia&rlz=1C1JZAP_itIT919IT919&sxsrf=AJOqlzWVfadyP-lERGYPIpzOruMTcn9DFg%3A1676201599146&ei=f87oY_bHCLr87_UP6sW8mAM&ved=0ahUKEwi2g47H8Y_9AhU6_rsIHeoiDzMQ4dUDCA8&uact=5&oq=numero+abitanti+murcia&gs_lcp=Cgxnd3Mtd2l6LXNlcnAQAzIFCCEQoAE6CggAEEcQ1gQQsAM6BwgAEA0QgAQ6CQgAEBYQHhDxBDoGCAAQFhAeOgsIABAIEB4QDRDxBDoFCAAQgAQ6CAgAEBYQHhAPOgoIABCABBBGEPsBSgQIQRgASgQIRhgAUP4tWPVdYN1gaAFwAXgAgAG-AYgBwQuSAQQwLjEwmAEAoAEByAEIwAEB&sclient=gws-wiz-serp");
        WebElement acceptButton = driver.findElement(By.xpath("//*[@id=\"L2AGLb\"]/div"));
        acceptButton.click();
        WebElement inhabitantsMurcia = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/block-component/div/div/div[1]/div/div/div[1]/div[1]/div/div[2]/div/div/div[1]/div"));
        String cityData = inhabitantsMurcia.getText();
        String[] parse = cityData.split(" ");
        String[] number = parse[0].split("\\.");
        int inhabitants = Integer.parseInt(number[0] + number[1]);
        String year = parse[1];

        assertTrue(inhabitants > 400000);


        driver.close();
        driver.quit();
        Printer.print("Inhabitants: " + inhabitants + " year: " + year);

    }

}
