package Constant;

public class DepartDate {
    
    public static final int FIRST_AVAILABLE = 0;
    
    public static final int TOMORROW = 1;
    
    public static final int DAY_AFTER_TOMORROW = 2;
    
    public static final int TWENTYFIVE_DAYS_LATER = 25;
    
    // Private constructor to prevent instantiation
    private DepartDate() {
        throw new AssertionError("Cannot instantiate DepartDate class");
    }
}