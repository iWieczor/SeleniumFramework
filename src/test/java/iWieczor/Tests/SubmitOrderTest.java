package iWieczor.Tests;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

public class SubmitOrderTest extends BaseTest {

	List<String> shoppingList;

	@Test(dataProvider = "getData", groups = { "Purchase" },retryAnalyzer=Retry.class)
	public void submitOrder(Map<String, Object> testData) throws IOException {

		@SuppressWarnings("unchecked")
		List<String> shoppingList = (List<String>) testData.get("products");

		ProductCatalogue productCatalogue = landingPage.loginApplication((testData.get("email")).toString(),
				(testData.get("password")).toString());
		productCatalogue.addProductToCart(shoppingList);
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.checkCart(driver, shoppingList);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.fillCheckoutInfo("222", "Wieczorek", "Poland".subSequence(0, "Poland".length()));
		ConfirmationPage confirmationPage = checkoutPage.placeOrder();
		Boolean matchText = confirmationPage.confirmation(driver);
		Assert.assertTrue(matchText);

	}


	
	@DataProvider
	public Object[][] getData() throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> testDataList = objectMapper.readValue(new File(filePath),
				new TypeReference<List<Map<String, Object>>>() {
				});
		Object[][] testDataArray = new Object[testDataList.size()][1];
		for (int i = 0; i < testDataList.size(); i++) {
			testDataArray[i][0] = testDataList.get(i);
		}

		return testDataArray;
	}

}
