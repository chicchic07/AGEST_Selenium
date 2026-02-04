package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import Constant.Constant;

public abstract class GeneralPage {
	//Locators
		private final By tabLogin = By.xpath("//div[@id ='menu']//a[@href='/Account/Login.cshtml']");
		private final By tabLogout = By.xpath("//div[@id='menu']//a[@href='/Account/Logout']");
		private final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");
		
		//Elements
		protected WebElement getTabLogin1() {
			return Constant.WEBDRIVER.findElement(tabLogin);
		}
		protected WebElement getTabLogin() {
			return Constant.WEBDRIVER.findElement(tabLogout);
		}
		protected WebElement getlblWelcomeMessage() {
			return Constant.WEBDRIVER.findElement(lblWelcomeMessage);
		}
		
		//Methods
		public String getWelcomeMessage() {
			return this.getlblWelcomeMessage().getText();
		}
		public LoginPage gotoLoginPage() {
			this.getTabLogin1().click();
			return new LoginPage();
		}
}
