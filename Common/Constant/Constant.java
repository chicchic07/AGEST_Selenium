package Constant;

public class Constant {
    
    // ========== URLs ==========
    public static final String RAILWAY_URL = "http://www.saferailway.somee.com/Page/HomePage.cshtml";
    public static final String GUERRILLA_MAIL_URL = "https://www.guerrillamail.com/inbox";
    
    // ========== CREDENTIALS ==========
    public static final String USERNAME = "khoivu2302@gmail.com";
    public static final String PASSWORD = "123456789";
    public static final String PID = "123456789";
    public static final String INVALID_PASSWORD = "wrongPassword123";
    
    // ========== TIMEOUTS ==========
    public static final int DEFAULT_TIME = 10;
    public static final int PAGE_LOAD_TIMEOUT = 30;
    public static final int LONG_WAIT_TIME = 30;
    
    // ========== MESSAGES ==========
    public static final String WELCOME_MESSAGE_FORMAT = "Welcome %s";
    public static final String LOGIN_ERROR_MESSAGE = "There was a problem with your login and/or errors exist in your form.";
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid username or password. Please try again.";
    public static final String LOGIN_ATTEMPTS_WARNING = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
    public static final String REGISTRATION_SUCCESS_MESSAGE = "An email with instructions on how to activate your account is on its way to you.\r\nPlease check your mailbox.";
    public static final String DUPLICATE_EMAIL_MESSAGE = "This email address is already in use.";
    public static final String TICKET_BOOKED_SUCCESS_MESSAGE = "Ticket booked successfully!";
    
    // ========== LOGIN SETTINGS ==========
    public static final int MAX_LOGIN_ATTEMPTS = 4;
    
    // ========== DATA CLASSES ==========
    
    public static class User {
        private String username = "";
        private String password = "";
        
        public User() {
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
        private final String email;
        private final String password;
        private final String confirmPassword;
        private final String pid;
        
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