package iWieczor.pageobjects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import iWieczor.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent {

    WebDriver driver;

    // Constructor to initialize the WebDriver
    public OrderPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // Method to check if the items in the shopping list are present in the order
    public Boolean checkOrder(WebDriver driver, List<String> shoppingList) {
        // Call the goToOrders method from the AbstractComponent class
        goToOrders(driver);

        // Locator for order items
        By orderItemLocator = By.cssSelector("tr td:nth-child(3)");
        List<WebElement> orderItems = driver.findElements(orderItemLocator);

        // Set to store found products
        Set<String> foundProducts = new HashSet<>();

        // Loop through the shopping list and order items to find matches
        for (String product : shoppingList) {
            for (WebElement orderItem : orderItems) {
                String orderItemText = orderItem.getText();
                // If the order item contains the product, add it to the set and break the loop
                if (orderItemText.contains(product)) {
                    foundProducts.add(product);
                    break;
                }
            }
        }

        // Check if all items in the shopping list are found in the order
        Boolean matchText = foundProducts.equals(new HashSet<>(shoppingList));
        return matchText;
    }
}
