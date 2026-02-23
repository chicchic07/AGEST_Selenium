package src;

import org.testng.Assert;
import org.testng.annotations.Test;

import Constant.Constant;
import Railway.HomePage;
import Railway.LoginPage;

public class LoginTest extends BaseTest {
    
    @Test
    public void TC01() {
        System.out.println("TC01 - User can log into Railway with valid username and password");
        
        System.out.println("Step 1: Navigate to Railway Safe homepage");
        homePage.open();
        
        System.out.println("Step 2: Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();
        
        System.out.println("Step 3: Enter valid username and password, then click 'Login' button");
        HomePage homePageAfterLogin = loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        
        System.out.println("Step 4: Verify welcome message is displayed");
        String actualMsg = homePageAfterLogin.getWelcomeMessage();
        String expectedMsg = String.format(Constant.WELCOME_MESSAGE_FORMAT, Constant.USERNAME);
        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
    }
    
    @Test
    public void TC02() {
        System.out.println("TC02 - User cannot login with blank 'Username' textbox");
        
        System.out.println("Step 1: Navigate to Railway Safe homepage");
        homePage.open();
        
        System.out.println("Step 2: Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();
        
        System.out.println("Step 3: Login with blank 'Username' textbox");
        loginPage.login("", Constant.PASSWORD);
        
        System.out.println("Step 4: Verify error message displayed.");
        String actualMsg = loginPage.getErrorMessage();
        
        Assert.assertEquals(actualMsg, Constant.LOGIN_ERROR_MESSAGE, "Error message is not displayed as expected");
    }
    
    @Test
    public void TC03() {
        System.out.println("TC03 - User cannot log into Railway with invalid password");
        
        System.out.println("Step 1: Navigate to Railway Safe homepage");
        homePage.open();
        
        System.out.println("Step 2: Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();
        
        System.out.println("Step 3: Login with invalid password");
        loginPage.login(Constant.USERNAME, Constant.INVALID_PASSWORD);
        
        System.out.println("Step 4: Verify error message displayed");
        String actualMsg = loginPage.getErrorMessage(); 
        Assert.assertEquals(actualMsg, Constant.INVALID_CREDENTIALS_MESSAGE, "Error message is not displayed as expected");
    }
    
    @Test
    public void TC04() {
        System.out.println("TC04 - Login with wrong password many times");
        
        System.out.println("Step 1: Navigate to Railway Safe homepage");
        homePage.open();
        
        System.out.println("Step 2: Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();
        
        System.out.println("Step 3: Login with invalid password many times");
        loginPage.login(Constant.USERNAME, "wrongPassword123");
        String firstAttemptMsg = loginPage.getErrorMessage();
        Assert.assertEquals(firstAttemptMsg, Constant.INVALID_CREDENTIALS_MESSAGE, 
            "First attempt error message is not displayed as expected");
      
        for (int i = 2; i <= Constant.MAX_LOGIN_ATTEMPTS; i++) {
            loginPage.login(Constant.USERNAME, "wrongPassword123");
            System.out.println("Login attempt: " + i);
        }
        
        System.out.println("Step 4: Verify warning message after 4 failed attempts displayed");
        String warningMsg = loginPage.getErrorMessage();
        Assert.assertEquals(warningMsg, Constant.LOGIN_ATTEMPTS_WARNING, "Warning message after 4 failed attempts is not displayed as expected");
        
        System.out.println("TC04 - PASSED");
    }
    
    @Test
    public void TC05() {
        System.out.println("TC05 - User can't login with an account that hasn't been activated");
        
        System.out.println("Step 1: Navigate to Railway Safe homepage");
        homePage.open();
        
        System.out.println("Step 2: Click on 'Login' tab");
        LoginPage loginPage = homePage.gotoLoginPage();
        
        System.out.println("Step 3: Login with an account that hasn't been activated");
        String inactiveEmail = "inactive@gmail.com";
        String inactivePassword = "123456789";
        loginPage.login(inactiveEmail, inactivePassword);
        
        System.out.println("Step 4: Verify error message displayed");
        String actualMsg = loginPage.getErrorMessage();
        Assert.assertEquals(actualMsg, Constant.INVALID_CREDENTIALS_MESSAGE, 
            "Error message is not displayed as expected for inactive account");
        
        System.out.println("TC05 - PASSED");
    }
}