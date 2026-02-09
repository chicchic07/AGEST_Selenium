package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Constant.Constant;

public class LoginPage extends GeneralPage{
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	//Locators
	private final By _txtUsername = By.xpath("//input[@id='username']");
	private final By _txtPassword = By.xpath("//input[@id='password']");
	private final By _btnLogin = By.xpath("//input[@value='login']");
	private final By _linkForgot = By.xpath("//a[contains(@href, 'Forgot')]");
		
	//Elements
	public WebElement getTxtUsername() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(_txtUsername));
	}
	public WebElement getTxtPassword() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(_txtPassword));
	}
	public WebElement getBtnLogin() {
		return wait.until(ExpectedConditions.elementToBeClickable(_btnLogin));
	}
	public WebElement getBtnForgot() {
		return wait.until(ExpectedConditions.elementToBeClickable(_linkForgot));
	}
		
	//Methods
	public HomePage login(Constant.User user) {
		enterUsername(user.getUsername());
		enterPassword(user.getPassword());
		clickLoginButton();
		return new HomePage(driver);
	}
	
	// Keep the original method for backward compatibility
	public HomePage login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickLoginButton();
		return new HomePage(driver);	
	}
	
	public LoginPage enterUsername(String username) {
		WebElement usernameField = getTxtUsername();
		usernameField.clear();
		usernameField.sendKeys(username);
		return this;
	}
	
	public LoginPage enterPassword(String password) {
		WebElement passwordField = getTxtPassword();
		passwordField.clear();
		passwordField.sendKeys(password);
		return this;
	}
	
	public HomePage clickLoginButton() {
        getBtnLogin().click();
        return new HomePage(driver);
    }
	
	public ForgotPage clickForgotPasswordLink() {
		getBtnForgot().click();
		return new ForgotPage(driver);
	}
}