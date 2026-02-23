package Constant;

public enum SeatPrice {
    // Sài Gòn to Phan Thiết
    SAI_GON_TO_PHAN_THIET(
        Stations.SAI_GON, 
        Stations.PHAN_THIET,
        "90000", "115000", "140000", "190000", "240000", "290000"
    ),
    
    // Sài Gòn to Nha Trang
    SAI_GON_TO_NHA_TRANG(
        Stations.SAI_GON,
        Stations.NHA_TRANG,
        "220000", "245000", "270000", "320000", "370000", "420000"
    ),
    
    // Sài Gòn to Đà Nẵng
    SAI_GON_TO_DA_NANG(
            Stations.SAI_GON,
            Stations.DA_NANG,
            "310000", "335000", "360000", "320000", "410000", "510000"
        ),
    
    // Sài Gòn to Huế
    SAI_GON_TO_HUE(
            Stations.SAI_GON,
            Stations.HUE,
            "460000", "485000", "510000", "560000", "610000", "660000"
        ),
    
    // Sài Gòn to Quảng Ngãi
    SAI_GON_TO_QUANG_NGAI(
            Stations.SAI_GON,
            Stations.QUANG_NGAI,
            "600000", "625000", "650000", "700000", "750000", "800000"
        ),
    
    // Phan Thiết to Sài Gòn
    PHAN_THIET_TO_SAI_GON(
            Stations.PHAN_THIET,
            Stations.SAI_GON,
            "90000", "115000", "140000", "190000", "240000", "290000"
        ),
    
    // Phan Thiết to Nha Trang
    PHAN_THIET_TO_NHA_TRANG(
            Stations.PHAN_THIET,
            Stations.NHA_TRANG,
            "100000", "125000", "150000", "200000", "250000", "300000"
        ),
    
    // Phan Thiết to Đà Nẵng
    PHAN_THIET_TO_DA_NANG(
            Stations.PHAN_THIET,
            Stations.DA_NANG,
            "240000", "265000", "290000", "340000", "390000", "440000"
        ),
    
    // Nha Trang to Sài Gòn
    NHA_TRANG_TO_SAI_GON(
            Stations.NHA_TRANG,
            Stations.SAI_GON,
            "220000", "245000",	"270000", "320000",	"370000", "420000"
        ),
    
    // Nha Trang to Phan Thiết
    NHA_TRANG_TO_PAHN_THIET(
            Stations.NHA_TRANG,
            Stations.PHAN_THIET,
            "100000", "125000", "150000", "200000",	"250000", "300000"
        ),
    
    // Nha Trang to Đà Nẵng
    NHA_TRANG_TO_DA_NANG(
            Stations.NHA_TRANG,
            Stations.DA_NANG,
            "110000", "135000",	"160000", "210000",	"260000", "310000"
        ),
    
    // Nha Trang to Huế
    NHA_TRANG_TO_HUE(
            Stations.NHA_TRANG,
            Stations.HUE,
            "240000", "265000",	"290000", "340000",	"390000", "440000"
        ),
    
    // Đà Nẵng to Sài Gòn
    DA_NANG_TO_SAI_GON(
            Stations.DA_NANG,
            Stations.SAI_GON,
            "310000", "335000",	"360000", "410000",	"460000", "510000"
        ),
    
    // Đà Nẵng to Nha Trang
    DA_NANG_TO_NHA_TRANG(
            Stations.DA_NANG,
            Stations.NHA_TRANG,
            "110000", "135000", "160000", "210000", "260000", "310000"

        ),
    
    // Đà Nẵng to Huế
    DA_NANG_TO_HUE(
            Stations.DA_NANG,
            Stations.HUE,
            "150000", "175000", "200000", "250000", "300000", "350000"

        ),
    
    // Đà Nẵng to Quảng Ngãi
    DA_NANG_TO_QUANG_NGAI(
            Stations.DA_NANG,
            Stations.QUANG_NGAI,
            "300000", "325000", "350000", "400000", "450000", "500000"
        ),
    
    // Huế to Sài Gòn
    HUE_TO_SAI_GON(
            Stations.HUE,
            Stations.SAI_GON,
            "450000", "475000", "500000", "550000", "600000", "650000"
        ),
    
    // Huế to Nha Trang
    HUE_TO_NHA_TRANG(
            Stations.HUE,
            Stations.NHA_TRANG,
            "250000", "275000", "300000", "350000", "400000", "450000"
        ),
    
    // Huế to Đà Nẵng
    HUE_TO_DA_NANG(
            Stations.HUE,
            Stations.DA_NANG,
            "150000", "175000", "200000", "250000", "300000", "350000"
        ),
    
    // Huế to Quảng Ngãi
    HUE_TO_QUANG_NGAI(
            Stations.HUE,
            Stations.QUANG_NGAI,
            "140000", "165000", "190000", "240000", "290000", "340000"
        ),
    
    // Quảng Ngãi to Sài Gòn
    QUANG_NGAI_TO_SAI_GON(
            Stations.QUANG_NGAI,
            Stations.SAI_GON,
            "600000", "625000", "650000", "700000", "750000", "800000"
        ),
    
    // Quảng Ngãi to Nha Trang
    QUANG_NGAI_TO_NHA_TRANG(
            Stations.QUANG_NGAI,
            Stations.NHA_TRANG,
            "300000", "325000", "350000", "400000", "450000", "500000"
        ),
    
    // Quảng Ngãi to Đà Nẵng
    QUANG_NGAI_TO_DA_NANG(
            Stations.QUANG_NGAI,
            Stations.DA_NANG,
            "300000", "325000", "350000", "400000", "450000", "500000"
        ),
    
    // Quảng Ngãi to Huế
    QUANG_NGAI_TO_HUE(
            Stations.QUANG_NGAI,
            Stations.HUE,
            "150000", "175000", "200000", "250000", "300000", "350000"
        ),
    ;
    
    private final Stations departStation;
    private final Stations arriveStation;
    private final String hardSeat;
    private final String softSeat;
    private final String softSeatAC;
    private final String hardBed;
    private final String softBed;
    private final String softBedAC;
    
    SeatPrice(Stations departStation, Stations arriveStation,
              String hardSeat, String softSeat, String softSeatAC,
              String hardBed, String softBed, String softBedAC) {
        this.departStation = departStation;
        this.arriveStation = arriveStation;
        this.hardSeat = hardSeat;
        this.softSeat = softSeat;
        this.softSeatAC = softSeatAC;
        this.hardBed = hardBed;
        this.softBed = softBed;
        this.softBedAC = softBedAC;
    }
    
    // Getters
    public Stations getDepartStation() {
        return departStation;
    }
    
    public Stations getArriveStation() {
        return arriveStation;
    }
    
    public String getHardSeat() {
        return hardSeat;
    }
    
    public String getSoftSeat() {
        return softSeat;
    }
    
    public String getSoftSeatAC() {
        return softSeatAC;
    }
    
    public String getHardBed() {
        return hardBed;
    }
    
    public String getSoftBed() {
        return softBed;
    }
    
    public String getSoftBedAC() {
        return softBedAC;
    }
    
    public static SeatPrice findByRoute(Stations depart, Stations arrive) {
        for (SeatPrice price : values()) {
            if (price.departStation == depart && price.arriveStation == arrive) {
                return price;
            }
        }
        throw new IllegalArgumentException(
            "No price found for route: " + depart + " to " + arrive
        );
    }
}