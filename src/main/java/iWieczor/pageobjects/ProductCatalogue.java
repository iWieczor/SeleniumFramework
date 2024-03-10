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

    // Constructor to initialize the WebDriver and set up PageFactory
    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // List of WebElements representing products
    @FindBy(css = ".mb-3")
    List<WebElement> products;

    // Locators for products, add to cart button, and toast message
    By productsBy = By.cssSelector(".mb-3");
    By addToCart = By.xpath("(.//button[@class='btn w-10 rounded'])");
    By toastMessage = By.cssSelector("#toast-container");

    // Method to add products to the cart based on the shopping list
    public void addProductToCart(List<String> shoppingList) {
        for (String item : shoppingList) {
            // Find the product in the list based on its name
            WebElement product = products.stream()
                    .filter(s -> s.findElement(By.cssSelector("b")).getText().equals(item)).findFirst().orElse(null);

            if (product != null) {
                // Find the add to cart button for the product and click it
                WebElement button = product.findElement(addToCart);
                button.click();

                // Wait for the toast message to appear and disappear
                waitForElementToAppear(toastMessage);
                waitForElementToDisappear(toastMessage);
            } else {
                System.out.println("Item not found: " + item);
            }
        }
    }
}
