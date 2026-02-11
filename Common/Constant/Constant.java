package Constant;

import org.openqa.selenium.WebDriver;

/**
 * Central configuration class for test constants
 */
public class Constant {
    // WebDriver instance
    public static WebDriver WEBDRIVER;
    
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
    
    // ========== STATIONS (Deprecated - Use Station enum instead) ==========
    /**
     * @deprecated Use {@link Station#SAI_GON} instead
     */
    @Deprecated
    public static final String STATION_SAIGON = "Sài Gòn";
    
    /**
     * @deprecated Use {@link Station#PHAN_THIET} instead
     */
    @Deprecated
    public static final String STATION_PHAN_THIET = "Phan Thiết";
    
    /**
     * @deprecated Use {@link Station#NHA_TRANG} instead
     */
    @Deprecated
    public static final String STATION_NHA_TRANG = "Nha Trang";
    
    /**
     * @deprecated Use {@link Station#HUE} instead
     */
    @Deprecated
    public static final String STATION_HUE = "Huế";
    
    /**
     * @deprecated Use {@link Station#DA_NANG} instead
     */
    @Deprecated
    public static final String STATION_DA_NANG = "Đà Nẵng";
    
    /**
     * @deprecated Use {@link Station#QUANG_NGAI} instead
     */
    @Deprecated
    public static final String STATION_QUANG_NGAI = "Quảng Ngãi";
    
    // ========== SEAT TYPES (Deprecated - Use SeatType enum instead) ==========
    /**
     * @deprecated Use {@link SeatType#HARD_SEAT} instead
     */
    @Deprecated
    public static final String SEAT_TYPE_HARD_SEAT = "Hard seat";
    
    /**
     * @deprecated Use {@link SeatType#SOFT_SEAT} instead
     */
    @Deprecated
    public static final String SEAT_TYPE_SOFT_SEAT = "Soft seat";
    
    /**
     * @deprecated Use {@link SeatType#SOFT_BED} instead
     */
    @Deprecated
    public static final String SEAT_TYPE_SOFT_BED = "Soft bed";
    
    /**
     * @deprecated Use {@link SeatType#HARD_BED} instead
     */
    @Deprecated
    public static final String SEAT_TYPE_HARD_BED = "Hard bed";
    
    /**
     * @deprecated Use {@link SeatType#SOFT_BED_AC} instead
     */
    @Deprecated
    public static final String SEAT_TYPE_SOFT_BED_AC = "Soft bed with air conditioner";
    
    /**
     * @deprecated Use {@link SeatType#HARD_BED_AC} instead (Note: This constant was unused)
     */
    @Deprecated
    public static final String SEAT_TYPE_HARD_BED_AC = "Hard bed with air conditioner";
    
    // ========== PRE-CONFIGURED USERS ==========
    public static final User VALID_USER = new User(USERNAME, PASSWORD);
    public static final User BLANK_USERNAME_USER = new User("", PASSWORD);
    public static final User INVALID_PASSWORD_USER = new User(USERNAME, INVALID_PASSWORD);
    public static final User WRONG_PASSWORD_USER = new User(USERNAME, "wrongPassword123");
    public static final User INACTIVE_USER = new User("inactive@gmail.com", "123456789");
    
    // ========== DATA CLASSES ==========
    
    /**
     * User credentials data class
     */
    public static class User {
        private final String username;
        private final String password;
        
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
    
    /**
     * Registration data class
     */
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
    
    /**
     * Booking data class
     * @deprecated Consider using individual parameters with Station and SeatType enums instead
     */
    @Deprecated
    public static class BookingData {
        private final String date;
        private final String departStation;
        private final String arriveStation;
        private final String seatType;
        private final String amount;
        
        public BookingData(String date, String departStation, String arriveStation, 
                          String seatType, String amount) {
            this.date = date;
            this.departStation = departStation;
            this.arriveStation = arriveStation;
            this.seatType = seatType;
            this.amount = amount;
        }
        
        public String getDate() {
            return date;
        }
        
        public String getDepartStation() {
            return departStation;
        }
        
        public String getArriveStation() {
            return arriveStation;
        }
        
        public String getSeatType() {
            return seatType;
        }
        
        public String getAmount() {
            return amount;
        }
    }
}