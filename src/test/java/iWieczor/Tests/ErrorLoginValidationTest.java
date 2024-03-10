package iWieczor.Tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import iWieczor.TestComponents.BaseTest;
import iWieczor.pageobjects.CartPage;
import iWieczor.pageobjects.ProductCatalogue;

public class ErrorLoginValidationTest extends BaseTest{
	
	@Test
	public void errorProduct() throws IOException {

		List<String> shoppingList = Arrays.asList("IPHONE 133 PRO");
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("iwieczorek2@iwie.com", "Testtest1!");
		productCatalogue.addProductToCart(shoppingList);
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.checkCart(driver, shoppingList);
		Assert.assertFalse(match);


	}
}