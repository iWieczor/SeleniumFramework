package iWieczor.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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
	@FindBy(css = "#toast-container")
	WebElement ToastMessage;

	public ProductCatalogue loginApplication(String email, String password) {
	    emailEle.sendKeys(email);
	    passwordEle.sendKeys(password);
	    submit.click();
	    ProductCatalogue productCatalogue = new ProductCatalogue(driver);
	    return productCatalogue;
	}
	
	public String getToastMessage() {
		waitForWebElementToAppear(ToastMessage);
		return ToastMessage.getText();
	}
	public void toastDisappear() {
		waitForWebElementToDisappear(ToastMessage);
	}

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");

	}
	public List<String> invalidEmailText(){
		submit.click();
		String emptyEmailText = (driver.findElement(By.cssSelector("div[class='form-group'] div[class='invalid-feedback'] div"))).getText();
		String emptyPasswordText = (driver.findElement(By.cssSelector("div[class='form-group mb-4'] div[class='invalid-feedback'] div"))).getText();
		emailEle.sendKeys("invalid Email");
	    submit.click();
	    String invalidEmailText = (driver.findElement(By.cssSelector("div[class='form-group'] div[class='invalid-feedback'] div"))).getText();
        List<String> loginErrorsMsgList = new ArrayList<>();
        loginErrorsMsgList.add(emptyEmailText);
        loginErrorsMsgList.add(emptyPasswordText);
        loginErrorsMsgList.add(invalidEmailText);
        return loginErrorsMsgList;
	}

}
