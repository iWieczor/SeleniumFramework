package iWieczor.SeleniumFramework;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StandAloneTest {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); // set implicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2)); // set wait time to 5s
		driver.get("https://rahulshettyacademy.com/client"); // Loading the homepage

		login(driver, wait);
		addToCart(driver, wait);
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
	}

	private static void addToCart(WebDriver driver, WebDriverWait wait) {
		List<String> shoppingList = Arrays.asList("ZARA COAT 3","test item", "IPHONE 13 PRO");

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
