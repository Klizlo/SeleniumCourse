package pollub.onet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SidePanelPage {

    private WebDriver driver;

    @FindBy(css = "button[class='cta  go2360613202']")
    private WebElement sendMailButton;

    public SidePanelPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickSendMailButton() {
        sendMailButton.click();
    }

}
