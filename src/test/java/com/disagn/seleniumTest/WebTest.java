package com.disagn.seleniumTest;


import org.disagn.cli.io.Output;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebTest {
    //Selenium test Dissan Uddin Ahmed
    @Test
    void webTest(){
        System.out.println(Objects.requireNonNull(getClass().getResource("chromedriver")).getPath());
        System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(getClass().getResource("chromedriver")).getPath());
        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();
        // Navigate to the Xe currency website for Dollar converter
        driver.get("https://www.xe.com/it/currencyconverter/convert/?Amount=1&From=EUR&To=USD");
        // Get the Xpath elem for the searched value of USD
        WebElement dollarValue = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/section/div[2]/div/main/div/div[2]/div[1]/p[2]"));
        assertNotNull(dollarValue);
        float dollar = getValue(dollarValue);
        driver.quit();
        //closing the window
        driver = new ChromeDriver();
        // Navigate to the Xe currency website for Pound converter
        driver.get("https://www.xe.com/it/currencyconverter/convert/?Amount=1&From=EUR&To=GBP");
        // Get the Xpath elem for the searched value of GBD
        WebElement poundValue = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/section/div[2]/div/main/div/div[2]/div[1]/p[2]"));
        assertNotNull(poundValue);
        float pound = getValue(poundValue);
        driver.quit();
        // Close the browser window
        // Print the contents of the section in the console
        Output.println("Change 1 EUR = " + dollar + " USD");
        Output.println("Change 1 EUR = " + pound + " GBP");
    }


    private float getValue(WebElement element){
        String webText = element.getText();
        String[] parseDot = webText.split(",");
        String[] parseValue = parseDot[1].split(" ");
        String textToAnalyze = parseDot[0] + "." + parseValue[0];
        return Float.parseFloat(textToAnalyze);
    }
}
