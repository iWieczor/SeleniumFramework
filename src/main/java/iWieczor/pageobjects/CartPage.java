package iWieczor.pageobjects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import iWieczor.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {

	WebDriver driver;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[normalize-space()='Checkout']")
	WebElement goToCheckout;

	public CheckoutPage goToCheckout() {
		goToCheckout.click();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
	}

	public Boolean checkCart(WebDriver driver, List<String> shoppingList) {
		goToCart(driver);

		By cartItemLocator = By.cssSelector(".cartSection h3");
		List<WebElement> cartItems = driver.findElements(cartItemLocator);

		Set<String> foundProducts = new HashSet<>();
		for (String product : shoppingList) {
			for (WebElement cartItem : cartItems) {
				String cartItemText = cartItem.getText();
				if (cartItemText.contains(product)) {
					foundProducts.add(product);
					break;
				}
			}
		}

		Boolean matchText = foundProducts.equals(new HashSet<>(shoppingList));
		return matchText;
		
		}
	}

