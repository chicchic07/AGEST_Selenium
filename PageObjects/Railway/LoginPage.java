package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Constant.Constant;

public class LoginPage extends GeneralPage {
    
    // ========== LOCATORS - Consistent naming ==========
    private static final By TXT_USERNAME = By.xpath("//input[@id='username']");
    private static final By TXT_PASSWORD = By.xpath("//input[@id='password']");
    private static final By BTN_LOGIN = By.xpath("//input[@value='login']");
    private static final By LINK_FORGOT_PASSWORD = By.xpath("//a[contains(@href, 'Forgot')]");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    // ========== MAIN ACTIONS ==========
    
    public HomePage login(Constant.User user) {
        return login(user.getUsername(), user.getPassword());
    }
    
    public HomePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new HomePage(driver);
    }
    
    // ========== INPUT METHODS ==========
    
    public LoginPage enterUsername(String username) {
        WebElement field = getElement(TXT_USERNAME);
        field.clear();
        field.sendKeys(username);
        return this;
    }
    
    public LoginPage enterPassword(String password) {
        WebElement field = getElement(TXT_PASSWORD);
        field.clear();
        field.sendKeys(password);
        return this;
    }
    
    public HomePage clickLoginButton() {
        getClickableElement(BTN_LOGIN).click();
        return new HomePage(driver);
    }
    
    // ========== NAVIGATION ==========
    
    public ForgotPage clickForgotPasswordLink() {
        getClickableElement(LINK_FORGOT_PASSWORD).click();
        return new ForgotPage(driver);
    }
}