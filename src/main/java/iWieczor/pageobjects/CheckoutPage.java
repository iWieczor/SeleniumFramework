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

	// Constructor to initialize the WebDriver and set up PageFactory
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// WebElements representing the CVV, name, and place order button
	@FindBy(xpath = "(//input[@type='text'])[2]")
	WebElement CVVEle;
	@FindBy(xpath = "(//input[@type='text'])[3]")
	WebElement nameEle;
	@FindBy(xpath = "//div[@class='details__user']//a")
	WebElement placeOrder;

	// Method to fill checkout information (CVV, name, and country)
	public void fillCheckoutInfo(String CVV, String name, CharSequence charSequence) {
		// Set up WebDriverWait for dynamic page elements
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		// Fill CVV and name fields
		CVVEle.sendKeys(CVV);
		nameEle.sendKeys(name);

		// Select country from the dropdown
		driver.findElement(By.xpath("(//input[@placeholder='Select Country'])")).sendKeys(charSequence);

		// Handle the country selection using a try-catch block for TimeoutException
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

	// Method to place the order and return a ConfirmationPage object
	public ConfirmationPage placeOrder() {
		placeOrder.click();
		ConfirmationPage confirmationPage = new ConfirmationPage(driver);
		return confirmationPage;
	}

}
