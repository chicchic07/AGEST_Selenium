package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GuerrillaMailPage extends GeneralPage {
    private String currentEmail = null;
    // Lưu handle của tab GuerrillaMail để switch về khi cần
    private String guerrillaMailWindowHandle = null;
    
    // ========== LOCATORS - Consistent naming ==========
    private static final By SPAN_EMAIL_WIDGET = By.id("email-widget");
    private static final By TXT_EMAIL_STR = By.id("email-str");
    private static final By IFRAME_EMAIL = By.id("emailFrame");
    private static final By DIV_EMAIL_BODY = By.id("email_body");
    private static final By CHK_SCRAMBLE = By.id("use-alias");
    private static final String TD_EMAIL_FROM_SENDER = "//td[contains(text(), '%s')]";
    // Tìm email theo subject để phân biệt activation email vs reset password email
    private static final String TD_EMAIL_BY_SUBJECT = "//td[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '%s')]";
    private static final By LINK_ACTIVATION_SAFERAILWAY = By.xpath("//a[contains(@href, 'saferailway')]");
    private static final By LINK_RESET_PASSWORD = By.xpath("//a[contains(@href, 'ResetPassword') or contains(text(), 'reset') or contains(text(), 'Reset')]");
    
    public GuerrillaMailPage(WebDriver driver) {
        super(driver);
    }
    
    // ========== PUBLIC METHODS - MAIN ACTIONS ==========
    
    public GuerrillaMailPage open() {
        System.out.println("Opening GuerrillaMail...");
        driver.navigate().to(Constant.GUERRILLA_MAIL_URL);

        // Lưu lại handle của tab GuerrillaMail ngay khi mở
        guerrillaMailWindowHandle = driver.getWindowHandle();

        waitForPageLoad(); 
        waitForCondition(
            d -> getEmailViaJS() != null && getEmailViaJS().contains("@"), 
            10, 
            "Email generated"
        );
        
        System.out.println("GuerrillaMail loaded successfully");
        return this;
    }
    
    public GuerrillaMailPage uncheckScrambleAddress() {
        String oldEmail = getEmailViaJS();
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(CHK_SCRAMBLE));
        
        if (!checkbox.isSelected()) {
            System.out.println("Scramble already unchecked");
            return this;
        }
        
        checkbox.click();
        System.out.println("Scramble Address unchecked");
        
        // Wait for email to change
        waitForCondition(
            d -> {
                String newEmail = getEmailViaJS();
                return newEmail != null && !newEmail.equals(oldEmail);
            }, 
            5, 
            "Email changed after uncheck scramble"
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
    
    public GuerrillaMailPage closeAdvertisements() {
        System.out.println("Closing advertisements...");
        
        try {
            waitForCondition(d -> countAdsOnPage() > 0, 2, "Ads displayed");
            
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
                
                // Click email to open
                driver.findElement(
                    By.xpath(String.format(TD_EMAIL_FROM_SENDER, "thanhletraining"))
                ).click();
                
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

        // Đảm bảo driver đang ở tab GuerrillaMail trước khi tìm email
        driver.switchTo().window(guerrillaMailWindowHandle);
        System.out.println("  Switched to GuerrillaMail tab");

        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000);

        while (System.currentTimeMillis() < endTime) {
            refreshInbox();

            // Tìm email theo subject "reset" để không nhầm với email activation
            String resetSubjectXpath = String.format(TD_EMAIL_BY_SUBJECT, "reset");
            List<WebElement> resetEmails = driver.findElements(By.xpath(resetSubjectXpath));

            if (!resetEmails.isEmpty()) {
                System.out.println("  Found reset password email by subject");
                try {
                    resetEmails.get(0).click();
                } catch (Exception e) {
                    // Fallback: JS click nếu bị stale/not interactable
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", resetEmails.get(0));
                }

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
            ExpectedConditions.presenceOfElementLocated(SPAN_EMAIL_WIDGET),
            ExpectedConditions.presenceOfElementLocated(TXT_EMAIL_STR)
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
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(IFRAME_EMAIL),
                ExpectedConditions.presenceOfElementLocated(DIV_EMAIL_BODY)
            ));
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            // Content might be in main body
        }
    }
    
    private boolean clickActivationLink() {
        System.out.println("Looking for activation link...");

        try {
            // Wait for link to appear
            wait.until(ExpectedConditions.presenceOfElementLocated(LINK_ACTIVATION_SAFERAILWAY));

            // Get URL from link (không click trực tiếp để tránh mở trên tab GuerrillaMail)
            String activationUrl = driver.findElement(LINK_ACTIVATION_SAFERAILWAY)
                                         .getAttribute("href");

            System.out.println("  Found activation link: " + activationUrl);

            // Mở tab mới và navigate đến activation URL
            driver.switchTo().newWindow(WindowType.TAB);
            System.out.println("  Opened new tab for activation");

            driver.navigate().to(activationUrl);

            // Wait for activation page to load
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("Registration"),
                ExpectedConditions.urlContains("Confirm")
            ));

            System.out.println("  Activation page loaded in new tab");
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
            wait.until(ExpectedConditions.presenceOfElementLocated(LINK_RESET_PASSWORD));

            // Lấy URL từ link, không click trực tiếp để tránh mở trên tab GuerrillaMail
            String resetUrl = driver.findElement(LINK_RESET_PASSWORD)
                                    .getAttribute("href");

            System.out.println("  Found reset password link: " + resetUrl);

            // Mở tab mới và navigate đến reset password URL
            driver.switchTo().newWindow(WindowType.TAB);
            System.out.println("  Opened new tab for reset password form");

            driver.navigate().to(resetUrl);

            // Wait for reset password page to load
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("ResetPassword"),
                ExpectedConditions.urlContains("Reset")
            ));

            System.out.println("  Reset password page loaded in new tab");
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