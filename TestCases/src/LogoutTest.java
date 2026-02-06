package src;
import org.testng.Assert;
import org.testng.annotations.Test;

//import Common.Utilities;
import Constant.Constant;
import Railway.HomePage;
import Railway.LoginPage;

public class LogoutTest extends BaseTest{
	@Test
	public void TC06() {
		System.out.println("TC06 - User is redirected to Home page after logging out.");
		
		System.out.println("Step 1: Navigate to Railway Safe homepage");
		homePage.open();
		
		System.out.println("Step 2: Login with valid credentials");
		LoginPage loginPage = homePage.gotoLoginPage();
		loginPage.login(Constant.USERNAME, Constant.PASSWORD);
		
		System.out.println("Step 3: Click on 'FAQ' tab");
		homePage.clickFAQ();
		
		System.out.println("Step 4: Click on 'Logout' tab");
		homePage.logout();
		
		System.out.println("Step 5: Verify user is on HomePage after logout");
		String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
		String expectedUrl = Constant.RAILWAY_URL;
		Assert.assertEquals(currentUrl, expectedUrl, "User is not redirected to Home page after logout");
		
		System.out.println("TC06 - PASSED");
	}
}