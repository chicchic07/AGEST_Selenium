package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GuerrillaMailPage extends GeneralPage {
    private String currentEmail = null;
    
    // LOCATORS 
    private final By emailWidgetSpan = By.id("email-widget");
    private final By emailStrField = By.id("email-str");
    private final By emailIframe = By.id("emailFrame");
    private final By mailBodyDiv = By.id("email_body");
    private final By scrambleCheckbox = By.id("use-alias");
    private final By emailFromSender = By.xpath("//td[contains(text(), '%s')]");
    private final By activationLinkSafeRailway = By.xpath("//a[contains(@href, 'saferailway')]");
    private final By resetPasswordLink = By.xpath("//a[contains(@href, 'ResetPassword') or contains(text(), 'reset') or contains(text(), 'Reset')]");
    
    // ========== CONSTRUCTOR ==========
    public GuerrillaMailPage(WebDriver driver) {
        super(driver);
    }
    
    // ========== PUBLIC METHODS - MAIN ACTIONS ==========
    public GuerrillaMailPage open() {
        System.out.println("Opening GuerrillaMail...");
        driver.navigate().to(Constant.GUERRILLA_MAIL_URL);
        
        waitForPageLoad(); 
        waitForCondition( d -> getEmailViaJS() != null && getEmailViaJS().contains("@"), 10, "Email generated" );
        
        System.out.println("GuerrillaMail loaded successfully");
        return this;
    }
    
    public GuerrillaMailPage uncheckScrambleAddress() {
        String oldEmail = getEmailViaJS();
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(scrambleCheckbox));
        
        if (!checkbox.isSelected()) {
            System.out.println("Scramble already unchecked");
            return this;
        }
        
        checkbox.click();
        System.out.println("Scramble Address unchecked");
        
        // Đợi email mới
        waitForCondition(
            d -> {
                String newEmail = getEmailViaJS();
                return newEmail != null && !newEmail.equals(oldEmail);
            }, 5, "Email changed after uncheck scramble"
        );
        
        currentEmail = getEmailViaJS();
        System.out.println("New email: " + currentEmail);
        return this;
    }
    
    public String getEmailAddress() {
        if (currentEmail != null && currentEmail.contains("@")) {
            System.out.println("Using cached email: " + currentEmail);
            return currentEmail;
        }
        
        currentEmail = getEmailViaJS();
        if (currentEmail == null || !currentEmail.contains("@")) {
            throw new RuntimeException("Failed to retrieve email from GuerrillaMail");
        }
        
        System.out.println("Email retrieved: " + currentEmail);
        return currentEmail;
    }
    
    //Đóng quảng cáo (nếu có)
    public GuerrillaMailPage closeAdvertisements() {
        System.out.println("Closing advertisements...");
        
        try {
            waitForCondition( d -> countAdsOnPage() > 0, 2, "Ads displayed" );
            
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "const ads = document.querySelectorAll('.adsbygoogle, .advertisement, [id*=\"ad-\"]');" +
                "ads.forEach(ad => ad.remove());"
            );
            
            System.out.println("Ads removed");
        } catch (Exception e) {
            System.out.println("No ads found or already removed");
        }
        
        return this;
    }
    
    public boolean waitForActivationEmailAndClick(int timeoutSeconds) {
        System.out.println("Waiting for activation email (timeout: " + timeoutSeconds + "s)...");
        
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000);
        
        while (System.currentTimeMillis() < endTime) {
            refreshInbox();
            
            if (waitForEmailFromSender("thanhletraining", 3)) {
                System.out.println("  Found email from thanhletraining");
                
                // Click email để mở
                driver.findElement(By.xpath(String.format(emailFromSender.toString().replace("By.xpath: ", ""), "thanhletraining"))).click();
                
                waitForEmailContentLoad();
                return clickActivationLink();
            }
            
            System.out.println("Waiting... (" + getRemainingSeconds(endTime) + "s left)");
        }
        
        System.out.println("  Timeout: No activation email received");
        return false;
    }
    
    public boolean waitForResetPasswordEmailAndClick(int timeoutSeconds) {
        System.out.println("Waiting for reset password email (timeout: " + timeoutSeconds + "s)...");
        
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000);
        
        while (System.currentTimeMillis() < endTime) {
            refreshInbox();
            
            if (waitForEmailFromSender("thanhletraining", 3)) {
                System.out.println("  Found email from thanhletraining");
                
                // Click email to open
                driver.findElement(By.xpath(String.format(emailFromSender.toString().replace("By.xpath: ", ""), "thanhletraining"))).click();
                
                waitForEmailContentLoad();
                return clickResetPasswordLink();
            }
            
            System.out.println("Waiting... (" + getRemainingSeconds(endTime) + "s left)");
        }
        
        System.out.println("  Timeout: No reset password email received");
        return false;
    }
    
    public String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }
    
    
    
    // ========== PRIVATE HELPER METHODS ==========
    private void waitForPageLoad() {
        wait.until(ExpectedConditions.urlContains("guerrillamail.com"));
        wait.until(ExpectedConditions.or(
            ExpectedConditions.presenceOfElementLocated(emailWidgetSpan),
            ExpectedConditions.presenceOfElementLocated(emailStrField)
        ));
    }
    
    private String getEmailViaJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        String[] scripts = {
            "return document.getElementById('email-widget')?.textContent?.trim();",
            "return document.getElementById('email-str')?.value?.trim();",
            "return window.emailaddress;"
        };
        
        for (String script : scripts) {
            try {
                Object result = js.executeScript(script);
                if (result != null) {
                    String email = result.toString().trim();
                    if (email.contains("@") && !email.contains("null")) {
                        return email;
                    }
                }
            } catch (Exception e) {
                // Continue to next script
            }
        }
        
        return null;
    }
    
    private long countAdsOnPage() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object count = js.executeScript(
                "return document.querySelectorAll('.adsbygoogle, .advertisement, [id*=\"ad-\"]').length;"
            );
            return count != null ? (Long) count : 0;
        } catch (Exception e) {
            return 0;
        }
    }
    
    private void refreshInbox() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("if(typeof check_email === 'function') { check_email(); }");
        } catch (Exception e) {
            System.err.println("Cannot refresh inbox: " + e.getMessage());
        }
    }
    
    private boolean waitForEmailFromSender(String senderName, int timeoutSeconds) {
        return waitForCondition(
            d -> {
                List<WebElement> emails = d.findElements(
                    By.xpath("//td[contains(text(), '" + senderName + "')]")
                );
                return emails.size() > 0;
            },
            timeoutSeconds,
            "Email from " + senderName
        );
    }
    
    private void waitForEmailContentLoad() {
        try {
            WebDriverWait contentWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            contentWait.until(ExpectedConditions.or(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(emailIframe),
                ExpectedConditions.presenceOfElementLocated(mailBodyDiv)
            ));
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            // Content might be in main body
        }
    }
    
    private boolean clickActivationLink() {
        System.out.println("Looking for activation link...");
        
        try {
            // Chờ link xuất hiện
            wait.until(ExpectedConditions.presenceOfElementLocated(activationLinkSafeRailway));
            
            // Lấy URL và navigate
            String activationUrl = driver.findElement(activationLinkSafeRailway)
                                         .getAttribute("href");
            
            System.out.println("  Found link: " + activationUrl);
            driver.navigate().to(activationUrl);
            
            // Chờ trang activation load
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("Registration"),
                ExpectedConditions.urlContains("Confirm")
            ));
            
            System.out.println("  Activation page loaded");
            return true;
            
        } catch (Exception e) {
            System.err.println("  Cannot find activation link: " + e.getMessage());
            return false;
        }
    }
    
    private boolean clickResetPasswordLink() {
        System.out.println("Looking for reset password link...");
        
        try {
            // Wait for link to appear
            wait.until(ExpectedConditions.presenceOfElementLocated(resetPasswordLink));
            
            // Get URL and navigate
            String resetUrl = driver.findElement(resetPasswordLink)
                                    .getAttribute("href");
            
            System.out.println("  Found link: " + resetUrl);
            driver.navigate().to(resetUrl);
            
            // Wait for reset password page to load
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("ResetPassword"),
                ExpectedConditions.urlContains("Reset")
            ));
            
            System.out.println("  Reset password page loaded");
            return true;
            
        } catch (Exception e) {
            System.err.println("  Cannot find reset password link: " + e.getMessage());
            return false;
        }
    }
    
    private int getRemainingSeconds(long endTime) {
        return (int) ((endTime - System.currentTimeMillis()) / 1000);
    }
    
    private boolean waitForCondition(
        java.util.function.Function<WebDriver, Boolean> condition,
        int timeoutSeconds,
        String description
    ) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(condition);
            return true;
        } catch (Exception e) {
            System.out.println("Timeout waiting for: " + description);
            return false;
        }
    }
}