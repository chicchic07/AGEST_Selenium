import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import Common.Utilities;
import Constant.Constant;
import Railway.FAQPage;
import Railway.HomePage;
import Railway.LoginPage;

public class LogoutTest {
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
	public void TC06() {
		System.out.println("TC06 - User is redirected to Home page after logging out.");
		
		HomePage homePage = new HomePage();
		homePage.open();
		
		LoginPage loginPage = homePage.gotoLoginPage();
		loginPage.login(Constant.USERNAME, Constant.PASSWORD);
		
		homePage.clickFAQ();
		
		homePage.logout();
		
		String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
		String expectedUrl = Constant.RAILWAY_URL;
		Assert.assertEquals(currentUrl, expectedUrl, "User is not redirected to Home page after logout");
	}
}