package iWieczor.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import iWieczor.pageobjects.OrderPage;

public class AbstractComponent {

	WebDriver driver;

	// Constructor to initialize the WebDriver
	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
	}

	// Method to wait for an element to appear on the page
	public void waitForElementToAppear(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

	// Method to wait for an element to disappear from the page
	public void waitForElementToDisappear(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
	}

	// Method to wait for a WebElement to appear on the page
	public void waitForWebElementToAppear(WebElement webElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		wait.until(ExpectedConditions.visibilityOf(webElement));
	}

	// Method to wait for a WebElement to disappear from the page
	public void waitForWebElementToDisappear(WebElement webElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		wait.until(ExpectedConditions.invisibilityOf(webElement));
	}

	// Method to navigate to the cart page
	public void goToCart(WebDriver driver) {
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
	}

	// Method to sign out from the application
	public void signOut(WebDriver driver) {
		driver.findElement(By.xpath("//button[normalize-space()='Sign Out']")).click();
	}

	// Method to navigate to the orders page and return an OrderPage object
	public OrderPage goToOrders(WebDriver driver) {
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/myorders']")).click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}
}
