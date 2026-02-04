import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import Common.Utilities;
import Constant.Constant;
import Railway.HomePage;
import Railway.LoginPage;

public class CreatAccountTest {
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("Pre-Condition");
		Constant.WEBDRIVER = new ChromeDriver();
		Constant.WEBDRIVER.manage().window().maximize();
	}
	
	@AfterMethod
	public void afterMethod() {
		System.out.println("Post-Condition");
		Constant.WEBDRIVER.quit();
	}
	
	@Test
	public void TC07() {
		System.out.println("TC07 - User can't create account with an already in-use email");
		
	}

}
