package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ResetPasswordPage extends GeneralPage {
    
    // ========== LOCATORS - Consistent naming ==========
    private static final By TXT_NEW_PASSWORD = By.xpath("//input[@id='newPassword']");
    private static final By TXT_CONFIRM_PASSWORD = By.xpath("//input[@id='confirmPassword']");
    private static final By BTN_RESET_PASSWORD = By.xpath("//input[@value='Reset Password']");
    private static final By LBL_PASSWORD_ERROR = By.xpath("//label[@class='validation-error']");
    private static final By LBL_ERROR_MESSAGE = By.xpath("//p[contains(@class,'message error')]");
    private static final By LBL_SUCCESS_MESSAGE = By.xpath("//p[@class='message success']");
    private static final By LINK_HERE_TO_LOGIN = By.xpath("//p[@class='message success']//a[contains(@href,'Login')]");
    
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
        WebElement btn = getClickableElement(BTN_RESET_PASSWORD);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        btn.click();
        return this;
    }
    
    // ========== VERIFICATION METHODS ==========
    public String waitForErrorMessage() {
        try {
            return getElement(LBL_ERROR_MESSAGE).getText();
        } catch (Exception e) {
            return getElement(LBL_PASSWORD_ERROR).getText();
        }
    }

    public String getGeneralErrorMessage() {
        return getElement(LBL_ERROR_MESSAGE).getText();
    }

    public String getConfirmPasswordErrorMessage() {
        return getElement(LBL_PASSWORD_ERROR).getText();
    }

    public String getSuccessMessage() {
        return getElement(LBL_SUCCESS_MESSAGE).getText();
    }

    public LoginPage clickHereToLogin() {
        WebElement link = getClickableElement(LINK_HERE_TO_LOGIN);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
        link.click();
        return new LoginPage(driver);
    }

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