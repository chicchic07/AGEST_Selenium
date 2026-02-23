package src;

import org.testng.Assert;
import org.testng.annotations.Test;

import Common.RequiresAccount;
import Constant.Constant;
import Constant.SeatType;
import Constant.Stations;
import Railway.BookTicketPage;
import Railway.LoginPage;
import Railway.MyTicketPage;
import Railway.TimeTablePage;

public class BookTicketTest extends BaseTest {

    @Test
    @RequiresAccount
    public void TC12() {
        System.out.println("TC12 - User can book 1 ticket at a time");

        // Pre-Condition: An activated account is existing

        // Step 1: Navigate to QA Railway Website
        driver.switchTo().window(tempEmail);
        homePage.open();

        // Step 2: Login with a valid account
        System.out.println("\nStep 2: Login with a valid account");
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 3: Click on "Book ticket" tab
        System.out.println("\nStep 3: Click on 'Book ticket' tab");
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();

        // Step 4-7: Select booking details
        System.out.println("\nStep 4-7: Select booking details");
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
        Assert.assertEquals(bookTicketPage.getBookedDepartStation(), "Nha Trang");
        Assert.assertEquals(bookTicketPage.getBookedArriveStation(), "Huế");
        Assert.assertEquals(bookTicketPage.getBookedSeatType(), "Soft bed with air conditioner");
        Assert.assertEquals(bookTicketPage.getBookedAmount(), "1");
    }

    @Test
    @RequiresAccount
    public void TC13()  {
        System.out.println("TC13 - User can book many tickets at a time");

        // Step 1-2: Navigate and login
        System.out.println("\nStep 1-2: Navigate to Railway and Login with a valid account");
        driver.switchTo().window(tempEmail);
        homePage.open();

        // Step 3: Click on "Book ticket" tab
        System.out.println("\nStep 3: Click on 'Book ticket' tab");
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();

        // Step 4-7: Select booking details
        System.out.println("\nStep 4-7: Select booking details");
        BookTicketPage.bookTicket25DaysLater(bookTicketPage, Stations.NHA_TRANG, Stations.SAI_GON, SeatType.SOFT_BED_AC, 5);

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
        Assert.assertEquals(bookTicketPage.getBookedDepartStation(), "Nha Trang");
        Assert.assertEquals(bookTicketPage.getBookedArriveStation(), "Sài Gòn");
        Assert.assertEquals(bookTicketPage.getBookedSeatType(), "Soft bed with air conditioner");
        Assert.assertEquals(bookTicketPage.getBookedAmount(), "5");
    }
    
    @Test
    @RequiresAccount
    public void TC14() {
        System.out.println("TC14 - User can check price of ticket from Timetable");

        // Step 1: Navigate to QA Railway Website
        System.out.println("\nStep 1: Navigate to QA Railway Website");
        driver.switchTo().window(tempEmail);
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

        // Step 4: Click on "check price" link
        System.out.println("\nStep 4: Click on 'check price' link of the route from 'Đà Nẵng' to 'Sài Gòn'");
        timeTablePage.clickCheckPrice("Đà Nẵng", "Sài Gòn");

        // Verify price page loaded
        Assert.assertTrue(timeTablePage.isTicketPricePageLoaded(), 
            "Ticket price page did not load");
        System.out.println("   Price page loaded");

        // Verify correct route is displayed
        Assert.assertTrue(timeTablePage.isRouteDisplayed("Đà Nẵng", "Sài Gòn"),
            "Route information is incorrect");
        System.out.println("   Route displayed correctly");

        // Verify all seat prices
        System.out.println("\nVerifying seat prices...");
        boolean pricesCorrect = timeTablePage.verifySeatPrices(
            "30 $", "45 $", "60 $", "60 $", "75 $", "90 $"
        );
        Assert.assertTrue(pricesCorrect, "Seat prices do not match expected values");
    }
    
    @Test
    @RequiresAccount
    public void TC15() {
        System.out.println("TC15 - User can book ticket by clicking on 'book ticket' link in Timetable page");

        // Step 1: Navigate to QA Railway Website
        System.out.println("\nStep 1: Navigate to QA Railway Website");
        driver.switchTo().window(tempEmail);
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

        // Step 4: Click on "book ticket" link
        System.out.println("\nStep 4: Click on 'book ticket' link of route from 'Quảng Ngãi' to 'Huế'");
        BookTicketPage bookTicketPage = timeTablePage.clickBookTicket("Quảng Ngãi", "Huế");

        // Step 5: Select Depart date = tomorrow
        System.out.println("\nStep 5: Select Depart date = tomorrow");
        bookTicketPage.selectDepartDateByIndex(1);

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
    }
    
    @Test
    @RequiresAccount
    public void TC16() {
        System.out.println("TC16 - User can cancel a ticket");

        // Pre-Condition: An activated account is existing

        // Step 1: Navigate to QA Railway Website
        System.out.println("\nStep 1: Navigate to QA Railway Website");
        driver.switchTo().window(tempEmail);
        homePage.open();

        // Step 2: Login with a valid account
        System.out.println("\nStep 2: Login with a valid account");
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Verify login successful
        String welcomeMsg = homePage.getWelcomeMessage();
        System.out.println("  Login successful. Welcome message: " + welcomeMsg);
        Assert.assertTrue(welcomeMsg.contains("Welcome"), "Login failed - user not logged in");

        // Step 3: Book a ticket
        System.out.println("\nStep 3: Book a ticket");
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        BookTicketPage.bookSimpleTicket(bookTicketPage, Stations.SAI_GON, Stations.NHA_TRANG);

        // Verify ticket is booked
        Assert.assertTrue(
            bookTicketPage.isTicketBookedSuccessfully(),
            "Ticket booking failed - success message not displayed"
        );
        System.out.println("   Ticket booked successfully");

        String bookedDepartStation = bookTicketPage.getBookedDepartStation();
        String bookedArriveStation = bookTicketPage.getBookedArriveStation();
        System.out.println("  Booked ticket: " + bookedDepartStation + " → " + bookedArriveStation);

        // Step 4: Click on "My ticket" tab
        System.out.println("\nStep 4: Click on 'My ticket' tab");
        MyTicketPage myTicketPage = homePage.gotoMyTicketPage();
        int initialTicketCount = myTicketPage.getInitialTicketCount();

        // Step 5: Click on "Cancel" button of ticket which user booked
        System.out.println("\nStep 5: Click on 'Cancel' button of ticket");
        myTicketPage.clickFirstCancelButton();

        // Step 6: Click on "OK" button on Confirmation message "Are you sure?"
        System.out.println("\nStep 6: Click on 'OK' button on Confirmation message");
        boolean confirmed = myTicketPage.confirmCancellation();
        Assert.assertTrue(confirmed, "Failed to confirm cancellation");

        // Step 7: Verify the canceled ticket is disappeared
        System.out.println("\nStep 7: Verify the canceled ticket is disappeared");
        Assert.assertTrue(
            myTicketPage.waitForCancellationComplete(bookedDepartStation, bookedArriveStation, initialTicketCount),
            "Ticket was not canceled successfully"
        );
    }
}