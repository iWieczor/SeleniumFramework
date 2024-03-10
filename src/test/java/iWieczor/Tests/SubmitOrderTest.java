package iWieczor.Tests;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import iWieczor.TestComponents.BaseTest;
import iWieczor.TestComponents.Retry;
import iWieczor.pageobjects.CartPage;
import iWieczor.pageobjects.CheckoutPage;
import iWieczor.pageobjects.ConfirmationPage;
import iWieczor.pageobjects.ProductCatalogue;

/**
 * This class contains a TestNG test for submitting orders using data from a JSON file.
 */
public class SubmitOrderTest extends BaseTest {

    // List to store shopping items
    List<String> shoppingList;

    /**
     * Test method to submit an order based on the provided test data.
     *
     * @param testData Test data containing email, password, and products.
     * @throws IOException If there is an issue reading the data.
     */
    @Test(dataProvider = "getData", groups = { "Purchase" }, retryAnalyzer = Retry.class)
    public void submitOrder(Map<String, Object> testData) throws IOException {

        // Extracting the list of products from the test data
        @SuppressWarnings("unchecked")
        List<String> shoppingList = (List<String>) testData.get("products");

        // Login to the application
        ProductCatalogue productCatalogue = landingPage.loginApplication(
                (testData.get("email")).toString(),
                (testData.get("password")).toString());

        // Add products to the cart
        productCatalogue.addProductToCart(shoppingList);
        CartPage cartPage = new CartPage(driver);
        
        // Check if products are present in the cart
        Boolean match = cartPage.checkCart(driver, shoppingList);
        Assert.assertTrue(match);

        // Proceed to checkout
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.fillCheckoutInfo("222", "Wieczorek", "Poland".subSequence(0, "Poland".length()));

        // Place the order and get confirmation
        ConfirmationPage confirmationPage = checkoutPage.placeOrder();
        Boolean matchText = confirmationPage.confirmation(driver);
        Assert.assertTrue(matchText);
    }

    /**
     * Data provider method to read test data from a JSON file.
     *
     * @return Object array containing test data.
     * @throws IOException If there is an issue reading the data.
     */
    @DataProvider
    public Object[][] getData() throws IOException {

        // ObjectMapper for reading JSON data
        ObjectMapper objectMapper = new ObjectMapper();

        // Read test data from the JSON file
        List<Map<String, Object>> testDataList = objectMapper.readValue(
                new File(filePath),
                new TypeReference<List<Map<String, Object>>>() {});

        // Convert the list to a 2D array
        Object[][] testDataArray = new Object[testDataList.size()][1];
        for (int i = 0; i < testDataList.size(); i++) {
            testDataArray[i][0] = testDataList.get(i);
        }

        return testDataArray;
    }
}
