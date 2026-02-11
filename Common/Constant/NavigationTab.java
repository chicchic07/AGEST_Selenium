package Constant;

import org.openqa.selenium.By;

public enum NavigationTab {
    LOGIN("Login", "//div[@id='menu']//a[@href='/Account/Login.cshtml']"),
    LOGOUT("Logout", "//div[@id='menu']//a[@href='/Account/Logout']"),
    REGISTER("Register", "//div[@id='menu']//a[contains(@href, 'Register')]"),
    BOOK_TICKET("Book ticket", "//div[@id='menu']//a[contains(@href, 'Book')]"),
    FAQ("FAQ", "//div[@id='menu']//a[contains(@href, 'FAQ')]"),
    TIMETABLE("Timetable", "//div[@id='menu']//a[contains(@href, 'Time')]"),
    MY_TICKET("My ticket", "//div[@id='menu']//a[contains(@href, 'Manage')]");
    
    private final String tabName;
    private final String xpath;
    
    NavigationTab(String tabName, String xpath) {
        this.tabName = tabName;
        this.xpath = xpath;
    }
    
    public String getTabName() {
        return tabName;
    }
    
    public By getLocator() {
        return By.xpath(xpath);
    }
}