package pollub.onet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

public class SendMailTest {

    private static WebDriver driver;
    private static String login;
    private static String recipient;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    public static void setUpBeforeClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        ResourceBundle bundle = ResourceBundle.getBundle("mail");
        login = bundle.getString("mail.login");
        String password = bundle.getString("mail.password");
        recipient = bundle.getString("mail.recipient");

        driver.get("https://konto.onet.pl/signin?state=https%3A%2F%2Fpoczta.onet.pl%2F&client_id=poczta.onet.pl.front.onetapi.pl");
        driver.findElement(By.className("cmp-intro_acceptAll"))
                .click();

        logInToAccount(login, password);

    }

    private static void logInToAccount(String login, String password) {
        LoginStepPage loginPage = new LoginStepPage(driver);
        loginPage.enterEmail(login);
        PasswordStepPage passwordPage = new PasswordStepPage(driver);
        passwordPage.enterPassword(password);
        RememberDevicePage rememberDevicePage = new RememberDevicePage(driver);
        rememberDevicePage.doNotAddDeviceToTrustedDevices();
    }

    @BeforeEach
    public void setUp() {
        driver.get("https://poczta.onet.pl/");
    }

    @Test
    public void whenClickSendMail_returnSendMailPage() {
        SidePanelPage sidePanelPage = new SidePanelPage(driver);
        sidePanelPage.clickSendMailButton();
        SendMailPage sendMailPage = new SendMailPage(driver);
        assertThat(sendMailPage.getSender())
                .isEqualTo(login);
    }

    @Test
    public void givenRecipientAndSubjectAndContent_whenSendMail_returnMainPageWithSuccessMessage() {
        SidePanelPage sidePanelPage = new SidePanelPage(driver);
        sidePanelPage.clickSendMailButton();

        SendMailPage sendMailPage = new SendMailPage(driver);
        sendMailPage.sendMail(recipient, "Test", "Test mail");

        MainPage mainPage = new MainPage(driver);
        assertThat(mainPage.getSuccessToastText())
                .isEqualTo("Mail został wysłany");
    }

    @AfterAll
    public static void tearDownAfterClass() {
        driver.quit();
    }

}
