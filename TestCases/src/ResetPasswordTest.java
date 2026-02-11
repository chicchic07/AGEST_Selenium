package src;

import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;

import Common.TestDataManager;
import Constant.Constant;
import Railway.ForgotPage;
import Railway.GuerrillaMailPage;
import Railway.LoginPage;
import Railway.ResetPasswordPage;

public class ResetPasswordTest extends BaseTest {
	
	@Test(dependsOnMethods = {"src.CreateAccountTest.TC09"})
	public void TC10() throws Exception {
		System.out.println("TC10 - Reset password shows error if the new password is same as current");
		System.out.println("============================================================");
		
		// Pre-Condition: Verify TC09 data is available
		Assert.assertTrue(TestDataManager.hasTC09Data(), 
			"TC09 must run successfully before TC10. Please run TC09 first to create an activated account.");
		
		String activatedEmail = TestDataManager.getTC09Email();
		String currentPassword = TestDataManager.getTC09Password();
		
		System.out.println("Using account from TC09:");
		System.out.println("  Email: " + activatedEmail);
		System.out.println("  Password: " + currentPassword);
		
		// Step 1: Navigate to QA Railway Login page
		System.out.println("\nStep 1: Navigate to QA Railway Login page");
		homePage.open();
		LoginPage loginPage = homePage.gotoLoginPage();
		
		// Step 2: Click on 'Forgot Password page' link
		System.out.println("\nStep 2: Click on 'Forgot Password page' link");
		ForgotPage forgotPage = loginPage.clickForgotPasswordLink();
		
		// Step 3: Enter the email address of the activated account
		System.out.println("\nStep 3: Enter the email address of the activated account");
		System.out.println("  Email: " + activatedEmail);
		forgotPage.enterEmail(activatedEmail);
		
		// Step 4: Click on 'Send Instructions' button
		System.out.println("\nStep 4: Click on 'Send Instructions' button");
		forgotPage.clickSendInstructions();
		
			// Verify success message
		String successMsg = forgotPage.getSuccessMessage();
		System.out.println("  Success message: " + successMsg);
		Assert.assertTrue(successMsg.contains("Instructions to reset your password have been sent"),
				" Reset password success message is incorrect");
		
		// Step 5: Login to the mailbox (the same mailbox when creating account)
		System.out.println("\nStep 5: Navigate to GuerrillaMail to access the mailbox");
		
		// Open new tab for GuerrillaMail
		String railwayWindow = Constant.WEBDRIVER.getWindowHandle();
		Constant.WEBDRIVER.switchTo().newWindow(WindowType.TAB);
		
		GuerrillaMailPage mailPage = new GuerrillaMailPage(driver);
		mailPage.open();
		
		// The email should automatically be the same as TC09 since we're using the same domain
		String currentEmail = mailPage.getEmailAddress();
		System.out.println("  Mailbox email: " + currentEmail);
		
		// Close advertisements
		mailPage.closeAdvertisements();
		
		// Step 6: Open email with subject containing 'Please reset your password'
		System.out.println("\nStep 6: Waiting for reset password email...");
		
		// Step 7: Click on reset link
		System.out.println("\nStep 7: Click on reset link");
		boolean emailReceivedAndLinkClicked = mailPage.waitForResetPasswordEmailAndClick(30);
		Assert.assertTrue(emailReceivedAndLinkClicked, 
			"Failed to receive reset password email or click reset link within 30 seconds");
		
		// Step 8: Input same password into 2 fields 'new password' and 'confirm password'
		System.out.println("\nStep 8: Input same password into 'new password' and 'confirm password' fields");
		ResetPasswordPage resetPage = new ResetPasswordPage(driver);
		
		System.out.println("  Using same password as current: " + currentPassword);
		resetPage.enterNewPassword(currentPassword);
		resetPage.enterConfirmPassword(currentPassword);
		
		// Step 9: Click Reset Password
		System.out.println("\nStep 9: Click Reset Password button");
		resetPage.clickResetPasswordButton();
		
		// Verify error message
		System.out.println("\nVerifying error message...");
		
		// Wait a bit for error to appear
		Thread.sleep(2000);
		
		// Check for error message
		String errorMsg = "";
		boolean hasError = false;
		
		try {
			errorMsg = resetPage.getErrorMessage();
			hasError = true;
			System.out.println("  Error message found: " + errorMsg);
		} catch (Exception e) {
			// Try alternative error message location
			String passwordError = resetPage.getPasswordErrorMessage();
			if (!passwordError.isEmpty()) {
				errorMsg = passwordError;
				hasError = true;
				System.out.println("  Password field error found: " + errorMsg);
			}
		}
		
		Assert.assertTrue(hasError, "No error message displayed when using same password");
		Assert.assertTrue(
			errorMsg.toLowerCase().contains("cannot") || 
			errorMsg.toLowerCase().contains("same") || 
			errorMsg.toLowerCase().contains("different") ||
			errorMsg.toLowerCase().contains("current"),
			"Error message does not indicate the issue with using same password. Actual: " + errorMsg
		);
		
		System.out.println("\n✓ TC10 PASSED - System correctly prevents resetting password with same password");
		System.out.println("============================================================");
		
		// Clean up
		TestDataManager.clearTC09Data();
	}
	
	
	@Test(dependsOnMethods = {"src.CreateAccountTest.TC09"})
	public void TC11() throws Exception {
		System.out.println("TC11 - Reset password shows error if the new password and confirm password doesn't match");
		System.out.println("============================================================");
		
		// Pre-Condition: Verify TC09 data is available
		Assert.assertTrue(TestDataManager.hasTC09Data(), 
			"TC09 must run successfully before TC11. Please run TC09 first to create an activated account.");
		
		String activatedEmail = TestDataManager.getTC09Email();
		String currentPassword = TestDataManager.getTC09Password();
		
		System.out.println("Using account from TC09:");
		System.out.println("  Email: " + activatedEmail);
		System.out.println("  Password: " + currentPassword);
		
		// Step 1: Navigate to QA Railway Login page
		System.out.println("\nStep 1: Navigate to QA Railway Login page");
		homePage.open();
		LoginPage loginPage = homePage.gotoLoginPage();
		
		// Step 2: Click on 'Forgot Password page' link
		System.out.println("\nStep 2: Click on 'Forgot Password page' link");
		ForgotPage forgotPage = loginPage.clickForgotPasswordLink();
		
		// Step 3: Enter the email address of the activated account
		System.out.println("\nStep 3: Enter the email address of the activated account");
		System.out.println("  Email: " + activatedEmail);
		forgotPage.enterEmail(activatedEmail);
		
		// Step 4: Click on 'Send Instructions' button
		System.out.println("\nStep 4: Click on 'Send Instructions' button");
		forgotPage.clickSendInstructions();
		
			// Verify success message
		String successMsg = forgotPage.getSuccessMessage();
		System.out.println("  Success message: " + successMsg);
		Assert.assertTrue(successMsg.contains("Instructions to reset your password have been sent"),
				" Reset password success message is incorrect");
		
		// Step 5: Login to the mailbox (the same mailbox when creating account)
		System.out.println("\nStep 5: Login to the mailbox (the same mailbox when creating account)");
		
		// Open new tab for GuerrillaMail
		String railwayWindow = Constant.WEBDRIVER.getWindowHandle();
		Constant.WEBDRIVER.switchTo().newWindow(WindowType.TAB);
		
		GuerrillaMailPage mailPage = new GuerrillaMailPage(driver);
		mailPage.open();
		
			// The email should automatically be the same as TC09 since we're using the same domain
		String currentEmail = mailPage.getEmailAddress();
		System.out.println("  Mailbox email: " + currentEmail);
		
		// Close advertisements
		mailPage.closeAdvertisements();
		
		// Step 6: Open email with subject contaning 'Please reset your password' and the email of the account at step 3
		System.out.println("\nStep 6: Open email with subject contaning 'Please reset your password' and the email of the account at step 3");
		
		// Step 7: Click on reset link
		System.out.println("\nStep 7: Click on reset link");
		boolean emailReceivedAndLinkClicked = mailPage.waitForResetPasswordEmailAndClick(30);
		Assert.assertTrue(emailReceivedAndLinkClicked, 
			"Failed to receive reset password email or click reset link within 30 seconds");
		
		// Step 8: Input different input into 2 fields  "new password" and "confirm password"
		System.out.println("\nStep 8: Input different input into 2 fields 'new password' and 'confirm password'");
		ResetPasswordPage resetPage = new ResetPasswordPage(driver);
		
		System.out.println("  Using same password as current: " + currentPassword);
		resetPage.enterNewPassword(currentPassword);
		resetPage.enterConfirmPassword(Constant.PASSWORD);
		
		// Step 9: Click Reset Password
		System.out.println("\nStep 9: Click Reset Password button");
		resetPage.clickResetPasswordButton();
		
			// Verify error message
		System.out.println("\nVerifying error message...");
		
			// Wait a bit for error to appear
		Thread.sleep(2000);
		
		// Check for error message
		String errorMsg = "";
		boolean hasError = false;
		
		try {
			errorMsg = resetPage.getErrorMessage();
			hasError = true;
			System.out.println("  Error message found: " + errorMsg);
		} catch (Exception e) {
			// Try alternative error message location
			String passwordError = resetPage.getPasswordErrorMessage();
			if (!passwordError.isEmpty()) {
				errorMsg = passwordError;
				hasError = true;
				System.out.println("  Password field error found: " + errorMsg);
			}
		}
		
		Assert.assertTrue(hasError, "No error message displayed when using same password");
		Assert.assertTrue(
			errorMsg.toLowerCase().contains("cannot") || 
			errorMsg.toLowerCase().contains("same") || 
			errorMsg.toLowerCase().contains("different") ||
			errorMsg.toLowerCase().contains("current"),
			"Error message does not indicate the issue with using same password. Actual: " + errorMsg
		);
		
		System.out.println("\n✓ TC10 PASSED - System correctly prevents resetting password with same password");
		System.out.println("============================================================");
		
		// Clean up
		TestDataManager.clearTC09Data();
	}
}