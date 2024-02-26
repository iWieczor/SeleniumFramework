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

public class OrderPage extends AbstractComponent {

	WebDriver driver;

	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;

	}

	public Boolean checkOrder(WebDriver driver, List<String> shoppingList) {
		goToOrders(driver);

		By orderItemLocator = By.cssSelector("tr td:nth-child(3)");
		List<WebElement> cartItems = driver.findElements(orderItemLocator);

		Set<String> foundProducts = new HashSet<>();
		for (String product : shoppingList) {
			for (WebElement orderItem : cartItems) {
				String orderItemText = orderItem.getText();
				if (orderItemText.contains(product)) {
					foundProducts.add(product);
					break;
				}
			}
		}

		Boolean matchText = foundProducts.equals(new HashSet<>(shoppingList));
		return matchText;
		
		}
	}

