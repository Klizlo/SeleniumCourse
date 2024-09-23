package pollub.onet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SendMailPage {

    private WebDriver driver;

    @FindBy(id = "new-mail-from-item")
    private WebElement sender;

    @FindBy(className = "go2780166340")
    private WebElement recipient;

    @FindBy(className = "go2007921317")
    private WebElement subject;

    @FindBy(id = "newmail-editor")
    private WebElement content;

    @FindBy(css = "button[class='cta go1664305979 go1163868150']")
    private WebElement sendButton;

    public SendMailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void sendMail(String recipient, String subject, String content) {
        this.recipient.sendKeys(recipient);
        this.subject.sendKeys(subject);
        this.content.sendKeys(content);
        this.content.click();
        this.sendButton.click();
    }

    public String getSender() {
        return sender.getText();
    }

}
