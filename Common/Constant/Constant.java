package Constant;

import org.openqa.selenium.WebDriver;

public class Constant {
	public static WebDriver WEBDRIVER;
	public static final String RAILWAY_URL = "http://saferailway.somee.com/Page/HomePage.cshtml";
	public static final String USERNAME = "1202phamnam@gmail.com";
	public static final String PASSWORD = "11111111";
	
	public static final int DEFAULT_TIME = 10;
	public static final int PAGE_LOAD_TIMEOUT = 30;
	
	public static final String WELCOME_MESSAGE_FORMAT = "Welcome %s";
	public static final String LOGIN_ERROR_MESSAGE = "There was a problem with your login and/or errors exist in your form.";
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid username or password. Please try again.";
    public static final String LOGIN_ATTEMPTS_WARNING = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
    public static final int MAX_LOGIN_ATTEMPTS = 4;
    public static final String INVALID_PASSWORD = "123456789";
}