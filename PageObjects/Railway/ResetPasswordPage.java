package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ResetPasswordPage extends GeneralPage {
    
    // Locators
    private static final By TXT_NEW_PASSWORD = By.xpath("//input[@id='newPassword']");
    private static final By TXT_CONFIRM_PASSWORD = By.xpath("//input[@id='confirmPassword']");
    private static final By BTN_RESET_PASSWORD = By.xpath("//input[@value='Reset Password']");
    private static final By LBL_PASSWORD_ERROR = By.xpath("//label[@class='validation-error' and @for='newPassword']");
    
    public ResetPasswordPage(WebDriver driver) {
        super(driver);
    }
    
    // ========== MAIN ACTIONS ==========
    
    public ResetPasswordPage resetPassword(String newPassword, String confirmPassword) {
        enterNewPassword(newPassword);
        enterConfirmPassword(confirmPassword);
        clickResetPasswordButton();
        return this;
    }
    
    // ========== INPUT METHODS ==========
    
    public ResetPasswordPage enterNewPassword(String password) {
        fillField(TXT_NEW_PASSWORD, password);
        return this;
    }
    
    public ResetPasswordPage enterConfirmPassword(String password) {
        fillField(TXT_CONFIRM_PASSWORD, password);
        return this;
    }
    
    public ResetPasswordPage clickResetPasswordButton() {
        getClickableElement(BTN_RESET_PASSWORD).click();
        return this;
    }
    
    // ========== VERIFICATION METHODS ==========
    
    public String getPasswordErrorMessage() {
        try {
            return getElement(LBL_PASSWORD_ERROR).getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            // Uses inherited getErrorMessage() from GeneralPage
            getErrorMessage();
            return true;
        } catch (Exception e) {
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