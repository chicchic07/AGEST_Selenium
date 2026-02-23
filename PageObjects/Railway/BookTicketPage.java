package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;

import Constant.Stations;
import Constant.DepartDate;
import Constant.SeatType;

public class BookTicketPage extends GeneralPage {

    public BookTicketPage(WebDriver driver) {
        super(driver);
    }

    // ========== LOCATORS - Consistent naming ==========
    private static final By DDL_DATE = By.name("Date");
    private static final By DDL_DEPART_FROM = By.name("DepartStation");
    private static final By DDL_ARRIVE_AT = By.name("ArriveStation");
    private static final By DDL_SEAT_TYPE = By.name("SeatType");
    private static final By DDL_TICKET_AMOUNT = By.name("TicketAmount");
    private static final By BTN_BOOK_TICKET = By.xpath("//input[@type='submit' and @value='Book ticket']");
    private static final By LBL_SUCCESS_TITLE = By.xpath("//h1[text()='Ticket booked successfully!']");
    private static final By TBL_BOOKED_ROW = By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow']");
    
    // Helper method for cell locators
    private By cellByIndex(int index) {
        return By.xpath("(//table[@class='MyTable WideTable']//tr[@class='OddRow']/td)[" + index + "]");
    }
    
    // ========== ELEMENT GETTERS ==========
    
    public WebElement getSelectDate() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(DDL_DATE));
    }

    public WebElement getSelectDepartFrom() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(DDL_DEPART_FROM));
    }

    public WebElement getSelectArriveAt() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(DDL_ARRIVE_AT));
    }

    public WebElement getSelectSeatType() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(DDL_SEAT_TYPE));
    }

    public WebElement getSelectTicketAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(DDL_TICKET_AMOUNT));
    }

    public WebElement getBtnBookTicket() {
        return wait.until(ExpectedConditions.elementToBeClickable(BTN_BOOK_TICKET));
    }

    // ========== METHODS WITH ENUM SUPPORT ==========
    
    /**
     * Select departure station using enum
     * @param station - Station enum value
     * @return BookTicketPage
     */
    public BookTicketPage selectDepartStation(Stations station) {
        Select stationDropdown = new Select(getSelectDepartFrom());
        stationDropdown.selectByVisibleText(station.getDisplayName());
        System.out.println("  Selected depart station: " + station.getDisplayName());
        return this;
    }
    
    /**
     * Select arrival station using enum
     * @param station - Station enum value
     * @return BookTicketPage
     */
    public BookTicketPage selectArriveStation(Stations station) {
        // Wait for the dropdown to be enabled and updated by the server
        try {
            WebElement initialElement = getSelectArriveAt();
            wait.until(ExpectedConditions.stalenessOf(initialElement));
        } catch (Exception e) {
            // Element might already be stale, continue
        }
        
        wait.until(ExpectedConditions.elementToBeClickable(DDL_ARRIVE_AT));
        
        wait.until(driver -> {
            try {
                Select select = new Select(getSelectArriveAt());
                List<WebElement> options = select.getOptions();
                return options.size() > 1;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
        
        wait.until(driver -> {
            try {
                Select select = new Select(getSelectArriveAt());
                for (WebElement option : select.getOptions()) {
                    if (option.getText().equals(station.getDisplayName())) {
                        return true;
                    }
                }
                return false;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Select stationDropdown = new Select(getSelectArriveAt());
        stationDropdown.selectByVisibleText(station.getDisplayName());
        System.out.println("  Selected arrive station: " + station.getDisplayName());
        return this;
    }
    
    /**
     * Select seat type using enum
     * @param seatType - SeatType enum value
     * @return BookTicketPage
     */
    public BookTicketPage selectSeatType(SeatType seatType) {
        Select seatTypeDropdown = new Select(getSelectSeatType());
        seatTypeDropdown.selectByVisibleText(seatType.getDisplayName());
        System.out.println("  Selected seat type: " + seatType.getDisplayName());
        return this;
    }

    // ========== BACKWARD COMPATIBLE STRING METHODS ==========
    
    /**
     * @deprecated Use {@link #selectDepartStation(Stations)} instead
     */
    @Deprecated
    public BookTicketPage selectDepartStation(String station) {
        Select stationDropdown = new Select(getSelectDepartFrom());
        stationDropdown.selectByVisibleText(station);
        System.out.println("  Selected depart station: " + station);
        return this;
    }

    /**
     * @deprecated Use {@link #selectArriveStation(Stations)} instead
     */
    @Deprecated
    public BookTicketPage selectArriveStation(String station) {
        try {
            WebElement initialElement = getSelectArriveAt();
            wait.until(ExpectedConditions.stalenessOf(initialElement));
        } catch (Exception e) {
        }
        
        wait.until(ExpectedConditions.elementToBeClickable(DDL_ARRIVE_AT));
        
        wait.until(driver -> {
            try {
                Select select = new Select(getSelectArriveAt());
                List<WebElement> options = select.getOptions();
                return options.size() > 1;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
        
        wait.until(driver -> {
            try {
                Select select = new Select(getSelectArriveAt());
                for (WebElement option : select.getOptions()) {
                    if (option.getText().equals(station)) {
                        return true;
                    }
                }
                return false;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Select stationDropdown = new Select(getSelectArriveAt());
        stationDropdown.selectByVisibleText(station);
        System.out.println("  Selected arrive station: " + station);
        return this;
    }

    /**
     * @deprecated Use {@link #selectSeatType(SeatType)} instead
     */
    @Deprecated
    public BookTicketPage selectSeatType(String seatType) {
        Select seatTypeDropdown = new Select(getSelectSeatType());
        seatTypeDropdown.selectByVisibleText(seatType);
        System.out.println("  Selected seat type: " + seatType);
        return this;
    }

    /**
     * @deprecated Use {@link #selectDepartDateByIndex(int)} instead
     */
    @Deprecated
    public BookTicketPage selectDepartDate(String date) {
        Select dateDropdown = new Select(getSelectDate());
        dateDropdown.selectByVisibleText(date);
        System.out.println("  Selected depart date: " + date);
        return this;
    }

    public BookTicketPage selectDepartDateByIndex(int daysFromFirstAvailable) {
        Select dateDropdown = new Select(getSelectDate());
        List<WebElement> availableOptions = dateDropdown.getOptions();
        
        System.out.println("  Available dates in dropdown:");
        for (int i = 0; i < availableOptions.size(); i++) {
            System.out.println("    [" + i + "] " + availableOptions.get(i).getText());
        }
        
        if (availableOptions.isEmpty()) {
            throw new RuntimeException("No dates available in dropdown");
        }
        
        String firstDateStr = availableOptions.get(0).getText().trim();
        System.out.println("  First date in dropdown: " + firstDateStr);
        
        LocalDate firstDate = null;
        String[] formats = {"M/d/yyyy", "MM/dd/yyyy", "M/dd/yyyy", "MM/d/yyyy"};
        
        for (String format : formats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                firstDate = LocalDate.parse(firstDateStr, formatter);
                System.out.println("  Successfully parsed first date with format: " + format);
                break;
            } catch (Exception e) {
                continue;
            }
        }
        
        if (firstDate == null) {
            throw new RuntimeException("Could not parse first date: " + firstDateStr);
        }
        
        LocalDate targetDate = firstDate.plusDays(daysFromFirstAvailable);
        System.out.println("  Target date (first date + " + daysFromFirstAvailable + " days): " + targetDate);
        
        for (String format : formats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                String formattedDate = targetDate.format(formatter);
                
                boolean found = false;
                for (WebElement option : availableOptions) {
                    if (option.getText().trim().equals(formattedDate)) {
                        found = true;
                        break;
                    }
                }
                
                if (found) {
                    dateDropdown.selectByVisibleText(formattedDate);
                    System.out.println("  ✓ Selected depart date: " + formattedDate + " (format: " + format + ")");
                    return this;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        System.err.println("  ERROR: Could not find target date in dropdown");
        throw new RuntimeException("Could not find date '" + targetDate + "' in dropdown");
    }

    // ========== TICKET AMOUNT METHODS ==========
    
    public BookTicketPage selectTicketAmount(String amount) {
        Select amountDropdown = new Select(getSelectTicketAmount());
        amountDropdown.selectByVisibleText(amount);
        System.out.println("  Selected ticket amount: " + amount);
        return this;
    }

    public BookTicketPage selectTicketAmount(int amount) {
        Select amountDropdown = new Select(getSelectTicketAmount());
        amountDropdown.selectByVisibleText(String.valueOf(amount));
        System.out.println("  Selected ticket amount: " + amount);
        return this;
    }

    public BookTicketPage clickBookTicketButton() {
        getBtnBookTicket().click();
        System.out.println("  Clicked 'Book ticket' button");
        return this;
    }

    // ========== COMPLETE BOOKING METHODS WITH ENUM SUPPORT ==========
    
    public BookTicketPage bookTicket(int daysFromFirstAvailable, Stations departStation, 
                                     Stations arriveStation, SeatType seatType, int amount) {
        selectDepartDateByIndex(daysFromFirstAvailable);
        selectDepartStation(departStation);
        selectArriveStation(arriveStation);
        selectSeatType(seatType);
        selectTicketAmount(amount);
        clickBookTicketButton();
        return this;
    }

    @Deprecated
    public BookTicketPage bookTicket(String date, String departStation, String arriveStation, 
                                     String seatType, String amount) {
        selectDepartDate(date);
        selectDepartStation(departStation);
        selectArriveStation(arriveStation);
        selectSeatType(seatType);
        selectTicketAmount(amount);
        clickBookTicketButton();
        return this;
    }

    @Deprecated
    public BookTicketPage bookTicket(int daysFromToday, String departStation, String arriveStation, 
                                     String seatType, int amount) {
        selectDepartDateByIndex(daysFromToday);
        selectDepartStation(departStation);
        selectArriveStation(arriveStation);
        selectSeatType(seatType);
        selectTicketAmount(amount);
        clickBookTicketButton();
        return this;
    }

    // ========== VERIFICATION METHODS ==========
    
    public boolean isTicketBookedSuccessfully() {
        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(LBL_SUCCESS_TITLE))
                .isDisplayed();
    }

    public String getBookedDepartStation() {
        return driver.findElement(cellByIndex(1)).getText();
    }

    public String getBookedArriveStation() {
        return driver.findElement(cellByIndex(2)).getText();
    }

    public String getBookedSeatType() {
        return driver.findElement(cellByIndex(3)).getText();
    }

    public String getBookedDepartDate() {
        return driver.findElement(cellByIndex(4)).getText();
    }

    public String getBookedAmount() {
        return driver.findElement(cellByIndex(7)).getText();
    }
    
    // ========== BOOKING HELPER METHODS ==========
    
    public static BookTicketPage bookTicket(BookTicketPage bookTicketPage, int daysFromFirstAvailable,
            Stations departStation, Stations arriveStation, SeatType seatType, int amount) {

        System.out.println("  Booking ticket:");
        System.out.println("    Route: " + departStation.getDisplayName() + " → " + arriveStation.getDisplayName());
        System.out.println("    Seat: " + seatType.getDisplayName());
        System.out.println("    Amount: " + amount);
        System.out.println("    Days from first available: " + daysFromFirstAvailable);

        return bookTicketPage.bookTicket(daysFromFirstAvailable, departStation, arriveStation, seatType, amount);
    }

    public static BookTicketPage bookSimpleTicket(BookTicketPage bookTicketPage, Stations departStation, Stations arriveStation) {
        return bookTicket(bookTicketPage, DepartDate.FIRST_AVAILABLE, departStation, arriveStation, SeatType.HARD_SEAT, 1);
    }

    public static BookTicketPage bookTicketForTomorrow(BookTicketPage bookTicketPage, Stations departStation,
                       Stations arriveStation, SeatType seatType, int amount) {
        return bookTicket(bookTicketPage, DepartDate.TOMORROW, departStation, arriveStation, seatType, amount);
    }
    
    public static BookTicketPage bookTicketTwoDaysLater(BookTicketPage bookTicketPage,
            Stations departStation, Stations arriveStation, SeatType seatType, int amount) {
        return bookTicket(bookTicketPage, DepartDate.DAY_AFTER_TOMORROW, departStation, arriveStation, seatType, amount);
    }
    
    public static BookTicketPage bookTicket25DaysLater(BookTicketPage bookTicketPage, Stations departStation,
            Stations arriveStation, SeatType seatType, int amount) {
        return bookTicket(bookTicketPage, DepartDate.TWENTYFIVE_DAYS_LATER, departStation, arriveStation, seatType, amount);
    }

    // ========== HELPER METHODS ==========
    
    public List<WebElement> getAvailableDates() {
        Select dateDropdown = new Select(getSelectDate());
        return dateDropdown.getOptions();
    }

    public List<WebElement> getAvailableDepartStations() {
        Select stationDropdown = new Select(getSelectDepartFrom());
        return stationDropdown.getOptions();
    }

    public List<WebElement> getAvailableArriveStations() {
        Select stationDropdown = new Select(getSelectArriveAt());
        return stationDropdown.getOptions();
    }

    public List<WebElement> getAvailableSeatTypes() {
        Select seatTypeDropdown = new Select(getSelectSeatType());
        return seatTypeDropdown.getOptions();
    }
}