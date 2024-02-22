package iWieczor.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import iWieczor.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;

	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".mb-3")
	List<WebElement> products;

	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.xpath("(.//button[@class='btn w-10 rounded'])");
	By toastMessage = By.cssSelector("#toast-container");

	public void addProductToCart(List<String> shoppingList) {
		for (String item : shoppingList) {
			WebElement product = products.stream()
					.filter(s -> s.findElement(By.cssSelector("b")).getText().equals(item)).findFirst().orElse(null);

			if (product != null) {
				WebElement button = product.findElement(addToCart);
				button.click();

				waitForElementToAppear(toastMessage);
				waitForElementToDisappear(toastMessage);
			} else {
				System.out.println("Item not found: " + item);
			}
		}
	}

}
