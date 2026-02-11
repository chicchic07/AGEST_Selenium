package Constant;

public enum Stations {
	SAI_GON("//option[text()='Sài Gòn']"),
    PHAN_THIET("//option[text()='Phan Thiết']"),
    NHA_TRANG("//option[text()='Nha Trang']"),
    HUE("//option[text()='Huế']"),
    DA_NANG("//option[text()='Đà Nẵng']"),
    QUANG_NGAI("//option[text()='Quảng Ngãi']");
    
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
