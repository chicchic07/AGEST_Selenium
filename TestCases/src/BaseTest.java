package src;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import Common.RequiresAccount;
import Constant.BrowserType;
import Constant.Constant;
import Railway.GuerrillaMailPage;
import Railway.HomePage;
import Railway.RegisterPage;

public class BaseTest {
    protected HomePage homePage;
    protected WebDriver driver;

    // Shared data giữa @BeforeMethod và các TC con
    protected String tempEmail;
    protected GuerrillaMailPage mailPage;
    protected String guerrillaMailWindow;

    public WebDriver getDriver() {
        return driver;
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(Method method, @Optional("chrome") String browser) throws Exception {
        driver = createDriver(browser);
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        homePage.open();

        // Chỉ register nếu test method có annotation @RequiresAccount
        if (method.isAnnotationPresent(RequiresAccount.class)) {
            System.out.println("  [@RequiresAccount] Registering new account for: " + method.getName());
            registerAndActivateNewAccount();
        }
    }

    private void registerAndActivateNewAccount() throws Exception {
        System.out.println("\n========== @BeforeMethod: Register & Activate New Account ==========");

        // Pre-Cond Step 1: Navigate to GuerrillaMail to get temporary email
        System.out.println("[Pre-Condition] Step 1: Navigate to GuerrillaMail to get temporary email");
        mailPage = new GuerrillaMailPage(driver);
        mailPage.open().uncheckScrambleAddress();

        tempEmail = mailPage.getEmailAddress();
        Assert.assertNotNull(tempEmail, "Failed to get temporary email from GuerrillaMail");
        Assert.assertTrue(tempEmail.contains("@"), "Invalid email format: " + tempEmail);
        System.out.println("  Temporary email generated: " + tempEmail);

        // Save GuerrillaMail window handle to switch back later
        guerrillaMailWindow = mailPage.getCurrentWindowHandle();

        // Pre-Cond Step 2: Open new tab and navigate to Railway
        System.out.println("[Pre-Condition] Step 2: Open new tab and navigate to Railway Website");
        driver.switchTo().newWindow(WindowType.TAB);
        homePage.open();

        // Pre-Cond Step 3: Click Register tab
        System.out.println("[Pre-Condition] Step 3: Click on 'Register' tab");
        RegisterPage registerPage = homePage.gotoRegisterPage();

        // Pre-Cond Step 4: Enter valid information and register
        System.out.println("[Pre-Condition] Step 4: Enter valid information and click 'Register'");
        Constant.RegistrationData registrationData = new Constant.RegistrationData(
            tempEmail, Constant.PASSWORD, Constant.PASSWORD, Constant.PID
        );
        System.out.println("  Email: "    + tempEmail);
        System.out.println("  Password: " + Constant.PASSWORD);
        System.out.println("  PID: "      + Constant.PID);

        registerPage.register(registrationData);

        String successMsg = registerPage.getSuccessMessage();
        System.out.println("  Success message: " + successMsg);
        Assert.assertTrue(
            successMsg.contains("Thank you for registering") ||
            successMsg.contains("activation") || successMsg.contains("email"),
            "Registration success message not displayed as expected. Actual: " + successMsg
        );
        System.out.println("  Registration successful!");

        // Pre-Cond Step 5: Switch to GuerrillaMail → activate account
        System.out.println("[Pre-Condition] Step 5: Switch to GuerrillaMail and activate account");
        driver.switchTo().window(guerrillaMailWindow);
        mailPage.closeAdvertisements();

        boolean activated = mailPage.waitForActivationEmailAndClick(30);
        Assert.assertTrue(activated, "Activation email not received or activation link click failed");

        RegisterPage confirmPage = new RegisterPage(driver);
        Assert.assertTrue(confirmPage.isRegistrationConfirmed(),
            "Registration Confirmed message is not displayed");
        System.out.println("  Confirmation: " + confirmPage.getRegistrationConfirmedMessage());
        System.out.println("  Account created and activated successfully!");
        System.out.println("====================================================================\n");
    }

    private WebDriver createDriver(String browserName) {
        BrowserType browserType;

        try {
            browserType = BrowserType.valueOf(browserName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown browser: " + browserName + ". Using Chrome as default.");
            browserType = BrowserType.CHROME;
        }

        switch (browserType) {
            case FIREFOX:
                System.out.println("Initializing Firefox Driver...");
                return new FirefoxDriver();

            case EDGE:
                System.out.println("Initializing Edge Driver...");
                return new EdgeDriver();

            case CHROME:
            default:
                System.out.println("Initializing Chrome Driver...");
                return new ChromeDriver();
        }
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Post-Condition");
        if (driver != null) {
            driver.quit();
        }
    }
}