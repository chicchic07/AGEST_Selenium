package Constant;

public enum NavigationTab {
    LOGIN("Login"),
    LOGOUT("Logout"),
    REGISTER("Register"),
    BOOK_TICKET("Book ticket"),
    FAQ("FAQ"),
    TIMETABLE("Timetable"),
    MY_TICKET("My ticket");
    
    private final String tabName;
    
    NavigationTab(String tabName) {
        this.tabName = tabName;
    }
    
    public String getTabName() {
        return tabName;
    }
}