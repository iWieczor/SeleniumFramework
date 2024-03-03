package iWieczor.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import iWieczor.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {

	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//input[@type='text'])[2]")
	WebElement CVVEle;
	@FindBy(xpath = "(//input[@type='text'])[3]")
	WebElement nameEle;
	@FindBy(xpath = "//div[@class='details__user']//a")
	WebElement placeOrder;

	
	public void fillCheckoutInfo(String CVV, String name, CharSequence charSequence) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		CVVEle.sendKeys(CVV);
		nameEle.sendKeys(name);
		
		driver.findElement(By.xpath("(//input[@placeholder='Select Country'])")).sendKeys(charSequence);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='payment__shipping']//button")));
			List<WebElement> options = driver.findElements(By.xpath("//div[@class='payment__shipping']//button"));
			for (WebElement option : options) {
				if (option.getText().equalsIgnoreCase("Poland")) {
					option.click();
					break;
				}
			}
		} catch (TimeoutException e) {
			System.out.println("ERROR - Invalid country");
		}
	}
	public ConfirmationPage placeOrder() {
		placeOrder.click();
		ConfirmationPage confirmationPage = new ConfirmationPage(driver);
		return confirmationPage;
	}

}
