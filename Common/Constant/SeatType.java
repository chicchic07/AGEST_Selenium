package Constant;

public enum SeatType {
    HARD_SEAT("Hard seat", "HS"),
    SOFT_SEAT("Soft seat", "SS"),
    SOFT_SEAT_AC("Soft seat with air conditioner", "SSC"),
    HARD_BED("Hard bed", "HB"),
    SOFT_BED("Soft bed", "SB"),
    SOFT_BED_AC("Soft bed with air conditioner", "SBC");
    
    private final String displayName;
    private final String abbreviation;
    
    SeatType(String displayName, String abbreviation) {
        this.displayName = displayName;
        this.abbreviation = abbreviation;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getAbbreviation() {
        return abbreviation;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}