package Common;

public class TestDataManager {
    private static String tc09CreatedEmail = null;
    private static String tc09Password = null;
    
    public static void setTC09Data(String email, String password) {
        tc09CreatedEmail = email;
        tc09Password = password;
        System.out.println("TC09 data stored - Email: " + email);
    }
    
    public static String getTC09Email() {
        return tc09CreatedEmail;
    }
    
    public static String getTC09Password() {
        return tc09Password;
    }
    
    public static boolean hasTC09Data() {
        return tc09CreatedEmail != null && tc09Password != null;
    }
    
    public static void clearTC09Data() {
        tc09CreatedEmail = null;
        tc09Password = null;
        System.out.println("TC09 data cleared");
    }
}