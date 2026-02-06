package Railway;

import Constant.Constant;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends GeneralPage {
	
	public HomePage(WebDriver driver) {
		 super(driver);
    }
	//Locators
	
	//Elements
		
	//Methods
	public HomePage open() {
		driver.navigate().to(Constant.RAILWAY_URL);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constant.PAGE_LOAD_TIMEOUT));
		wait.until(ExpectedConditions.urlToBe(Constant.RAILWAY_URL));
		return this;
	}
	
	//Kiểm tra xem user có đang ở HomePage chưa
	public boolean isOnHomePage() {
		return driver.getCurrentUrl().equals(Constant.RAILWAY_URL);
	}
}