import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import Common.Utilities;
import Constant.Constant;
import Railway.RegisterPage;
//import Railway.HomePage;
//import Railway.LoginPage;

public class CreateAccountTest extends BaseTest{
	@Test
	public void TC07() {
		System.out.println("TC07 - User can't create account with an already in-use email");
		
		System.out.println("Step 1: Navigate to Railway Safe homepage");
		homePage.open();
		
		System.out.println("Step 2: Click on 'Register' tab");
		RegisterPage registerPage = homePage.gotoRegisterPage();
		
		System.out.println("Step 3: Enter an already registered email and other valid registration information");
		registerPage.register(Constant.USERNAME, Constant.PASSWORD, Constant.PASSWORD, "123456789");
		
		System.out.println("Step 4: Click 'Register' button");
		
		System.out.println("Step 5: Verify error message about duplicate email is displayed");
		String actualMsg = registerPage.getErrorMessage();
		String expectedMsg = "This email address is already in use.";
		Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
		
		System.out.println("TC07 - Test implementation pending");
	}

}