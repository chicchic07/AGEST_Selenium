import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import Constant.Constant;
import Railway.HomePage;
import java.time.Duration;

public class BaseTest {
	protected HomePage homePage;
	
	@BeforeMethod
	public void setUp() {
		System.out.println("Pre-Condition");
		Constant.WEBDRIVER = new ChromeDriver();
		Constant.WEBDRIVER.manage().window().maximize();
		Constant.WEBDRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constant.DEFAULT_TIME));
		
		homePage = new HomePage();
	}
	
	@AfterMethod
	public void tearDown() {
		System.out.println("Post-Condition");
		if (Constant.WEBDRIVER != null)
			Constant.WEBDRIVER.quit();
	}
	
}