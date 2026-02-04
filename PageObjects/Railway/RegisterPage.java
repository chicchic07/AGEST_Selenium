package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends GeneralPage {
	public RegisterPage() { super(); }
	
	//Locators
	private final By _txtUsername = By.xpath("//input[@id='username']");
	private final By _txtPassword = By.xpath("//input[@id='password']");
	private final By _txtConfirmPass = By.xpath("//input[@id='confirmPassword]");
	private final By _txtPID = By.xpath("//input[@id='pid']");
	private final By _btnRegister = By.xpath("//input[@value='register']");
	
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
		return Constant.WEBDRIVER.findElement(_txtPID);
	}
	public WebElement getBtnRegister() {
		return wait.until(ExpectedConditions.elementToBeClickable(_btnRegister));
	}
	
	//Methods
	public HomePage register(String username, String password, String confirmPassword, String pid) {
        enterUsername(username);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        enterPID(pid);
        clickRegisterButton();
        
        return new HomePage();
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
    
    public HomePage clickRegisterButton() {
        getBtnRegister().click();
        return new HomePage();
    }
}
	
	
