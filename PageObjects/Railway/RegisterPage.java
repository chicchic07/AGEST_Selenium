package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage extends GeneralPage {
    
    // Locators
    private static final By TXT_EMAIL = By.xpath("//input[@id='email']");
    private static final By TXT_PASSWORD = By.xpath("//input[@id='password']");
    private static final By TXT_CONFIRM_PASSWORD = By.xpath("//input[@id='confirmPassword']");
    private static final By TXT_PID = By.xpath("//input[@id='pid']");
    private static final By BTN_REGISTER = By.xpath("//input[@value='Register']");
    private static final By LBL_SUCCESS_MESSAGE = By.xpath("//h1[contains(text(), 'Thank you')]");
    private static final By LBL_REGISTRATION_CONFIRMED = By.xpath("//p[contains(text(), 'Registration Confirmed')]");
    private static final String LBL_ERROR_FIELD_TEMPLATE = "//label[@class='validation-error' and @for='%s']";
    
    public RegisterPage(WebDriver driver) { 
        super(driver); 
    }
    
    // ========== MAIN ACTIONS ==========
    
    public RegisterPage register(Constant.RegistrationData data) {
        enterEmail(data.getEmail());
        enterPassword(data.getPassword());
        enterConfirmPassword(data.getConfirmPassword());
        enterPID(data.getPid());
        clickRegisterButton();
        return this;
    }
    
    public HomePage register(String email, String password, String confirmPassword, String pid) {
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        enterPID(pid);
        clickRegisterButton();
        return new HomePage(driver);
    }
    
    // ========== INPUT METHODS ==========
    
    public RegisterPage enterEmail(String email) {
        fillField(TXT_EMAIL, email);
        return this;
    }
    
    public RegisterPage enterPassword(String password) {
        fillField(TXT_PASSWORD, password);
        return this;
    }
    
    public RegisterPage enterConfirmPassword(String confirmPassword) {
        fillField(TXT_CONFIRM_PASSWORD, confirmPassword);
        return this;
    }
    
    public RegisterPage enterPID(String pid) {
        fillField(TXT_PID, pid);
        return this;
    }
    
    public RegisterPage clickRegisterButton() {
        getClickableElement(BTN_REGISTER).click();
        return this;
    }
    
    // ========== VERIFICATION METHODS ==========
    
    public String getSuccessMessage() {
        return getElement(LBL_SUCCESS_MESSAGE).getText();
    }
    
    public String getRegistrationConfirmedMessage() {
        return getElement(LBL_REGISTRATION_CONFIRMED).getText();
    }
    
    public String getErrorField(String fieldFor) {
        By locator = By.xpath(String.format(LBL_ERROR_FIELD_TEMPLATE, fieldFor));
        return getElement(locator).getText();
    }
    
    public boolean isRegistrationConfirmed() {
        try {
            return getElement(LBL_REGISTRATION_CONFIRMED).isDisplayed();
        } catch (Exception e) {
            System.err.println("Registration Confirmed message not found: " + e.getMessage());
            return false;
        }
    }
    
    // ========== HELPER METHODS ==========
    
    private void fillField(By locator, String value) {
        WebElement field = getElement(locator);
        field.clear();
        field.sendKeys(value);
    }
}