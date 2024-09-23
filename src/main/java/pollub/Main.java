package pollub;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pollub.onet.MailSelenium;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        ChromeOptions options = new ChromeOptions();
        // Disable search engine choice screen
        options.addArguments("--disable-search-engine-choice-screen");
        // Exercise 1
        // basicTask(options, "Lublin");

        //Exercise 2
//        try {
//            mailOperations(options);
//        }catch (IOException e) {
//            System.err.println("File not found");
//        }
    }

    /**
     * Exercise 1
     * Find something in Google search engine
     * @param options web options
     * @throws InterruptedException
     */
    private static void basicTask(ChromeOptions options, String subject) throws InterruptedException {
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.google.pl");
        Thread.sleep(1000); // Sleep 1 second
        driver.findElement(By.id("L2AGLb")) // Find button to accept all cookies
                .click();
        driver.findElement(By.className("gLFyf")) // Find search bar
                .sendKeys(subject); // Type subject in search bar
        Thread.sleep(1000); // Sleep 1 second
        driver.findElement(By.className("gNO89b")) // Find search button
                .click();
    }

    /**
     * Exercise 2
     * Login to mail and send x mails
     * @param options web options
     * @throws IOException
     */
    private static void mailOperations(ChromeOptions options) throws IOException, InterruptedException {
        MailSelenium mail = new MailSelenium(options);
        //Login and password are in mail.properties file
        ResourceBundle bundle = ResourceBundle.getBundle("mail");
        //Get login from properties file
        String login = bundle.getString("mail.login");
        //Get password from properties file
        String password = bundle.getString("mail.password");
        //Login to mail
        mail.login(login, password);

        String sendTo = bundle.getString("mail.sendTo");
        //send mail
        mail.sendMail(sendTo, 5);
    }
}