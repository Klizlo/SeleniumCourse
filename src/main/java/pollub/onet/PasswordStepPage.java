package pollub.onet;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PasswordStepPage {

    private WebDriver driver;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(className = "hitDDR")
    private WebElement errorMessage;

    public PasswordStepPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterPassword(String password) {
        this.password.sendKeys(password);
        this.password.sendKeys(Keys.ENTER);
    }

    public String getPasswordLabel() {
        return driver
                .findElement(By.cssSelector("label[for='password']"))
                .getText();
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(d -> !errorMessage.getText().isEmpty());
        return errorMessage.getText();
    }
}
