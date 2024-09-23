package pollub.onet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RememberDevicePage {

    private WebDriver driver;

    @FindBy(className = "gGirdi")
    private WebElement title;

    @FindBy(className = "hwOWuW")
    private WebElement rememberButton;

    @FindBy(className = "iyPdDH")
    private WebElement doNotRememberButton;

    @FindBy(className = "bwJnzL")
    private WebElement skipButton;

    public RememberDevicePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addDeviceToTrustedDevices() {
        rememberButton.click();
    }

    public void doNotAddDeviceToTrustedDevices() {
        doNotRememberButton.click();
    }

    public void skip() {
        skipButton.click();
    }

    public String getTitle() {
        return title.getText();
    }

}
