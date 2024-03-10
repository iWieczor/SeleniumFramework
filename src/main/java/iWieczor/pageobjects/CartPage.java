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

    // Constructor to initialize the WebDriver and set up PageFactory
    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // WebElement representing the "Checkout" button
    @FindBy(xpath = "//button[normalize-space()='Checkout']")
    WebElement goToCheckout;

    // Method to navigate to the checkout page and return a CheckoutPage object
    public CheckoutPage goToCheckout() {
        goToCheckout.click();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        return checkoutPage;
    }

    // Method to check if the items in the shopping list are present in the cart
    public Boolean checkCart(WebDriver driver, List<String> shoppingList) {
        // Call the goToCart method from the AbstractComponent class
        goToCart(driver);

        // Locator for cart items
        By cartItemLocator = By.cssSelector(".cartSection h3");
        List<WebElement> cartItems = driver.findElements(cartItemLocator);

        // Set to store found products
        Set<String> foundProducts = new HashSet<>();

        // Loop through the shopping list and cart items to find matches
        for (String product : shoppingList) {
            for (WebElement cartItem : cartItems) {
                String cartItemText = cartItem.getText();
                // If the cart item contains the product, add it to the set and break the loop
                if (cartItemText.contains(product)) {
                    foundProducts.add(product);
                    break;
                }
            }
        }

        // Check if all items in the shopping list are found in the cart
        Boolean matchText = foundProducts.equals(new HashSet<>(shoppingList));
        return matchText;
    }
}
