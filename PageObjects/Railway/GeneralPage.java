package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Constant.NavigationTab;

import java.time.Duration;

public abstract class GeneralPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    // ========== LOCATORS - Consistent naming convention ==========
    // Common locators
    private static final By LBL_WELCOME_MESSAGE = By.xpath("//h1[contains(text(), 'Welcome to')]");
    private static final By LBL_ERROR_MESSAGE = By.xpath("//p[contains(@class,'message error')]");
    
    // Navigation tab locators - centralized here instead of in enum
    private static final By LINK_LOGIN = By.xpath("//div[@id='menu']//a[@href='/Account/Login.cshtml']");
    private static final By LINK_LOGOUT = By.xpath("//div[@id='menu']//a[@href='/Account/Logout']");
    private static final By LINK_REGISTER = By.xpath("//div[@id='menu']//a[contains(@href, 'Register')]");
    private static final By LINK_BOOK_TICKET = By.xpath("//div[@id='menu']//a[contains(@href, 'Book')]");
    private static final By LINK_FAQ = By.xpath("//div[@id='menu']//a[contains(@href, 'FAQ')]");
    private static final By LINK_TIMETABLE = By.xpath("//div[@id='menu']//a[contains(@href, 'Time')]");
    private static final By LINK_MY_TICKET = By.xpath("//div[@id='menu']//a[contains(@href, 'Manage')]");
    
    public GeneralPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    // ========== NAVIGATION METHODS ==========
    
    /**
     * Get locator for navigation tab
     */
    private By getTabLocator(NavigationTab tab) {
        switch (tab) {
            case LOGIN:
                return LINK_LOGIN;
            case LOGOUT:
                return LINK_LOGOUT;
            case REGISTER:
                return LINK_REGISTER;
            case BOOK_TICKET:
                return LINK_BOOK_TICKET;
            case FAQ:
                return LINK_FAQ;
            case TIMETABLE:
                return LINK_TIMETABLE;
            case MY_TICKET:
                return LINK_MY_TICKET;
            default:
                throw new IllegalArgumentException("Unknown tab: " + tab);
        }
    }
    
    /**
     * Generic method to click any navigation tab using enum
     */
    protected void clickTab(NavigationTab tab) {
        By locator = getTabLocator(tab);
        WebElement tabElement = wait.until(
            ExpectedConditions.elementToBeClickable(locator)
        );
        tabElement.click();
    }
    
    public LoginPage gotoLoginPage() {
        clickTab(NavigationTab.LOGIN);
        return new LoginPage(driver);
    }
    
    public RegisterPage gotoRegisterPage() {
        clickTab(NavigationTab.REGISTER);
        return new RegisterPage(driver);
    }
    
    public BookTicketPage gotoBookTicketPage() {
        clickTab(NavigationTab.BOOK_TICKET);
        return new BookTicketPage(driver);
    }
    
    public TimeTablePage gotoTimeTablePage() {
        clickTab(NavigationTab.TIMETABLE);
        return new TimeTablePage(driver);
    }
    
    public MyTicketPage gotoMyTicketPage() {
        clickTab(NavigationTab.MY_TICKET);
        return new MyTicketPage(driver);
    }
    
    public HomePage clickFAQ() {
        clickTab(NavigationTab.FAQ);
        return new HomePage(driver);
    }
    
    public HomePage logout() {
        clickTab(NavigationTab.LOGOUT);
        return new HomePage(driver);
    }
    
    // ========== MESSAGE METHODS ==========
    
    public String getWelcomeMessage() {
        return getElement(LBL_WELCOME_MESSAGE).getText();
    }
    
    public String getErrorMessage() {
        return getElement(LBL_ERROR_MESSAGE).getText();
    }
    
    // ========== USER STATE METHODS ==========
    
    public boolean isUserLoggedIn() {
        try {
            return getElement(LBL_WELCOME_MESSAGE).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    // ========== HELPER METHODS ==========
    
    protected WebElement getElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    protected WebElement getClickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}