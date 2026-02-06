package src;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;

import Constant.Constant;
import Railway.GuerrillaMailPage;
import Railway.LoginPage;
import Railway.RegisterPage;

public class CreateAccountTest extends BaseTest {
	@Test
	public void TC07() throws Exception {
		System.out.println("TC07 - User can create account and activate via email");
		System.out.println("============================================================");
		
		// Step 1: Navigate to GuerrillaMail to get temporary email
		System.out.println("\nStep 1: Navigate to GuerrillaMail to get temporary email");
		GuerrillaMailPage mailPage = new GuerrillaMailPage(driver);

		mailPage.open().uncheckScrambleAddress();
		
		// Get the temporary email address
		String tempEmail = mailPage.getEmailAddress();
		Assert.assertNotNull(tempEmail, "Failed to get temporary email from GuerrillaMail");
		Assert.assertTrue(tempEmail.contains("@"), "Invalid email format: " + tempEmail);
		System.out.println("  Temporary email generated: " + tempEmail);
		
		// Save the GuerrillaMail window handle
		String guerrillaMailWindow = mailPage.getCurrentWindowHandle();
		
		// Step 2: Open new tab for Railway and navigate to Register page
		System.out.println("\nStep 2: Navigate to Railway and go to Register page");
		Constant.WEBDRIVER.switchTo().newWindow(WindowType.TAB);
		String railwayWindow = Constant.WEBDRIVER.getWindowHandle();
		
		homePage.open();
		RegisterPage registerPage = homePage.gotoRegisterPage();
		System.out.println("  Navigated to Register page");
		
		// Step 3: Fill registration form with temporary email
		System.out.println("\nStep 3: Fill registration information with temporary email");
		String password = "Password123!";
		String pid = "123456789";
		
		Constant.RegistrationData registrationData = new Constant.RegistrationData(tempEmail, password, password, pid);
		
		System.out.println("Email: " + tempEmail);
		System.out.println("Password: " + password);
		System.out.println("PID: " + pid);
		
		registerPage.register(registrationData);
		System.out.println("  Registration form submitted");
		
		// Step 4: Verify success message about activation email
		System.out.println("\nStep 4: Verify registration success message");
		String actualSuccessMsg = registerPage.getSuccessMessage();
		System.out.println("Success message received: " + actualSuccessMsg);
		
		Assert.assertTrue(
			actualSuccessMsg.contains("Thank you for registering") || 
			actualSuccessMsg.contains("activation") || actualSuccessMsg.contains("email"), 
			"Registration success message is not displayed as expected. Actual: " + actualSuccessMsg
		);
		System.out.println("  Registration successful!");
		
		// Step 5: Switch back to GuerrillaMail tab and wait for activation email from thanhletraining
		System.out.println("\nStep 5: Switch to GuerrillaMail and wait for activation email");
		Constant.WEBDRIVER.switchTo().window(guerrillaMailWindow);
		System.out.println("  Switched to GuerrillaMail tab");
		
		// Ad-blocking
		mailPage.closeAdvertisements();
		
		// Wait for activation email from thanhletraining (30 seconds) and click activation link
		boolean emailReceivedAndLinkClicked = mailPage.waitForActivationEmailAndClick(30);
		Assert.assertTrue(emailReceivedAndLinkClicked, 
			"Failed to receive activation email from thanhletraining or click activation link within 30 seconds");
		System.out.println("  Activation email received and link clicked!");
		
		// Step 6: Verify Registration Confirmed page
		System.out.println("\nStep 6: Verify Registration Confirmed");
		
		RegisterPage confirmPage = new RegisterPage(driver);
		
		// Verify "Registration Confirmed" message is displayed
		boolean isConfirmed = confirmPage.isRegistrationConfirmed();
		Assert.assertTrue(isConfirmed, "Registration Confirmed message is not displayed");
		
		String confirmMessage = confirmPage.getRegistrationConfirmedMessage();
		System.out.println("Confirmation message: " + confirmMessage);
		System.out.println("  Registration confirmed successfully!");
		
		// Step 7: Navigate to Login page and login with newly created account
		System.out.println("\nStep 7: Login with newly created account");
		
		// Navigate to login page
		LoginPage loginPage = homePage.gotoLoginPage();
		
		// Login with new credentials
		Constant.User newUser = new Constant.User(tempEmail, password);
		System.out.println("Logging in with: " + tempEmail);
		loginPage.login(newUser);
		
		// Verify login successful
		String welcomeMsg = homePage.getWelcomeMessage();
		String expectedMsg = "Welcome to Safe Railway";
		
		System.out.println("Welcome message: " + welcomeMsg);
		System.out.println("Expected message: " + expectedMsg);
		
		Assert.assertEquals(welcomeMsg, expectedMsg, 
			"Login with newly created account failed");
		
		System.out.println("  Login successful!");
		
		System.out.println("TC07 - PASSED");
		System.out.println("Successfully created and activated account: " + tempEmail);
	}
}