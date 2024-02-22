package iWieczor.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import iWieczor.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {

	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//input[@type='text'])[2]")
	WebElement CVV;
	@FindBy(xpath = "(//input[@type='text'])[3]\"")
	WebElement nameEle;

	
	public void loginApplication(String email, String password) {
		CVV.sendKeys();
		nameEle.sendKeys();
//		goTo.click();
	}


}
