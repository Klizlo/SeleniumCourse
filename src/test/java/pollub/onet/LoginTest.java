package pollub.onet;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {

    private static WebDriver driver;
    private static String login;
    private static String password;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    public static void setUpBeforeClass(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);

        ResourceBundle bundle = ResourceBundle.getBundle("mail");
        login = bundle.getString("mail.login");
        password = bundle.getString("mail.password");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void setUp(){
        driver.get("https://konto.onet.pl/signin?state=https%3A%2F%2Fpoczta.onet.pl%2F&client_id=poczta.onet.pl.front.onetapi.pl");
        //Accept all cookies
        if (!driver.findElements(By.className("cmp-intro_acceptAll")).isEmpty()) {
            driver.findElement(By.className("cmp-intro_acceptAll"))
                    .click();
        }
    }

    @Test
    void givenLogin_whenLoginIntoAccount_returnPasswordStepPage() {
        LoginStepPage loginPage = new LoginStepPage(driver);
        loginPage.enterEmail(login);
        PasswordStepPage passwordPage = new PasswordStepPage(driver);
        assertThat(passwordPage.getPasswordLabel())
                .isEqualTo("Hasło");

        takeScreenshot();
    }

    private void takeScreenshot() {
        File screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(
                    screenshot,
                    new File("./src/test/resources/screenshots/" + dateFormat.format(new Date()) + ".jpg")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void givenInvalidLogin_whenLoginIntoAccount_returnLoginStepPageWithErrorMessage() {
        LoginStepPage loginPage = new LoginStepPage(driver);
        loginPage.enterEmail("test_test_test@onet.pl");
        assertThat(loginPage.getErrorMessage())
                .isNotEmpty();

        takeScreenshot();
    }

    @Test
    void givenLoginAndPassword_whenLoginIntoAccount_returnRememberDevicePage() {
        LoginStepPage loginPage = new LoginStepPage(driver);
        loginPage.enterEmail(login);
        PasswordStepPage passwordPage = new PasswordStepPage(driver);
        passwordPage.enterPassword(password);
        RememberDevicePage rememberDevicePage = new RememberDevicePage(driver);
        assertThat(rememberDevicePage.getTitle())
                .isEqualTo("Czy chcesz dodać to urządzenie do zaufanych?");

        takeScreenshot();
    }

    @Test
    void givenLoginAndInvalidPassword_whenLoginIntoAccount_returnPasswordStepPageWithErrorMessage() {
        LoginStepPage loginPage = new LoginStepPage(driver);
        loginPage.enterEmail(login);
        PasswordStepPage passwordPage = new PasswordStepPage(driver);
        passwordPage.enterPassword("test");
        assertThat(passwordPage.getErrorMessage())
                .isNotEmpty();

        takeScreenshot();
    }

    @AfterAll
    public static void tearDownAfterClass(){
        driver.quit();
    }

}
