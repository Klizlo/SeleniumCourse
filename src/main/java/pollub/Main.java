package pollub;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        // Disable search engine choice screen
        options.addArguments("--disable-search-engine-choice-screen");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("https://www.google.pl");
        Thread.sleep(1000); // Sleep 1 second
        driver.findElement(By.id("L2AGLb")) // Find button to accept all cookies
                        .click();
        driver.findElement(By.className("gLFyf")) // Find search bar
                .sendKeys("Lublin"); // Type "Lublin" in search bar
        Thread.sleep(1000); // Sleep 1 second
        driver.findElement(By.className("gNO89b")) // Find search button
                .click();
    }
}