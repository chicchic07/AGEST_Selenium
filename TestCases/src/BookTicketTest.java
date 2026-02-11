package src;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import Common.TestDataManager;
import Constant.Constant;
import Constant.DepartDate;
import Constant.SeatType;
import Constant.Stations;
import Railway.BookTicketPage;
import Railway.HomePage;
import Railway.LoginPage;
import Railway.MyTicketPage;
import Railway.TimeTablePage;

public class BookTicketTest extends BaseTest {

    @Test
    public void TC12() {
        System.out.println("TC12 - User can book 1 ticket at a time");
        System.out.println("============================================================");

        // Pre-Condition: An activated account is existing

        // Step 1: Navigate to QA Railway Website
        System.out.println("\nStep 1: Navigate to QA Railway Website");
        homePage.open();

        // Step 2: Login with a valid account
        System.out.println("\nStep 2: Login with a valid account");
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 3: Click on "Book ticket" tab
        System.out.println("\nStep 3: Click on 'Book ticket' tab");
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();

        // Step 4: Select the next 2 days from "Depart date"
        System.out.println("\nStep 4: Select the next 2 days from 'Depart date'");
        // Step 5: Select Depart from "Nha Trang" and Arrive at "Huế"
        System.out.println("\nStep 5: Select Depart from 'Nha Trang' and Arrive at 'Huế'");
        // Step 6: Select "Soft bed with air conditioner" for Seat type
        System.out.println("\nStep 6: Select 'Soft bed with air conditioner' for Seat type");
        // Step 7: Select "1" for "Ticket amount"
        System.out.println("\nStep 7: Select '1' for 'Ticket amount'");
        BookTicketPage.bookTicketTwoDaysLater(bookTicketPage, Stations.NHA_TRANG, Stations.HUE, SeatType.SOFT_BED_AC, 1);
        
        // Step 8: Click on "Book ticket" button
        System.out.println("\nStep 8: Click on 'Book ticket' button");
        bookTicketPage.clickBookTicketButton();

        // Verify success message
        Assert.assertTrue(
            bookTicketPage.isTicketBookedSuccessfully(),
            "Ticket booking failed - success message not displayed"
        );

        // Verify ticket information displays correctly
        System.out.println("\nVerifying ticket information...");
        
        String bookedDepartStation = bookTicketPage.getBookedDepartStation();
        String bookedArriveStation = bookTicketPage.getBookedArriveStation();
        String bookedSeatType = bookTicketPage.getBookedSeatType();
        String bookedAmount = bookTicketPage.getBookedAmount();

        System.out.println("  Depart Station: " + bookedDepartStation);
        System.out.println("  Arrive Station: " + bookedArriveStation);
        System.out.println("  Seat Type: " + bookedSeatType);
        System.out.println("  Amount: " + bookedAmount);

        // Assertions for ticket information
        Assert.assertTrue(bookTicketPage.isTicketBookedSuccessfully());
        Assert.assertEquals( bookTicketPage.getBookedDepartStation(), "Nha Trang");
        Assert.assertEquals(bookTicketPage.getBookedArriveStation(), "Huế");
        Assert.assertEquals(bookTicketPage.getBookedSeatType(), "Soft bed with air conditioner");
        Assert.assertEquals(bookTicketPage.getBookedAmount(), "1");


        System.out.println("\n TC12 PASSED - Ticket booked successfully with correct information!");
        System.out.println("============================================================");
    }

    @Test
    public void TC13() {
        System.out.println("TC13 - User can book many tickets at a time");
        System.out.println("============================================================");

        // Step 1-2: Navigate and login (12/2/2026 lên cty sửa lại đoạn pr4e-conditon của các case thành register acc mới, ko login acc có sẵn
        System.out.println("\nStep 1-2: Navigate to Railway and Login with a valid account ");
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 3: Click on "Book ticket" tab
        System.out.println("\nStep 3: Click on \"Book ticket\" tab");
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();

        // Step 4: Select the next 25 days from "Depart date"
        System.out.println("\nStep 4: Select the next 2 days from 'Depart date'");
        // Step 5: Select Depart from "Nha Trang" and Arrive at "Sài Gòn"
        System.out.println("\nStep 5: Select Depart from 'Nha Trang' and Arrive at 'Sài Gòn'");
        // Step 6: Select "Soft bed with air conditioner" for Seat type
        System.out.println("\nStep 6: Select 'Soft bed with air conditioner' for Seat type");
        // Step 7: Select "5" for "Ticket amount"
        System.out.println("\nStep 7: Select '5' for 'Ticket amount'");
        BookTicketPage.bookTicket25DaysLater(bookTicketPage, Stations.NHA_TRANG, Stations.SAI_GON, SeatType.SOFT_BED_AC, 5);

        // Step 8: Click on "Book ticket" button
        System.out.println("\nStep 8: Click on 'Book ticket' button");
        bookTicketPage.clickBookTicketButton();

        // Verify success message
        Assert.assertTrue(
            bookTicketPage.isTicketBookedSuccessfully(),
            "Ticket booking failed - success message not displayed"
        );

        // Verify ticket information displays correctly
        System.out.println("\nVerifying ticket information...");
        
        String bookedDepartStation = bookTicketPage.getBookedDepartStation();
        String bookedArriveStation = bookTicketPage.getBookedArriveStation();
        String bookedSeatType = bookTicketPage.getBookedSeatType();
        String bookedAmount = bookTicketPage.getBookedAmount();

        System.out.println("  Depart Station: " + bookedDepartStation);
        System.out.println("  Arrive Station: " + bookedArriveStation);
        System.out.println("  Seat Type: " + bookedSeatType);
        System.out.println("  Amount: " + bookedAmount);

        // Assertions for ticket information
        Assert.assertTrue(bookTicketPage.isTicketBookedSuccessfully());
        Assert.assertEquals( bookTicketPage.getBookedDepartStation(), "Nha Trang");
        Assert.assertEquals(bookTicketPage.getBookedArriveStation(), "Sài Gòn");
        Assert.assertEquals(bookTicketPage.getBookedSeatType(), "Soft bed with air conditioner");
        Assert.assertEquals(bookTicketPage.getBookedAmount(), "5");


        System.out.println("\n TC13 PASSED - Ticket booked successfully with correct information!");
        System.out.println("============================================================");
    }
    
    @Test
    public void TC14() {
        System.out.println("TC14 - User can check price of ticket from Timetable");
        System.out.println("============================================================");

        // Pre-Condition: An activated account is existing

        // Step 1: Navigate to QA Railway Website
        System.out.println("\nStep 1: Navigate to QA Railway Website");
        homePage.open();

        // Step 2: Login with a valid account
        System.out.println("\nStep 2: Login with a valid account");
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Verify login successful
        String welcomeMsg = homePage.getWelcomeMessage();
        System.out.println("  Login successful. Welcome message: " + welcomeMsg);
        Assert.assertTrue(welcomeMsg.contains("Welcome"), "Login failed - user not logged in");

        // Step 3: Click on "Timetable" tab
        System.out.println("\nStep 3: Click on 'Timetable' tab");
        TimeTablePage timeTablePage = homePage.gotoTimeTablePage();

        // Step 4: Click on "check price" link of the route from "Đà Nẵng" to "Sài Gòn"
        System.out.println("\nStep 4: Click on 'check price' link of the route from 'Đà Nẵng' to 'Sài Gòn'");
        timeTablePage.clickCheckPrice("Đà Nẵng", "Sài Gòn");

        // Verify "Ticket Price" page is loaded
        System.out.println("\nVerifying 'Ticket Price' page is loaded...");
        Assert.assertTrue(
            timeTablePage.isTicketPricePageLoaded(),
            "Ticket Price page did not load"
        );
        System.out.println("   Ticket Price page loaded successfully");

        // Verify ticket table shows "Ticket price from Đà Nẵng to Sài Gòn"
        System.out.println("\nVerifying route information...");
        Assert.assertTrue(
            timeTablePage.isRouteDisplayed("Đà Nẵng", "Sài Gòn"),
            "Route information is incorrect"
        );
        System.out.println("   Route displayed: " + timeTablePage.getPageTitleText());

        // Verify price for each seat displays correctly
        System.out.println("\nVerifying seat prices...");
        System.out.println("Expected prices:");
        System.out.println("  HS  = 310000");
        System.out.println("  SS  = 335000");
        System.out.println("  SSC = 360000");
        System.out.println("  HB  = 410000");
        System.out.println("  SB  = 460000");
        System.out.println("  SBC = 510000");

        boolean pricesCorrect = timeTablePage.verifySeatPrices(
            "310000",   // Hard seat (HS)
            "335000",   // Soft seat (SS)
            "360000",   // Soft seat with air conditioner (SSC)
            "410000",   // Hard bed (HB)
            "460000",   // Soft bed (SB)
            "510000"    // Soft bed with air conditioner (SBC)
        );

        Assert.assertTrue(pricesCorrect, "One or more seat prices are incorrect");

        System.out.println("\n TC14 PASSED - All ticket prices are displayed correctly!");
        System.out.println("============================================================");
    }
    
    @Test
    public void TC15() {
        System.out.println("TC15 - User can book ticket from Timetable");
        System.out.println("============================================================");

        // Pre-Condition: An activated account is existing
        // We will use the existing account from Constant.USERNAME and Constant.PASSWORD

        // Step 1: Navigate to QA Railway Website
        System.out.println("\nStep 1: Navigate to QA Railway Website");
        homePage.open();

        // Step 2: Login with a valid account
        System.out.println("\nStep 2: Login with a valid account");
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Verify login successful
        String welcomeMsg = homePage.getWelcomeMessage();
        System.out.println("  Login successful. Welcome message: " + welcomeMsg);
        Assert.assertTrue(welcomeMsg.contains("Welcome"), "Login failed - user not logged in");

        // Step 3: Click on "Timetable" tab
        System.out.println("\nStep 3: Click on 'Timetable' tab");
        TimeTablePage timeTablePage = homePage.gotoTimeTablePage();

        // Step 4: Click on "book ticket" of route "Quảng Ngãi" to "Huế"
        System.out.println("\nStep 4: Click on 'book ticket' of route 'Quảng Ngãi' to 'Huế'");
        BookTicketPage bookTicketPage = timeTablePage.clickBookTicket("Quảng Ngãi", "Huế");

        // Verify Book ticket form is shown with the corrected "depart from" and "Arrive at"
        System.out.println("\nVerifying Book ticket form...");
        
        // Verify the form is loaded and ready for booking
        Assert.assertTrue(
            bookTicketPage.getSelectDate().isDisplayed(),
            "Book ticket form did not load properly"
        );
        System.out.println("   Book ticket form is displayed");

        // Step 5: Select Depart date = tomorrow
        System.out.println("\nStep 5: Select Depart date = tomorrow");
        bookTicketPage.selectDepartDateByIndex(1); // Tomorrow is 1 day from today

        // Step 6: Select Ticket amount = 5
        System.out.println("\nStep 6: Select Ticket amount = 5");
        bookTicketPage.selectTicketAmount(5);

        // Step 7: Click on "Book ticket" button
        System.out.println("\nStep 7: Click on 'Book ticket' button");
        bookTicketPage.clickBookTicketButton();

        // Verify Message "Ticket booked successfully!" appears
        System.out.println("\nVerifying ticket booking...");
        Assert.assertTrue(
            bookTicketPage.isTicketBookedSuccessfully(),
            "Ticket booking failed - success message not displayed"
        );
        System.out.println("   Success message: 'Ticket booked successfully!'");

        // Verify booked ticket information
        System.out.println("\nVerifying booked ticket information...");
        
        String bookedDepartStation = bookTicketPage.getBookedDepartStation();
        String bookedArriveStation = bookTicketPage.getBookedArriveStation();
        String bookedAmount = bookTicketPage.getBookedAmount();

        System.out.println("  Depart Station: " + bookedDepartStation);
        System.out.println("  Arrive Station: " + bookedArriveStation);
        System.out.println("  Amount: " + bookedAmount);

        // Assertions for ticket information
        Assert.assertEquals(bookedDepartStation, "Quảng Ngãi", "Depart station is incorrect");
        Assert.assertEquals(bookedArriveStation, "Huế", "Arrive station is incorrect");
        Assert.assertEquals(bookedAmount, "5", "Ticket amount is incorrect");

        System.out.println("\n TC15 PASSED - Ticket booked successfully from Timetable!");
        System.out.println("============================================================");
    }
    
    @Test(dependsOnMethods = {"src.CreateAccountTest.TC09"})
    public void TC16() throws Exception {
        System.out.println("TC16 - User can cancel a ticket");
        System.out.println("============================================================");

        // Pre-Condition: An activated account is existing
        
        // Step 1: Navigate to QA Railway Website
        System.out.println("\nStep 1: Navigate to QA Railway Website");
        homePage.open();
        
        Assert.assertTrue(TestDataManager.hasTC09Data(), 
    			"TC09 must run successfully before TC16. Please run TC09 first to create an activated account.");
    		
    		String activatedEmail = TestDataManager.getTC09Email();
    		String currentPassword = TestDataManager.getTC09Password();
    		
    		System.out.println("Using account from TC09:");
    		System.out.println("  Email: " + activatedEmail);
    		System.out.println("  Password: " + currentPassword);

        // Step 2: Login with a valid account
        System.out.println("\nStep 2: Login with a valid account");
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Verify login successful
        String welcomeMsg = homePage.getWelcomeMessage();
        System.out.println("  Login successful. Welcome message: " + welcomeMsg);
        Assert.assertTrue(welcomeMsg.contains("Welcome"), "Login failed - user not logged in");

        // Step 3: Book a ticket (simplified: click Book Ticket tab, scroll to Book Ticket button and click)
        System.out.println("\nStep 3: Book a ticket");
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        
        System.out.println("  Scrolling to 'Book ticket' button...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
                        bookTicketPage.getBtnBookTicket());
        
        // Wait a bit for scroll
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("  Clicking 'Book ticket' button...");
        bookTicketPage.clickBookTicketButton();

        // Verify ticket is booked
        Assert.assertTrue(
            bookTicketPage.isTicketBookedSuccessfully(),
            "Ticket booking failed - success message not displayed"
        );
        System.out.println("   Ticket booked successfully");

        // Get booked ticket information for verification later
        String bookedDepartStation = bookTicketPage.getBookedDepartStation();
        String bookedArriveStation = bookTicketPage.getBookedArriveStation();
        System.out.println("  Booked ticket: " + bookedDepartStation + " → " + bookedArriveStation);

        // Step 4: Click on "My ticket" tab
        System.out.println("\nStep 4: Click on 'My ticket' tab");
        MyTicketPage myTicketPage = homePage.gotoMyTicketPage();

        // Verify we're on My Ticket page and ticket exists
        Assert.assertTrue(myTicketPage.hasTickets(), 
                        "No tickets found on My Ticket page");
        System.out.println("   My Ticket page loaded");
        
        // Print current tickets for debugging
        myTicketPage.printAllTickets();
        
        // Get initial ticket count
        int initialTicketCount = myTicketPage.getTicketCount();
        System.out.println("  Initial ticket count: " + initialTicketCount);

        // Step 5: Click on "Cancel" button of ticket which user booked
        System.out.println("\nStep 5: Click on 'Cancel' button of ticket");
        myTicketPage.clickFirstCancelButton();

        // Step 6: Click on "OK" button on Confirmation message "Are you sure?"
        System.out.println("\nStep 6: Click on 'OK' button on Confirmation message");
        boolean confirmed = myTicketPage.confirmCancellation();
        Assert.assertTrue(confirmed, "Failed to confirm cancellation");

        // Step 7: Verify the canceled ticket is disappeared
        System.out.println("\nStep 7: Verify the canceled ticket is disappeared");
        
        // Wait a bit for page to refresh after cancellation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Method 1: Check if ticket count decreased
        int finalTicketCount = myTicketPage.getTicketCount();
        System.out.println("  Final ticket count: " + finalTicketCount);
        
        boolean ticketCountDecreased = finalTicketCount < initialTicketCount;
        System.out.println("  Ticket count decreased: " + ticketCountDecreased);

        // Method 2: Verify specific ticket is no longer in the list
        boolean ticketRemoved = myTicketPage.isTicketCanceled(bookedDepartStation, bookedArriveStation);
        
        // Method 3: If no tickets remain, verify "no ticket" message
        boolean noTicketsRemain = false;
        if (finalTicketCount == 0) {
            noTicketsRemain = myTicketPage.isNoTicketMessageDisplayed();
            System.out.println("  No ticket message displayed: " + noTicketsRemain);
        }

        // Assert at least one verification method confirms cancellation
        boolean cancelSuccess = ticketCountDecreased || ticketRemoved || noTicketsRemain;
        
        Assert.assertTrue(cancelSuccess, 
            "Ticket was not canceled successfully. " +
            "Initial count: " + initialTicketCount + 
            ", Final count: " + finalTicketCount +
            ", Ticket removed: " + ticketRemoved);

        System.out.println("\n TC16 PASSED - Ticket canceled successfully!");
        System.out.println("============================================================");
    }
}