package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class TimeTablePage extends GeneralPage {

    public TimeTablePage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By pageTitle = By.xpath("//h1[contains(text(), 'Ticket Price')]");
    private final By routeInfo = By.xpath("//tr[contains(@class, 'TableSmallHeader')]");
    
    private final By tableRows = By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow' or @class='EvenRow']");
    private final By priceTable = By.xpath("//table[@class='MyTable WideTable']");

    // Elements
    public WebElement getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }
    
    public WebElement getRouteInfo() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(routeInfo));
    }

    public WebElement getPriceTable() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(priceTable));
    }
    
    public List<WebElement> getTableRows() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));
    }

    // Methods
    public BookTicketPage clickBookTicket(String fromStation, String toStation) {
        System.out.println("  Looking for route: " + fromStation + " → " + toStation);
        
        List<WebElement> rows = getTableRows();
        boolean found = false;
        
        for (WebElement row : rows) {
            try {
                // Get all cells in the row
                List<WebElement> cells = row.findElements(By.tagName("td"));
                
                if (cells.size() < 7) continue; // Skip if not enough columns
                
                // Column index: 1=Depart Station, 2=Arrive Station, 6=book ticket link
                String departStation = cells.get(1).getText().trim();
                String arriveStation = cells.get(2).getText().trim();
                
                System.out.println("    Checking row: " + departStation + " → " + arriveStation);
                
                // Check if this is the correct route
                if (departStation.equals(fromStation) && arriveStation.equals(toStation)) {
                    System.out.println("      Found matching route!");
                    
                    // Scroll the row into view first
                    System.out.println("    Scrolling row into view...");
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", row);
                    
                    // Wait a bit for scroll to complete
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    // Find the "book ticket" link in this row
                    WebElement bookTicketLink = cells.get(6).findElement(By.tagName("a"));
                    
                    // Verify it's the book ticket link
                    if (bookTicketLink.getText().contains("book ticket")) {
                        System.out.println("    Clicking 'book ticket' link...");
                        
                        // Make sure the link is clickable
                        wait.until(ExpectedConditions.elementToBeClickable(bookTicketLink));
                        bookTicketLink.click();
                        
                        found = true;
                        
                        // Wait for book ticket page to load
                        System.out.println("      Book ticket page loaded");
                        break;
                    }
                }
            } catch (Exception e) {
                // Continue to next row if there's an issue
                System.out.println("    Error processing row: " + e.getMessage());
                continue;
            }
        }
        
        if (!found) {
            throw new RuntimeException("Could not find route from '" + fromStation + "' to '" + toStation + "'");
        }
        
        return new BookTicketPage(driver);
    }
    
    public TimeTablePage clickCheckPrice(String fromStation, String toStation) {
        System.out.println("  Looking for route: " + fromStation + " → " + toStation);
        
        List<WebElement> rows = getTableRows();
        boolean found = false;
        
        for (WebElement row : rows) {
            try {
                // Get all cells in the row
                List<WebElement> cells = row.findElements(By.tagName("td"));
                
                if (cells.size() < 6) continue; // Skip if not enough columns
                
                // Column index: 1=Depart Station, 2=Arrive Station, 5=check price link
                String departStation = cells.get(1).getText().trim();
                String arriveStation = cells.get(2).getText().trim();
                
                System.out.println("    Checking row: " + departStation + " → " + arriveStation);
                
                // Check if this is the correct route
                if (departStation.equals(fromStation) && arriveStation.equals(toStation)) {
                    System.out.println("      Found matching route!");
                    
                    // Scroll the row into view first
                    System.out.println("    Scrolling row into view...");
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", row);
                    
                    // Wait a bit for scroll to complete
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    // Find the "check price" link in this row
                    WebElement checkPriceLink = cells.get(5).findElement(By.tagName("a"));
                    
                    // Verify it's the check price link
                    if (checkPriceLink.getText().contains("check price")) {
                        System.out.println("    Clicking 'check price' link...");
                        
                        // Make sure the link is clickable
                        wait.until(ExpectedConditions.elementToBeClickable(checkPriceLink));
                        checkPriceLink.click();
                        
                        found = true;
                        
                        // Wait for price page to load
                        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
                        System.out.println("      Price page loaded");
                        break;
                    }
                }
            } catch (Exception e) {
                // Continue to next row if there's an issue
                System.out.println("    Error processing row: " + e.getMessage());
                continue;
            }
        }
        
        if (!found) {
            throw new RuntimeException("Could not find route from '" + fromStation + "' to '" + toStation + "'");
        }
        
        return this;
    }

    public boolean isTicketPricePageLoaded() {
        try {
            return getPageTitle().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitleText() {
        return getPageTitle().getText();
    }
    
    public String getRouteInfoText() {
        return getRouteInfo().getText();
    }

    public boolean isRouteDisplayed(String fromStation, String toStation) {
        String actualRouteInfo = getRouteInfoText();
        
        System.out.println("  Debug - Actual route info: '" + actualRouteInfo + "'");
        
        // Check various possible formats
        boolean contains1 = actualRouteInfo.contains("Ticket price from " + fromStation + " to " + toStation);
        boolean contains2 = actualRouteInfo.contains(fromStation + " to " + toStation);
        boolean contains3 = actualRouteInfo.contains(fromStation) && actualRouteInfo.contains(toStation);
        
        System.out.println("  Debug - Contains 'Ticket price from X to Y': " + contains1);
        System.out.println("  Debug - Contains 'X to Y': " + contains2);
        System.out.println("  Debug - Contains both station names: " + contains3);
        
        return contains1 || contains2 || contains3;
    }

    public String getSeatTypePrice(String seatType) {
        // Map full seat type names to abbreviations in the table
        String abbreviation;
        switch (seatType) {
            case "Hard seat":
                abbreviation = "HS";
                break;
            case "Soft seat":
                abbreviation = "SS";
                break;
            case "Soft seat with air conditioner":
                abbreviation = "SSC";
                break;
            case "Hard bed":
                abbreviation = "HB";
                break;
            case "Soft bed":
                abbreviation = "SB";
                break;
            case "Soft bed with air conditioner":
                abbreviation = "SBC";
                break;
            default:
                throw new IllegalArgumentException("Unknown seat type: " + seatType);
        }
        
        try {
            // Find the seat type header row
            WebElement seatTypeRow = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//th[@class='RowHeader' and contains(text(), 'Seat type')]/..")
            ));
            
            // Get all seat type cells in this row
            List<WebElement> seatTypeCells = seatTypeRow.findElements(By.tagName("td"));
            
            // Find the column index for the seat type
            int columnIndex = -1;
            for (int i = 0; i < seatTypeCells.size(); i++) {
                if (seatTypeCells.get(i).getText().trim().equals(abbreviation)) {
                    columnIndex = i;
                    break;
                }
            }
            
            if (columnIndex == -1) {
                throw new RuntimeException("Could not find column for seat type: " + abbreviation);
            }
            
            // Find the price row
            WebElement priceRow = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//th[@class='RowHeader' and contains(text(), 'Price')]/..")
            ));
            
            // Get the price at the same column index
            List<WebElement> priceCells = priceRow.findElements(By.tagName("td"));
            String price = priceCells.get(columnIndex).getText().trim();
            
            return price;
            
        } catch (Exception e) {
            System.err.println("Error getting price for " + seatType + ": " + e.getMessage());
            throw e;
        }
    }

    // Method to verify all seat prices for a route
    public boolean verifySeatPrices(String hardSeat, String softSeat, String softSeatAC, 
                                     String hardBed, String softBed, String softBedAC) {
        try {
            String actualHS = getSeatTypePrice("Hard seat");
            String actualSS = getSeatTypePrice("Soft seat");
            String actualSSC = getSeatTypePrice("Soft seat with air conditioner");
            String actualHB = getSeatTypePrice("Hard bed");
            String actualSB = getSeatTypePrice("Soft bed");
            String actualSBC = getSeatTypePrice("Soft bed with air conditioner");

            System.out.println("\n  Actual Prices:");
            System.out.println("    Hard seat (HS): " + actualHS + " (Expected: " + hardSeat + ")");
            System.out.println("    Soft seat (SS): " + actualSS + " (Expected: " + softSeat + ")");
            System.out.println("    Soft seat with AC (SSC): " + actualSSC + " (Expected: " + softSeatAC + ")");
            System.out.println("    Hard bed (HB): " + actualHB + " (Expected: " + hardBed + ")");
            System.out.println("    Soft bed (SB): " + actualSB + " (Expected: " + softBed + ")");
            System.out.println("    Soft bed with AC (SBC): " + actualSBC + " (Expected: " + softBedAC + ")");

            boolean hsMatch = actualHS.equals(hardSeat);
            boolean ssMatch = actualSS.equals(softSeat);
            boolean sscMatch = actualSSC.equals(softSeatAC);
            boolean hbMatch = actualHB.equals(hardBed);
            boolean sbMatch = actualSB.equals(softBed);
            boolean sbcMatch = actualSBC.equals(softBedAC);
            
            if (!hsMatch) System.out.println("     Hard seat price mismatch!");
            if (!ssMatch) System.out.println("     Soft seat price mismatch!");
            if (!sscMatch) System.out.println("    	Soft seat with AC price mismatch!");
            if (!hbMatch) System.out.println("     Hard bed price mismatch!");
            if (!sbMatch) System.out.println("     Soft bed price mismatch!");
            if (!sbcMatch) System.out.println("   	 Soft bed with AC price mismatch!");

            return hsMatch && ssMatch && sscMatch && hbMatch && sbMatch && sbcMatch;
        } catch (Exception e) {
            System.err.println("Error verifying seat prices: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}