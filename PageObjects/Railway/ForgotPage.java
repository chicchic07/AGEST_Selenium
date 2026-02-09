package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ForgotPage extends GeneralPage{
	public ForgotPage(WebDriver driver) {
		super(driver);
	}

	// Locators
	private final By _linkForgot = By.xpath("//a[contains(@href, 'Forgot')]");
		
	// Elements
	public WebElement getBtnForgot() {
		return wait.until(ExpectedConditions.elementToBeClickable(_linkForgot));
	}
	
	// Methods
	
}
