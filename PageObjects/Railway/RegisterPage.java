package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends GeneralPage {
	public RegisterPage(WebDriver driver) { 
		super(driver); 
	}
	
	//Locators
	private final By _txtUsername = By.xpath("//input[@id='email']");
	private final By _txtPassword = By.xpath("//input[@id='password']");
	private final By _txtConfirmPass = By.xpath("//input[@id='confirmPassword']");
	private final By _txtPID = By.xpath("//input[@id='pid']");
	private final By _btnRegister = By.xpath("//input[@value='Register']");
	private final By _lblSuccessMessage = By.xpath("//p[contains(text(), 'activate your account')]");
	private final By lblRegistrationConfirmed = By.xpath("//p[contains(text(), 'Registration Confirmed')]");
	
	//Elements
	public WebElement getTxtUsername() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(_txtUsername));
	}
	public WebElement getTxtPassword() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(_txtPassword));
	}
	public WebElement getTxtConfirmPass() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(_txtConfirmPass));
	}
	public WebElement getTxtPID() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(_txtPID));
	}
	public WebElement getBtnRegister() {
		return wait.until(ExpectedConditions.elementToBeClickable(_btnRegister));
	}
	public WebElement getLblSuccessMessage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(_lblSuccessMessage));
	}
	public WebElement getLblRegistrationConfirmed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(lblRegistrationConfirmed));
	}
	
	//Methods
	// Register with RegistrationData object
	public RegisterPage register(Constant.RegistrationData data) {
		enterUsername(data.getEmail());
		enterPassword(data.getPassword());
		enterConfirmPassword(data.getConfirmPassword());
		enterPID(data.getPid());
		clickRegisterButton();
		return this;
	}
	
	// Keep old method for backward compatibility
	public HomePage register(String username, String password, String confirmPassword, String pid) {
        enterUsername(username);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        enterPID(pid);
        clickRegisterButton();
        
        return new HomePage(driver);
    }
	
	public RegisterPage enterUsername(String username) {
        WebElement usernameField = getTxtUsername();
        usernameField.clear();
        usernameField.sendKeys(username);
        return this;
    }
    
    public RegisterPage enterPassword(String password) {
        WebElement passwordField = getTxtPassword();
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }
    
    public RegisterPage enterConfirmPassword(String confirmPassword) {
        WebElement confirmPassField = getTxtConfirmPass();
        confirmPassField.clear();
        confirmPassField.sendKeys(confirmPassword);
        return this;
    }
    
    public RegisterPage enterPID(String pid) {
        WebElement pidField = getTxtPID();
        pidField.clear();
        pidField.sendKeys(pid);
        return this;
    }
    
    public RegisterPage clickRegisterButton() {
        getBtnRegister().click();
        return this;
    }
    
    public String getSuccessMessage() {
    	return getLblSuccessMessage().getText();
    }
    
    public String getRegistrationConfirmedMessage() {
		return getLblRegistrationConfirmed().getText();
	}
	
	public boolean isRegistrationConfirmed() {
		try {
			return getLblRegistrationConfirmed().isDisplayed();
		} catch (Exception e) {
			System.err.println("Registration Confirmed message not found: " + e.getMessage());
			return false;
		}
	}
}