package src;

import org.testng.Assert;
import org.testng.annotations.Test;

import Constant.Constant;
import Railway.LoginPage;

public class ResetPassword extends BaseTest{
	@Test
	public void TC10() {
		System.out.println("TC10 - Reset password shows error if the new password is same as current");
		
		// Pre-Cond: an actived account is existing
		
		
		System.out.println("\nStep 1: Navigate to QA Railway Login page");
		homePage.open();
		
		System.out.println("\nStep 2: Click on 'Forgot Password page' link");
		
		
		System.out.println("\nStep 3: Enter the email address of the activated account");
		
		
		System.out.println("\nStep 4: Click on 'Send Instructions' button");
		
		
		System.out.println("\nStep 5: Login to the mailbox (the same mailbox when creating account)");
		
		
		System.out.println("\nStep 6: Open email with subject contaning 'Please reset your password' and the email of the account at step 3");
		
		
		System.out.println("\nStep 7: Click on reset link");
		
		
		System.out.println("\nStep 8: Input same password into 2 fields 'new password' and 'confirm password'");
		
		
		System.out.println("\nStep 9: Click Reset Password");
		
		
	}
}
