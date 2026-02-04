import org.testng.Assert;
import org.testng.annotations.Test;

import Constant.Constant;
import Railway.HomePage;
import Railway.LoginPage;

public class LoginTest extends BaseTest {
	
	@Test
	public void TC01() {
		System.out.println("TC01 - User can log into Railway with valid username and password");
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        HomePage homePageAfterLogin = loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        String actualMsg = homePageAfterLogin.getWelcomeMessage();
        
        String expectedMsg = String.format(Constant.WELCOME_MESSAGE_FORMAT, Constant.USERNAME);
        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
		
		
//		System.out.println("TC01 - User can log into Railway with valid username and password");
//		HomePage homePage = new HomePage();
//		homePage.open();
//		LoginPage loginPage = homePage.gotoLoginPage();
//		String actualMsg = loginPage.login(Constant.USERNAME, Constant.PASSWORD).getWelcomeMessage();
//		String expectedMsg = "Welcome" + Constant.USERNAME;
//		Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
	}
	
	@Test
	public void TC02() {
		System.out.println("TC02 - User cannot login with blank 'Username' textbox");
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("", Constant.PASSWORD);
        String actualMsg = loginPage.getErrorMessage();
        
        Assert.assertEquals(actualMsg, Constant.LOGIN_ERROR_MESSAGE, 
            "Error message is not displayed as expected");
		
		
//		System.out.println("TC02 - User cannot login with blank 'Usernamee' textbox");
//		HomePage home = new HomePage();
//		home.open();
//		LoginPage login = home.gotoLoginPage();
//		String actualMsg = login.login("", Constant.PASSWORD).getErrorMessage();
//		String expectedMsg = "There was a problem with your login and/or errors exist in your form.";
//		Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
	}
	
	@Test
	public void TC03() {
		System.out.println("TC03 - User cannot log into Railway with invalid password");
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.INVALID_PASSWORD);
        String actualMsg = loginPage.getErrorMessage();
        
        Assert.assertEquals(actualMsg, Constant.INVALID_CREDENTIALS_MESSAGE, 
            "Error message is not displayed as expected");
		
		
//		System.out.println("User cannot log into Railway with invalid password");
//		HomePage homePage = new HomePage();
//		homePage.open();
//		LoginPage login = homePage.gotoLoginPage();
//		String actualMsg = login.login(Constant.USERNAME, "123456789").getErrorMessage();
//		String expectedMsg = "Invalid username or password. Please try again.";
//		Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
	}
	
	@Test
	public void TC04() {
		System.out.println("TC04 - Login with wrong password many times");
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, "wrongPassword123");
        String firstAttemptMsg = loginPage.getErrorMessage();
        Assert.assertEquals(firstAttemptMsg, Constant.INVALID_CREDENTIALS_MESSAGE, 
            "First attempt error message is not displayed as expected");
      
        for (int i = 2; i <= Constant.MAX_LOGIN_ATTEMPTS; i++) {
            loginPage.login(Constant.USERNAME, "wrongPassword123");
            System.out.println("Login attempt: " + i);
        }
        
        // Assert - Check warning message after 4 failed attempts
        String warningMsg = loginPage.getErrorMessage();
        Assert.assertEquals(warningMsg, Constant.LOGIN_ATTEMPTS_WARNING, 
            "Warning message after 4 failed attempts is not displayed as expected");
		
		
//		System.out.println("TC04 - Login with wrong password many times");
//
//		HomePage homePage = new HomePage();
//		homePage.open();
//		LoginPage loginPage = homePage.gotoLoginPage();
//		String actualMsg = loginPage.login(Constant.USERNAME, "123").getErrorMessage();
//		String expectedMsg = "Invalid username or password. Please try again.";
//		Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
//
//		for (int i = 2; i <= 4; i++) {
//		// login sai
//		loginPage.login(Constant.USERNAME, "123").getErrorMessage();
//		System.out.println("Login attempt: " + i);
//		}
//		
//		String actualMsg2 = loginPage.login(Constant.USERNAME, "123").getErrorMessage();
//		String expectedMsg2 = "4 out of 5 login attempts, Warning message is not displayed";
//		Assert.assertEquals(actualMsg2, expectedMsg2, "Error message is not displayed as expected");
		
//		System.out.println("System shows message when user enters wrong password many times");
//		HomePage home = new HomePage();
//		home.open();
//		LoginPage login = home.gotoLoginPage();
//		
//		for (int i = 1; i <= 3; i++) {
//			login.login(Constant.USERNAME, "123456789");
//			System.out.println("Login attempt" + i + "with wrong password");
//		}
//		
//		String actualMsg = login.login(Constant.USERNAME, "123456789").getErrorMessage();
//		String expectedMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
//		Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
	}
	
	@Test
	public void TC05() {
		System.out.println("TC05 - User can't login with an account that hasn't been activated");
        homePage.open();
        String inactiveEmail = "inactive@gmail.com";
        String inactivePassword = "123456789";
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(inactiveEmail, inactivePassword);
        String actualMsg = loginPage.getErrorMessage();

        Assert.assertEquals(actualMsg, Constant.INVALID_CREDENTIALS_MESSAGE, 
            "Error message is not displayed as expected for inactive account");
		
		
//		System.out.println("TC05 - User can't login with an account hasn't been activated");
//		HomePage homePage = new HomePage();
//		homePage.open();
//		LoginPage loginPage = homePage.gotoLoginPage();
//		String actualMsg = loginPage.login("abc@gmail.com", "123456789").getErrorMessage();
//		String expectedMsg = "Invalid username or password. Please try again.";
//		Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
	}
}
