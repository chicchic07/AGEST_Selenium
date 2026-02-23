package Constant;

public enum Stations {
    SAI_GON("Sài Gòn"),
    PHAN_THIET("Phan Thiết"),
    NHA_TRANG("Nha Trang"),
    HUE("Huế"),
    DA_NANG("Đà Nẵng"),
    QUANG_NGAI("Quảng Ngãi");
    
    private final String displayName;
    
    Stations(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}