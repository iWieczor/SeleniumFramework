package iWieczor.SeleniumFramework;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import iWieczor.pageobjects.CartPage;
import iWieczor.pageobjects.CheckoutPage;
import iWieczor.pageobjects.LandingPage;
import iWieczor.pageobjects.ProductCatalogue;

public class SubmitOrderTest {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); // set implicit wait
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2)); // set wait time to 5s
		List<String> shoppingList = Arrays.asList("ZARA COAT 3", "IPHONE 13 PRO");
		
		LandingPage landingPage = new LandingPage(driver);
		landingPage.goTo();
		landingPage.loginApplication("iwieczorek@iwie.com", "Testtest1!");
		
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		productCatalogue.addProductToCart(shoppingList);
		
		CartPage cartPage = new CartPage(driver);
		cartPage.checkCart(driver, shoppingList);
		cartPage.goToCheckout();
		
//		CheckoutPage checkoutPage = new CheckoutPage(driver);
				
//		checkout(driver, wait);
//		confirmation(driver);
//		driver.quit();
	}


	private static void confirmation(WebDriver driver) {
		driver.findElement(By.xpath("//a[normalize-space()='Place Order']")).click();
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
//		System.out.println(confirmMessage);
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
	}

	private static void checkout(WebDriver driver, WebDriverWait wait) {
		driver.findElement(By.xpath("//button[normalize-space()='Checkout']")).click();
		driver.findElement(By.xpath("(//input[@type='text'])[1]")).sendKeys("1234567890");
		driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("222");
		driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys("Wieczorek");
		
		driver.findElement(By.xpath("(//input[@placeholder='Select Country'])")).sendKeys("Poland");
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

}
