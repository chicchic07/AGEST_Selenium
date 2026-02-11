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

public class BookTicketPage extends GeneralPage {

    public BookTicketPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By selectDate = By.name("Date");
    private final By selectDepartFrom = By.name("DepartStation");
    private final By selectArriveAt = By.name("ArriveStation");
    private final By selectSeatType = By.name("SeatType");
    private final By selectTicketAmount = By.name("TicketAmount");
    private final By btnBookTicket = By.xpath("//input[@type='submit' and @value='Book ticket']");
    private final By lblSuccessTitle = By.xpath("//h1[text()='Ticket booked successfully!']");
    
    private final By bookedRow = By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow']");

    
    // Elements
    public WebElement getSelectDate() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selectDate));
    }

    public WebElement getSelectDepartFrom() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selectDepartFrom));
    }

    public WebElement getSelectArriveAt() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selectArriveAt));
    }

    public WebElement getSelectSeatType() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selectSeatType));
    }

    public WebElement getSelectTicketAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selectTicketAmount));
    }

    public WebElement getBtnBookTicket() {
        return wait.until(ExpectedConditions.elementToBeClickable(btnBookTicket));
    }

    private By cellByIndex(int index) {
        return By.xpath("(//table[@class='MyTable WideTable']//tr[@class='OddRow']/td)[" + index + "]");
    }

    // Methods for selecting options
    public BookTicketPage selectDepartDate(String date) {
        Select dateDropdown = new Select(getSelectDate());
        dateDropdown.selectByVisibleText(date);
        System.out.println("  Selected depart date: " + date);
        return this;
    }

    public BookTicketPage selectDepartDateByIndex(int daysFromToday) {
        Select dateDropdown = new Select(getSelectDate());
        List<WebElement> availableOptions = dateDropdown.getOptions();
        
        // Print available dates for debugging
        System.out.println("  Available dates in dropdown:");
        for (int i = 0; i < availableOptions.size(); i++) {
            System.out.println("    [" + i + "] " + availableOptions.get(i).getText());
        }
        
        if (availableOptions.isEmpty()) {
            throw new RuntimeException("No dates available in dropdown");
        }
        
        // Get the first date from dropdown
        String firstDateStr = availableOptions.get(0).getText().trim();
        System.out.println("  First date in dropdown: " + firstDateStr);
        
        // Parse the first date - try different formats
        LocalDate firstDate = null;
        String[] formats = {"M/d/yyyy", "MM/dd/yyyy", "M/dd/yyyy", "MM/d/yyyy"};
        
        for (String format : formats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                firstDate = LocalDate.parse(firstDateStr, formatter);
                System.out.println("  Successfully parsed first date with format: " + format);
                break;
            } catch (Exception e) {
                // Try next format
                continue;
            }
        }
        
        if (firstDate == null) {
            throw new RuntimeException("Could not parse first date: " + firstDateStr);
        }
        
        // Add the required number of days to the first date
        LocalDate targetDate = firstDate.plusDays(daysFromToday);
        System.out.println("  Target date (first date + " + daysFromToday + " days): " + targetDate);
        
        // Try to select the target date with different formats
        for (String format : formats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                String formattedDate = targetDate.format(formatter);
                
                // Check if this date exists in the dropdown
                boolean found = false;
                for (WebElement option : availableOptions) {
                    if (option.getText().trim().equals(formattedDate)) {
                        found = true;
                        break;
                    }
                }
                
                if (found) {
                    dateDropdown.selectByVisibleText(formattedDate);
                    System.out.println("    Selected depart date: " + formattedDate + " (format: " + format + ")");
                    return this;
                }
            } catch (Exception e) {
                // Try next format
                continue;
            }
        }
        
        // If we couldn't find the exact date, throw an error with helpful info
        System.err.println("  ERROR: Could not find target date in dropdown");
        System.err.println("  First date: " + firstDate);
        System.err.println("  Days to add: " + daysFromToday);
        System.err.println("  Target date: " + targetDate);
        throw new RuntimeException("Could not find date '" + targetDate + "' in dropdown. Available dates: " + 
                                   availableOptions.stream()
                                                   .map(WebElement::getText)
                                                   .reduce("", (a, b) -> a + ", " + b));
    }

    public BookTicketPage selectDepartStation(String station) {
        Select stationDropdown = new Select(getSelectDepartFrom());
        stationDropdown.selectByVisibleText(station);
        System.out.println("  Selected depart station: " + station);
        return this;
    }

    public BookTicketPage selectArriveStation(String station) {
        // Wait for the dropdown to be enabled and updated by the server
        // After selecting depart station, the arrive station dropdown is refreshed
        
        // Step 1: Wait for element to be stale (old element reference becomes invalid)
        try {
            WebElement initialElement = getSelectArriveAt();
            wait.until(ExpectedConditions.stalenessOf(initialElement));
        } catch (Exception e) {
            // Element might already be stale, continue
        }
        
        // Step 2: Wait for new element to be present and clickable
        wait.until(ExpectedConditions.elementToBeClickable(selectArriveAt));
        
        // Step 3: Wait for dropdown to be updated (not showing default "Sài Gòn" anymore)
        wait.until(driver -> {
            try {
                Select select = new Select(getSelectArriveAt());
                List<WebElement> options = select.getOptions();
                // Make sure we have more than just the placeholder option
                return options.size() > 1;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
        
        // Step 4: Additional wait to ensure the target station is available in the list
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
        
        // Step 5: Small additional delay to ensure stability
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Step 6: Now select the station
        Select stationDropdown = new Select(getSelectArriveAt());
        stationDropdown.selectByVisibleText(station);

        System.out.println("  Selected arrive station: " + station);
        return this;
    }

    public BookTicketPage selectSeatType(String seatType) {
        Select seatTypeDropdown = new Select(getSelectSeatType());
        seatTypeDropdown.selectByVisibleText(seatType);
        System.out.println("  Selected seat type: " + seatType);
        return this;
    }

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

    // Complete booking method
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

    // Booking with days from today
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

    // Verification methods
    public boolean isTicketBookedSuccessfully() {
        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(lblSuccessTitle))
                .isDisplayed();
    }

    // Methods to get booked ticket information
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


    // Helper method to get all available dates
    public List<WebElement> getAvailableDates() {
        Select dateDropdown = new Select(getSelectDate());
        return dateDropdown.getOptions();
    }

    // Helper method to get all available stations
    public List<WebElement> getAvailableDepartStations() {
        Select stationDropdown = new Select(getSelectDepartFrom());
        return stationDropdown.getOptions();
    }

    public List<WebElement> getAvailableArriveStations() {
        Select stationDropdown = new Select(getSelectArriveAt());
        return stationDropdown.getOptions();
    }

    // Helper method to get all available seat types
    public List<WebElement> getAvailableSeatTypes() {
        Select seatTypeDropdown = new Select(getSelectSeatType());
        return seatTypeDropdown.getOptions();
    }
}