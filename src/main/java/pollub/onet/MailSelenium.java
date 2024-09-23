package pollub.onet;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * A helper class for sending e-mais from onet.pl
 */
public class MailSelenium {

    private final WebDriver driver;

    public MailSelenium(ChromeOptions options) {
        driver = new ChromeDriver(options);
    }

    /**
     * Email login function
     * @param login user login
     * @param password user password
     */
    public void login(String login, String password) {
        driver.get("https://konto.onet.pl/signin?state=https%3A%2F%2Fpoczta.onet.pl%2F&client_id=poczta.onet.pl.front.onetapi.pl");

        // Accept all cookies
        Wait(By.className("cmp-intro_acceptAll"), Duration.ofSeconds(20))
                .click();

        // Find login field and fill it
        driver.findElement(By.id("email"))
                .sendKeys(login);
        driver.findElement(By.className("kKyUAv"))
                .click();

        //Find password field and fill it
        Wait(By.id("password"), Duration.ofSeconds(20))
                .sendKeys(password);
        //Simulate click Enter button
        driver.findElement(By.id("password"))
                .sendKeys(Keys.ENTER);

        //Add device to trusted devices
        Wait(By.className("iyPdDH"), Duration.ofSeconds(20))
                .click();
    }

    /**
     * Function that sends emails x times
     * @param sendTo recipient e-mail
     * @param times how many mails need to be sent
     * @throws IOException
     * @throws InterruptedException
     */
    public void sendMail(String sendTo, int times) throws IOException, InterruptedException {
        for (int i=0; i<times; i++) {
            Thread.sleep(1000);
            if (!driver.findElements(By.cssSelector("button[class='cta go1767018107 go4190478407']")).isEmpty()) {
                Wait(By.cssSelector("button[class='cta go1767018107 go4190478407']"), Duration.ofSeconds(20))
                        .click();
            } else {
                Wait(By.cssSelector("button[class='cta  go2360613202']"), Duration.ofSeconds(20))
                        .click();
            }

            //find field "To"
            Wait(By.className("go2780166340"), Duration.ofSeconds(20))
                    .sendKeys(sendTo);

            //find field "Title"
            driver.findElement(By.className("go2007921317"))
                    .sendKeys("Test title " + i);

            String content = "Test mail " + i;

            //find text area
            driver.findElement(By.id("newmail-editor"))
                    .sendKeys(content);
            driver.findElement(By.id("newmail-editor"))
                    .click();

            File screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("./src/main/resources/screenshots/screenshot.jpg"));

            driver.findElement(By.cssSelector("button[class='cta go1664305979 go1163868150']"))
                    .click();
        }
    }

    /**
     * Function waiting for the page load before finding a specific element
     * @param location condition for finding a page element
     * @param seconds how many seconds the program must wait for page to load
     * @return WebElement
     */
    private WebElement Wait(By location, Duration seconds) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, seconds);
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(location));
    }

}
