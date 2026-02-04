package Railway;

//import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends GeneralPage{
	public LoginPage() {
		super();
	}
	
	//Locators
	private final By _txtUsername = By.xpath("//input[@id='username']");
	private final By _txtPassword = By.xpath("//input[@id='password']");
	private final By _btnLogin = By.xpath("//input[@value='login']");
		
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
		
	//Methods
	public HomePage login(String username, String password) {
		//Submit login credentials
		enterUsername(username);
		enterPassword(password);
		clickLoginButton();
			
		//Land on Home Page
		return new HomePage();	
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
        return new HomePage();
    }
}
