package Railway;

import Constant.Constant;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends GeneralPage {
	public HomePage() {
		super();
	}
	//Locators
	
	//Elements
		
	//Methods
	public HomePage open() {
		Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
		Constant.WEBDRIVER.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constant.PAGE_LOAD_TIMEOUT));
		wait.until(ExpectedConditions.urlToBe(Constant.RAILWAY_URL));
		return this;
	}
	
	//Kiểm tra xem user có đang ở HomePage chưa
	public boolean isOnHomePage() {
		return Constant.WEBDRIVER.getCurrentUrl().equals(Constant.RAILWAY_URL);
	}
}