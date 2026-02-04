

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.Utilities;
import Constant.Constant;
import Railway.HomePage;
import Railway.LoginPage;

public class LoginTest {
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("Pre-Condition");
		Constant.WEBDRIVER = new ChromeDriver();
		Constant.WEBDRIVER.manage().window().maximize();
	}
	
	@AfterMethod
	public void afterMethod() {
		System.out.println("Post-Condition");
		Constant.WEBDRIVER.quit();
	}
	
	@Test
	public void TC01() {
		System.out.println("TC01 - User can log into Railway with valid username and password");
		HomePage homePage = new HomePage();
		homePage.open();
		LoginPage loginPage = homePage.gotoLoginPage();
		String actualMsg = loginPage.login(Constant.USERNAME, Constant.PASSWORD).getWelcomeMessage();
		String expectedMsg = "Welcome" + Constant.USERNAME;
		Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
	}
}
