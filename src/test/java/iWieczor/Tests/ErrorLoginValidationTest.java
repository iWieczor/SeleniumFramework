package iWieczor.Tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import iWieczor.TestComponents.BaseTest;
import iWieczor.pageobjects.CartPage;
import iWieczor.pageobjects.ProductCatalogue;

public class ErrorLoginValidationTest extends BaseTest {

    @Test
    public void errorProduct() throws IOException {
        // List of products to be added to the cart
        List<String> shoppingList = Arrays.asList("IPHONE 133 PRO");

        // Login to the application and add products to the cart
        ProductCatalogue productCatalogue = landingPage.loginApplication("iwieczorek2@iwie.com", "Testtest1!");
        productCatalogue.addProductToCart(shoppingList);

        // Navigate to the cart page
        CartPage cartPage = new CartPage(driver);

        // Check if the added product is not present in the cart
        Boolean match = cartPage.checkCart(driver, shoppingList);

        // Assert that the product is not present in the cart
        Assert.assertFalse(match);
    }
}
