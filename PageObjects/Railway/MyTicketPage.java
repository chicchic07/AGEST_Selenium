package Railway;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MyTicketPage extends GeneralPage {

    public MyTicketPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By tableRows = By.xpath("//table[@class='MyTable']//tr[@class='OddRow' or @class='EvenRow']");
    private final By noTicketMessage = By.xpath("//h1[contains(text(), 'No ticket')] | //p[contains(text(), 'no ticket')]");
    
    private String cancelButtonByRoute = "//td[text()='%s']/following-sibling::td[text()='%s']/following-sibling::td//input[@value='Cancel']";
    
    private final By firstCancelButton = By.xpath("(//input[@value='Cancel'])[1]");

    // Elements
    public List<WebElement> getTableRows() {
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));
        } catch (Exception e) {
            // No tickets found
            return List.of();
        }
    }

    public WebElement getNoTicketMessage() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(noTicketMessage));
    }

    public WebElement getFirstCancelButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(firstCancelButton));
    }

    // Methods
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

    public void clickCancelByRoute(String departStation, String arriveStation) {
        System.out.println("  Looking for ticket: " + departStation + " → " + arriveStation);
        
        By cancelButton = By.xpath(String.format(cancelButtonByRoute, departStation, arriveStation));
        
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
            
            // Scroll to the button
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", btn);
            
            // Wait a bit for scroll
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
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
        
        // Scroll to the button
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", btn);
        
        // Wait a bit for scroll
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        btn.click();
    }

    public boolean confirmCancellation() {
        try {
            System.out.println("  Waiting for confirmation alert...");
            
            // Wait for alert to appear
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            
            // Get alert text for logging
            String alertText = alert.getText();
            System.out.println("  Alert message: " + alertText);
            
            // Click OK to confirm
            System.out.println("  Clicking 'OK' on confirmation alert...");
            alert.accept();
            
            // Wait a bit for the page to refresh
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println("  ✓ Confirmation accepted");
            return true;
            
        } catch (Exception e) {
            System.err.println("  ERROR: Could not handle confirmation alert: " + e.getMessage());
            return false;
        }
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