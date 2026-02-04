package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import Constant.Constant;

public abstract class GeneralPage {
	protected WebDriverWait wait;
	
	public GeneralPage() {
		this.wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(Constant.DEFAULT_TIME));
		
	}
	//Locators
	private final By tabLogin = By.xpath("//div[@id ='menu']//a[@href='/Account/Login.cshtml']");
	private final By tabLogout = By.xpath("//div[@id='menu']//a[@href='/Account/Logout']");
	private final By tabFAQ = By.xpath("//div[@id='menu']//a[contains(@href, 'FAQ')]");
	private final By tabRegister = By.xpath("//div[@id='menu']//a[contains(@href, 'Register')]");
	private final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");
	private final By lblErrorMessage = By.xpath("//p[@class='message error LoginForm']");
		
	//Elements
	protected WebElement getTabLogin() {
		return wait.until(ExpectedConditions.elementToBeClickable(tabLogin));
	}
	protected WebElement getTabLogout() {
		return wait.until(ExpectedConditions.elementToBeClickable(tabLogout));
	}
	protected WebElement getlblWelcomeMessage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(lblWelcomeMessage));
	}
	protected WebElement getlblErrorMessage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorMessage));
	}
	protected WebElement getTabFAQ() {
		return wait.until(ExpectedConditions.elementToBeClickable(tabFAQ));
	}
	protected WebElement getTabRegister() {
		return wait.until(ExpectedConditions.elementToBeClickable(tabRegister));
	}
		
	//Methods
	public String getWelcomeMessage() {
		return this.getlblWelcomeMessage().getText();
	}
	public String getErrorMessage() {
		return this.getlblErrorMessage().getText();
	}
	public LoginPage gotoLoginPage() {
		this.getTabLogin().click();
		return new LoginPage();
	}
	public HomePage clickFAQ() {
		this.getTabFAQ().click();
		return new HomePage();
	}
	public HomePage logout() {
		this.getTabLogout().click();
		return new HomePage();
	}
	
	//Kiểm tra xem người dùng đã login vào chưa
	public boolean isUserLoggedIn() {
		try {
			return getlblWelcomeMessage().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
