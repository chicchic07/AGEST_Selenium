package src;

import org.testng.Assert;
import org.testng.annotations.Test;

import Common.RequiresAccount;
import Constant.Constant;
import Railway.ForgotPage;
import Railway.ResetPasswordPage;

public class ResetPasswordTest extends BaseTest {

    @Test
    @RequiresAccount
    public void TC10() throws Exception {
        System.out.println("TC10 - Reset password shows error if new password is same as current");
        System.out.println("============================================================");

        // Step 1: Navigate to QA Railway Login page
        System.out.println("\nStep 1: Navigate to QA Railway Login page");
        homePage.open();

        // Step 2: Click on "Forgot Password page" link
        System.out.println("\nStep 2: Click on \"Forgot Password page\" link");
        ForgotPage forgotPage = homePage.gotoLoginPage().clickForgotPasswordLink();

        // Step 3: Enter the email address of the activated account
        System.out.println("\nStep 3: Enter the email address of the activated account");
        System.out.println("  Email: " + tempEmail);
        forgotPage.enterEmail(tempEmail);

        // Step 4: Click on "Send Instructions" button
        System.out.println("\nStep 4: Click on \"Send Instructions\" button");
        forgotPage.clickSendInstructions();

        	// Verify success message
        String successMsg = forgotPage.getSuccessMessage();
        System.out.println("  Success message: " + successMsg);
        Assert.assertTrue(
            successMsg.contains("Instructions to reset your password"),
            "Reset instruction message incorrect. Actual: " + successMsg
        );

        // Step 5: Login to the mailbox (the same mailbox when creating account) 
        System.out.println("\nStep 5: Login to the mailbox (the same mailbox when creating account) ");
        driver.switchTo().window(guerrillaMailWindow);

        // Step 6: Open email with subject contaning "Please reset your password" and the email of the account at step 3
        // Step 7: Click on reset link
        System.out.println("\nStep 6: Open email with subject contaning \"Please reset your password\" and the email of the account at step 3");
        System.out.println("\nStep 7: Click on reset link");
        boolean resetOpened = mailPage.waitForResetPasswordEmailAndClick(30);
        Assert.assertTrue(resetOpened, "Reset password email not received or reset link click failed");

        // Step 8:  Input same password into 2 fields  "new password" and "confirm password"
        System.out.println("\nStep 8:  Input same password into 2 fields  \"new password\" and \"confirm password\"");
        ResetPasswordPage resetPage = new ResetPasswordPage(driver);

        String newPassword = Constant.PASSWORD + "new";
        System.out.println("  New Password: " + newPassword);
        System.out.println("  Confirm Password: " + newPassword);
        resetPage.enterNewPassword(newPassword);
        resetPage.enterConfirmPassword(newPassword);

        // Step 9: Click Reset Password
        System.out.println("\nStep 9: Click Reset Password");
        resetPage.clickResetPasswordButton();

        // Verify success message "Password changed! Click here to login." xuất hiện
        System.out.println("\nStep 9: Verify success message displayed");
        String successResetMsg = resetPage.getSuccessMessage();
        System.out.println("  Success message: " + successResetMsg);
        Assert.assertTrue(
            successResetMsg.contains("Password changed"),
            "Password changed success message not displayed. Actual: " + successResetMsg
        );
    }

    @Test
    @RequiresAccount
    public void TC11() throws Exception {
        System.out.println("TC11 - Reset password shows error if the new password and confirm password doesn't match");
        System.out.println("============================================================");

        // Step 1: Navigate to QA Railway Login page
        System.out.println("\nStep 1: Navigate to QA Railway Login page");
        homePage.open();

        // Step 2: Click on "Forgot Password page" link
        System.out.println("\nStep 2: Click on \"Forgot Password page\" link");
        ForgotPage forgotPage = homePage.gotoLoginPage().clickForgotPasswordLink();

        // Step 3: Enter the email address of the activated account
        System.out.println("\nStep 3: Enter the email address of the activated account");
        System.out.println("  Email: " + tempEmail);
        forgotPage.enterEmail(tempEmail);

        // Step 4: Click on "Send Instructions" button
        System.out.println("\nStep 4: Click on \"Send Instructions\" button");
        forgotPage.clickSendInstructions();

        	// Verify success message
        String successMsg = forgotPage.getSuccessMessage();
        System.out.println("  Success message: " + successMsg);
        Assert.assertTrue(
            successMsg.contains("Instructions to reset your password"),
            "Reset instruction message incorrect. Actual: " + successMsg
        );

        // Step 5: Login to the mailbox (the same mailbox when creating account) 
        System.out.println("\nStep 5: Login to the mailbox (the same mailbox when creating account) ");
        driver.switchTo().window(guerrillaMailWindow);

        // Step 6: Open email with subject contaning "Please reset your password" and the email of the account at step 3
        // Step 7: Click on reset link
        System.out.println("\nStep 6: Open email with subject contaning \"Please reset your password\" and the email of the account at step 3");
        System.out.println("\nStep 7: Click on reset link");
        boolean resetOpened = mailPage.waitForResetPasswordEmailAndClick(30);
        Assert.assertTrue(resetOpened, "Reset password email not received or reset link click failed");

        // Step 8:  Input different input into 2 fields  "new password" and "confirm password"
        System.out.println("\nStep 8:  Input different input into 2 fields  \"new password\" and \"confirm password\"");
        ResetPasswordPage resetPage = new ResetPasswordPage(driver);

        String newPassword = Constant.PASSWORD + "new";
        System.out.println("  New Password: " + newPassword);
        System.out.println("  Confirm Password: " + Constant.PASSWORD);
        resetPage.enterNewPassword(newPassword);
        resetPage.enterConfirmPassword(Constant.PASSWORD);

        // Step 9: Click Reset Password
        System.out.println("\nStep 9: Click Reset Password");
        resetPage.clickResetPasswordButton();

        	// Verify 2 error messages theo đề:
        System.out.println("\nVerifying error messages...");

        		// Error 1: General error phía trên form
        String generalError = resetPage.getGeneralErrorMessage();
        System.out.println("  General error: " + generalError);
        Assert.assertTrue(
            generalError.contains("Could not reset password. Please correct the errors and try again."),
            "General error message is incorrect. Actual: " + generalError
        );

        		// Error 2: Field error cạnh confirm password
        String confirmPasswordError = resetPage.getConfirmPasswordErrorMessage();
        System.out.println("  Confirm password field error: " + confirmPasswordError);
        Assert.assertTrue(
            confirmPasswordError.contains("The password confirmation did not match the new password."),
            "Confirm password field error message is incorrect. Actual: " + confirmPasswordError
        );

    }
}