package iWieczor.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import iWieczor.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "userEmail")
	WebElement emailEle;
	@FindBy(id = "userPassword")
	WebElement passwordEle;
	@FindBy(id = "login")
	WebElement submit;

	public void loginApplication(String email, String password) {
		emailEle.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
	}

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");

	}

}
