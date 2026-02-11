package src;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;

import Common.TestDataManager;
import Constant.Constant;
import Railway.GuerrillaMailPage;
import Railway.RegisterPage;

public class CreateAccountTest extends BaseTest {
	
	@Test
	public void TC07() {
		System.out.println("TC07 - User can't create account with an already in-use email");
		
		// Step 1 : Navigate to QA Railway Website
		System.out.println("\nStep 1 : Navigate to QA Railway Website");
		homePage.open();
				
		// Step 2 : Click on "Register" tab
		System.out.println("\nStep 2 : Click on 'Register' tab");
		RegisterPage registerPage = homePage.gotoRegisterPage();
				
		// Step 3 : Enter valid information into all fields
		System.out.println("\nStep 3: Enter valid information into all fields");
		Constant.RegistrationData registrationData = new Constant.RegistrationData(Constant.USERNAME, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);
				
		System.out.println("Email: " + Constant.USERNAME);
		System.out.println("Password:" + Constant.PASSWORD);
		System.out.println("PID: " + Constant.PID);
				
		// Step 4 : Click on "Register" button
		System.out.println("Click on 'Register' button");
		registerPage.register(registrationData);
				
			// Verify "This email address is already in use." is shown
		String actualErrorMsg = registerPage.getErrorMessage();
		System.out.println("Error message received: " + actualErrorMsg);
				
		Assert.assertTrue(
			actualErrorMsg.contains("This email address is already in use."));
		System.out.println("  Registration failed!");
	}
	
	
	@Test 
	public void TC08() {
		System.out.println("TC08 - User can't create account while password and PID fields are empty");
		
		// Step 1 : Navigate to QA Railway Website
		System.out.println("\nStep 1 : Navigate to QA Railway Website");
		homePage.open();
				
		// Step 2 : Click on "Register" tab
		System.out.println("\nStep 2 : Click on 'Register' tab");
		RegisterPage registerPage = homePage.gotoRegisterPage();
				
		// Step 3 : Enter valid email address and leave other fields empty
		System.out.println("\nStep 3: Enter valid information into all fields");
		Constant.RegistrationData registrationData = new Constant.RegistrationData(Constant.USERNAME, "", Constant.PASSWORD, "");
				
		System.out.println("Email: " + Constant.USERNAME);
		System.out.println("Password:" + "");
		System.out.println("PID: " + "");
				
		// Step 4 : Click on "Register" button
		System.out.println("Click on 'Register' button");
		registerPage.register(registrationData);
				
			// Verify message "There're errors in the form. Please correct the errors and try again." appears above the form.
		String actualErrorMsg = registerPage.getErrorMessage();
		System.out.println("Error message received: " + actualErrorMsg);
				
		Assert.assertTrue(
			actualErrorMsg.contains("There're errors in the form. Please correct the errors and try again."));
			
			// Next to password fields, verify error message "Invalid password length." displays
		Assert.assertEquals(
			    registerPage.getErrorField("password"), "Invalid password length");

			// Next to PID field, verify error message "Invalid ID length." displays
		Assert.assertEquals(
			    registerPage.getErrorField("pid"), "Invalid ID length");
		System.out.println("  Registration failed!");
	}
	
	
	@Test(priority = 1)
	public void TC09() throws Exception {
		System.out.println("TC09 - User create and activate account");
		System.out.println("============================================================");
		
		// Pre-Cond step 1: Navigate to GuerrillaMail to get temporary email
		GuerrillaMailPage mailPage = new GuerrillaMailPage(driver);

		mailPage.open().uncheckScrambleAddress();
		
		// Get the temporary email address
		String tempEmail = mailPage.getEmailAddress();
		Assert.assertNotNull(tempEmail, "Failed to get temporary email from GuerrillaMail");
		Assert.assertTrue(tempEmail.contains("@"), "Invalid email format: " + tempEmail);
		System.out.println("  Temporary email generated: " + tempEmail);
		
		// Store data for TC10
		TestDataManager.setTC09Data(tempEmail, Constant.PASSWORD);
		
			// Save the GuerrillaMail window handle
		String guerrillaMailWindow = mailPage.getCurrentWindowHandle();
		
			// Open new window and navigate to Railway
		Constant.WEBDRIVER.switchTo().newWindow(WindowType.TAB);
		String railwayWindow = Constant.WEBDRIVER.getWindowHandle();
		
		// Step 1 : Navigate to QA Railway Website
		System.out.println("\nStep 1 : Navigate to QA Railway Website");
		homePage.open();
		
		// Step 2 : Click on "Register" tab
		System.out.println("\nStep 2 : Click on 'Register' tab");
		RegisterPage registerPage = homePage.gotoRegisterPage();
		
		// Step 3 : Enter valid information into all fields
		System.out.println("\nStep 3: Enter valid information into all fields");
		Constant.RegistrationData registrationData = new Constant.RegistrationData(tempEmail, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);
		
		System.out.println("Email: " + tempEmail);
		System.out.println("Password:" + Constant.PASSWORD);
		System.out.println("PID: " + Constant.PID);
		
		// Step 4 : Click on "Register" button
		System.out.println("Click on 'Register' button");
		registerPage.register(registrationData);
		
			// Verify "Thank you for registering your account" is shown
		String actualSuccessMsg = registerPage.getSuccessMessage();
		System.out.println("Success message received: " + actualSuccessMsg);
		
		Assert.assertTrue(
			actualSuccessMsg.contains("Thank you for registering") || 
			actualSuccessMsg.contains("activation") || actualSuccessMsg.contains("email"), 
			"Registration success message is not displayed as expected. Actual: " + actualSuccessMsg );
		System.out.println("  Registration successful!");
		
		// Step 5: Get email information (webmail address, mailbox and password) and navigate to that webmail 
		System.out.println("\nStep 5: Get email information and navigate to GuerrillaMail");
		Constant.WEBDRIVER.switchTo().window(guerrillaMailWindow);
		
			// Ad-blocking
		mailPage.closeAdvertisements();
		
			// Wait for activation email (30 seconds) and click activation link
		boolean emailReceivedAndLinkClicked = mailPage.waitForActivationEmailAndClick(30);
		Assert.assertTrue(emailReceivedAndLinkClicked, 
			"Failed to receive activation email from thanhletraining or click activation link within 30 seconds");
		
		// Step 6: Verify Registration Confirmed page
		System.out.println("\nStep 6: Verify Registration Confirmed");
		RegisterPage confirmPage = new RegisterPage(driver);
		
		// Verify "Registration Confirmed" message is displayed
		boolean isConfirmed = confirmPage.isRegistrationConfirmed();
		Assert.assertTrue(isConfirmed, "Registration Confirmed message is not displayed");
		
		String confirmMessage = confirmPage.getRegistrationConfirmedMessage();
		System.out.println("Confirmation message: " + confirmMessage);
		System.out.println("\nTC09 PASSED - Account created and activated successfully!");
	}
}