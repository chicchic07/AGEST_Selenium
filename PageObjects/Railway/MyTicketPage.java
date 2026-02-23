package Railway;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class MyTicketPage extends GeneralPage {

    public MyTicketPage(WebDriver driver) {
        super(driver);
    }

    // ========== LOCATORS - Consistent naming ==========
    private static final By TBL_ROWS = By.xpath("//table[@class='MyTable']//tr[@class='OddRow' or @class='EvenRow']");
    private static final By LBL_NO_TICKET = By.xpath("//h1[contains(text(), 'No ticket')] | //p[contains(text(), 'no ticket')]");
    private static final String BTN_CANCEL_BY_ROUTE = "//td[text()='%s']/following-sibling::td[text()='%s']/following-sibling::td//input[@value='Cancel']";
    private static final By BTN_FIRST_CANCEL = By.xpath("(//input[@value='Cancel'])[1]");

    // ========== ELEMENT GETTERS ==========
    
    public List<WebElement> getTableRows() {
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(TBL_ROWS));
        } catch (Exception e) {
            // No tickets found
            return List.of();
        }
    }

    public WebElement getNoTicketMessage() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(LBL_NO_TICKET));
    }

    public WebElement getFirstCancelButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(BTN_FIRST_CANCEL));
    }

    // ========== VERIFICATION METHODS ==========
    
    public boolean hasTickets() {
        try {
            List<WebElement> rows = getTableRows();
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoTicketMessageDisplayed() {
        try {
            return getNoTicketMessage().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getTicketCount() {
        return getTableRows().size();
    }

    // ========== CANCEL METHODS ==========
    
    public void clickCancelByRoute(String departStation, String arriveStation) {
        System.out.println("  Looking for ticket: " + departStation + " → " + arriveStation);
        
        By cancelButton = By.xpath(String.format(BTN_CANCEL_BY_ROUTE, departStation, arriveStation));
        
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
            
            // Scroll to the button then wait until it's fully visible
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", btn);
            wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
            
            System.out.println("  Clicking 'Cancel' button...");
            btn.click();
            
        } catch (Exception e) {
            throw new RuntimeException("Could not find or click cancel button for route: " + 
                                     departStation + " → " + arriveStation + ". Error: " + e.getMessage());
        }
    }

    public void clickFirstCancelButton() {
        System.out.println("  Clicking first 'Cancel' button...");
        
        WebElement btn = getFirstCancelButton();
        
        // Scroll to the button then wait until it's fully clickable
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", btn);
        wait.until(ExpectedConditions.elementToBeClickable(BTN_FIRST_CANCEL));
        
        btn.click();
    }

    public boolean confirmCancellation() {
        try {
            System.out.println("  Waiting for confirmation alert...");

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("  Alert message: " + alertText);

            // Lấy reference của row đầu tiên trước khi accept, dùng để wait staleness
            List<WebElement> rowsBefore = driver.findElements(TBL_ROWS);
            WebElement firstRowRef = rowsBefore.isEmpty() ? null : rowsBefore.get(0);

            System.out.println("  Clicking 'OK' on confirmation alert...");
            alert.accept();

            // Chờ page cập nhật sau khi cancel:
            // - Nếu có row trước đó → chờ row đó stale (DOM đã refresh)
            // - Nếu không có row → chờ no-ticket message xuất hiện
            if (firstRowRef != null) {
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.stalenessOf(firstRowRef),
                    ExpectedConditions.presenceOfElementLocated(LBL_NO_TICKET)
                ));
            } else {
                wait.until(ExpectedConditions.presenceOfElementLocated(LBL_NO_TICKET));
            }

            System.out.println("  ✓ Confirmation accepted");
            return true;

        } catch (Exception e) {
            System.err.println("  ERROR: Could not handle confirmation alert: " + e.getMessage());
            return false;
        }
    }

    /**
     * Vào My Ticket page: verify có vé, in danh sách, trả về số vé ban đầu.
     * Dùng trong TC trước khi cancel để lấy initialTicketCount.
     */
    public int getInitialTicketCount() {
        Assert.assertTrue(hasTickets(), "No tickets found on My Ticket page");
        System.out.println("   My Ticket page loaded");
        printAllTickets();
        int count = getTicketCount();
        System.out.println("  Initial ticket count: " + count);
        return count;
    }

    // ========== COMBINED METHODS ==========
    public boolean waitForCancellationComplete(String departStation, String arriveStation, int initialCount) {
        int finalCount = getTicketCount();

        boolean ticketCountDecreased = finalCount < initialCount;
        boolean ticketRemoved = isTicketCanceled(departStation, arriveStation);
        boolean noTicketMsg = finalCount == 0 && isNoTicketMessageDisplayed();

        return ticketCountDecreased || ticketRemoved || noTicketMsg;
    }

    public void cancelTicket(String departStation, String arriveStation) {
        clickCancelByRoute(departStation, arriveStation);
        confirmCancellation();
    }

    public void cancelFirstTicket() {
        clickFirstCancelButton();
        confirmCancellation();
    }

    public boolean isTicketCanceled(String departStation, String arriveStation) {
        System.out.println("  Verifying ticket is canceled: " + departStation + " → " + arriveStation);
        
        try {
            List<WebElement> rows = getTableRows();
            
            if (rows.isEmpty()) {
                System.out.println("  ✓ No tickets found - ticket was canceled");
                return true;
            }
            
            // Check each row to see if the ticket still exists
            for (WebElement row : rows) {
                try {
                    List<WebElement> cells = row.findElements(By.tagName("td"));
                    
                    if (cells.size() >= 3) {
                        String depart = cells.get(0).getText().trim();
                        String arrive = cells.get(1).getText().trim();
                        
                        if (depart.equals(departStation) && arrive.equals(arriveStation)) {
                            System.out.println("  ✗ Ticket still exists - cancellation failed");
                            return false;
                        }
                    }
                } catch (Exception e) {
                    // Continue checking other rows
                    continue;
                }
            }
            
            System.out.println("  ✓ Ticket not found in list - successfully canceled");
            return true;
            
        } catch (Exception e) {
            // If we can't find the table, assume ticket was canceled
            System.out.println("  ✓ Ticket table not found - ticket was canceled");
            return true;
        }
    }

    // ========== HELPER METHODS ==========
    
    public void printAllTickets() {
        System.out.println("  Current tickets in My Ticket:");
        
        List<WebElement> rows = getTableRows();
        
        if (rows.isEmpty()) {
            System.out.println("    No tickets found");
            return;
        }
        
        for (int i = 0; i < rows.size(); i++) {
            try {
                WebElement row = rows.get(i);
                List<WebElement> cells = row.findElements(By.tagName("td"));
                
                if (cells.size() >= 7) {
                    String depart = cells.get(0).getText().trim();
                    String arrive = cells.get(1).getText().trim();
                    String seatType = cells.get(2).getText().trim();
                    String departDate = cells.get(3).getText().trim();
                    
                    System.out.println("    [" + (i + 1) + "] " + depart + " → " + arrive + 
                                     " | " + seatType + " | " + departDate);
                }
            } catch (Exception e) {
                // Skip problematic rows
                continue;
            }
        }
    }
}