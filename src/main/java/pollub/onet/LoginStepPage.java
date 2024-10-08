package pollub.onet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginStepPage {

    private WebDriver driver;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(className = "fqXtUw")
    private WebElement errorMessage;

    @FindBy(className = "kKyUAv")
    private WebElement nextButton;

    public LoginStepPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String email) {
        this.email.sendKeys(email);
        this.nextButton.click();
    }

    public String getEmailLabel() {
        return driver
                .findElement(By.cssSelector("label[for='email']"))
                .getText();
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(d -> !errorMessage.getText().isEmpty());
        return errorMessage
                .getText();
    }

}
