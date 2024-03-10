package iWieczor.Tests;

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

public class StandAloneTest {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); // set implicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2)); // set wait time to 5s
		driver.get("https://rahulshettyacademy.com/client"); // Loading the homepage


		
		List<String> shoppingList = Arrays.asList("ZARA COAT 3", "IPHONE 13 PRO");

		login(driver, wait);
		addToCart(driver, wait, shoppingList);
		checkCart(driver, shoppingList);
		checkout(driver, wait);
		confirmation(driver);
		driver.quit();
	}

	private static void checkCart(WebDriver driver, List<String> shoppingList) {
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

		By cartItemLocator = By.cssSelector(".cartSection h3");
        List<WebElement> cartItems = driver.findElements(cartItemLocator);

        // Set to track found products
        Set<String> foundProducts = new HashSet<>();

        // Check if items in shoppingList are in the cart
        for (String product : shoppingList) {
            for (WebElement cartItem : cartItems) {
                // Assuming each cart item has a text representation, adjust this based on your website structure
                String cartItemText = cartItem.getText();
                if (cartItemText.contains(product)) {
                    foundProducts.add(product);
                    break;
                }
            }
        }

        // Check if there are extra items in the cart
        if (!foundProducts.equals(new HashSet<>(shoppingList))) {
            System.out.println("There are extra items in the cart");
        }
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

	private static void addToCart(WebDriver driver, WebDriverWait wait, List<String> shoppingList) {

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = new ArrayList<>(driver.findElements(By.cssSelector(".mb-3")));

		for (String item : shoppingList) {
			WebElement product = products.stream()
					.filter(s -> s.findElement(By.cssSelector("b")).getText().equals(item)).findFirst().orElse(null);

			if (product != null) {
				WebElement button = product.findElement(By.xpath("(.//button[@class='btn w-10 rounded'])"));
				button.click();

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#toast-container")));
			} else {
				System.out.println("Item not found: " + item);
			}
		}
	}

	private static void login(WebDriver driver, WebDriverWait wait) {
		String email = "iwieczorek@iwie.com";
		String password = "Testtest1!";
		driver.findElement(By.id("userEmail")).sendKeys(email);
		driver.findElement(By.id("userPassword")).sendKeys(password);
		driver.findElement(By.id("login")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#toast-container")));

	}

}
