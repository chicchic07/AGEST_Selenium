package Constant;

import org.openqa.selenium.WebDriver;

public class Constant {
	public static WebDriver WEBDRIVER;
	public static final String RAILWAY_URL = "http://saferailway.somee.com/Page/HomePage.cshtml";
	public static final String GUERRILLA_MAIL_URL = "https://www.guerrillamail.com/inbox";
	
	public static final String USERNAME = "1202phamnam@gmail.com";
	public static final String PASSWORD = "11111111";
	
	public static final int DEFAULT_TIME = 10;
	public static final int PAGE_LOAD_TIMEOUT = 30;
	public static final int LONG_WAIT_TIME = 30;
	
	public static final String WELCOME_MESSAGE_FORMAT = "Welcome %s";
	public static final String LOGIN_ERROR_MESSAGE = "There was a problem with your login and/or errors exist in your form.";
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid username or password. Please try again.";
    public static final String LOGIN_ATTEMPTS_WARNING = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
    public static final int MAX_LOGIN_ATTEMPTS = 4;
    public static final String INVALID_PASSWORD = "123456789";
    
    public static final String REGISTRATION_SUCCESS_MESSAGE = "An email with instructions on how to activate your account is on its way to you.\r\n"
    		+ "Please check your mailbox.";
	public static final String DUPLICATE_EMAIL_MESSAGE = "This email address is already in use.";
	
	public static final User VALID_USER = new User(USERNAME, PASSWORD);
    public static final User BLANK_USERNAME_USER = new User("", PASSWORD);
    public static final User INVALID_PASSWORD_USER = new User(USERNAME, INVALID_PASSWORD);
    public static final User WRONG_PASSWORD_USER = new User(USERNAME, "wrongPassword123");
    public static final User INACTIVE_USER = new User("inactive@gmail.com", "123456789");
    
    public static class User {
        private String username;
        private String password;
        
        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }
        
        public String getUsername() {
            return username;
        }
        
        public String getPassword() {
            return password;
        }
    }
    
    public static class RegistrationData {
        private String email;
        private String password;
        private String confirmPassword;
        private String pid;
        
        public RegistrationData(String email, String password, String confirmPassword, String pid) {
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
            this.pid = pid;
        }
        
        public String getEmail() {
            return email;
        }
        
        public String getPassword() {
            return password;
        }
        
        public String getConfirmPassword() {
            return confirmPassword;
        }
        
        public String getPid() {
            return pid;
        }
    }
}