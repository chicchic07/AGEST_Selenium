package src;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import Constant.Constant;
import Railway.HomePage;
import java.time.Duration;

public class BaseTest {
	protected HomePage homePage;
	protected static WebDriver driver; 	
	
	public static WebDriver getDriver() {
        return driver;
    }
	
	@BeforeMethod
	public void setUp() {
		System.out.println("Pre-Condition");
		driver = new ChromeDriver();
		Constant.WEBDRIVER = driver;
		driver.manage().window().maximize();
		
		homePage = new HomePage(driver);
	}
	
	@AfterMethod
	public void tearDown() {
		System.out.println("Post-Condition");
		if (Constant.WEBDRIVER != null)
			Constant.WEBDRIVER.quit();
	}
	
}