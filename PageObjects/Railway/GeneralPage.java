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
    
    // Common locators
    private static final By WELCOME_MESSAGE = By.xpath("//h1[contains(text(), 'Welcome to')]");
    private static final By ERROR_MESSAGE = By.xpath("//p[contains(@class,'message error')]");
    
    public GeneralPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    // ========== NAVIGATION METHODS ==========
    
    /**
     * Generic method to click any navigation tab using enum
     */
    protected void clickTab(NavigationTab tab) {
        WebElement tabElement = wait.until(
            ExpectedConditions.elementToBeClickable(tab.getLocator())
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
        return getElement(WELCOME_MESSAGE).getText();
    }
    
    public String getErrorMessage() {
        return getElement(ERROR_MESSAGE).getText();
    }
    
    // ========== USER STATE METHODS ==========
    
    public boolean isUserLoggedIn() {
        try {
            return getElement(WELCOME_MESSAGE).isDisplayed();
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