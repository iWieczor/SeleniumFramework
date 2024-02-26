package iWieczor.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import iWieczor.AbstractComponents.AbstractComponent;

public class ConfirmationPage extends AbstractComponent {

	WebDriver driver;

	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public Boolean confirmation(WebDriver driver) {
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Boolean match = confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER.");
		return match;
//		System.out.println(confirmMessage);
//		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
}
