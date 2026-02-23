package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgotPage extends GeneralPage {
    
    // ========== LOCATORS - Consistent naming ==========
    private static final By TXT_EMAIL = By.xpath("//input[@id='email']");
    private static final By BTN_SEND_INSTRUCTIONS = By.xpath("//input[@value='Send Instructions']");
    private static final By LBL_SUCCESS_MESSAGE = By.xpath("//p[@class='message success']");
    
    public ForgotPage(WebDriver driver) {
        super(driver);
    }
    
    // ========== MAIN ACTIONS ==========
    
    public ForgotPage sendResetInstructions(String email) {
        enterEmail(email);
        clickSendInstructions();
        return this;
    }
    
    // ========== INPUT METHODS ==========
    
    public ForgotPage enterEmail(String email) {
        WebElement field = getElement(TXT_EMAIL);
        field.clear();
        field.sendKeys(email);
        return this;
    }
    
    public ForgotPage clickSendInstructions() {
        WebElement btn = getClickableElement(BTN_SEND_INSTRUCTIONS);
        
        // Scroll into view before clicking
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", btn);
        
        btn.click();
        return this;
    }
    
    // ========== VERIFICATION METHODS ==========
    
    public String getSuccessMessage() {
        return getElement(LBL_SUCCESS_MESSAGE).getText();
    }
}