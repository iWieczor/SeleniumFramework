package iWieczor.Tests;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import iWieczor.TestComponents.BaseTest;
import iWieczor.pageobjects.CartPage;
import iWieczor.pageobjects.CheckoutPage;
import iWieczor.pageobjects.ConfirmationPage;
import iWieczor.pageobjects.OrderPage;
import iWieczor.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{

	List<String> shoppingList = Arrays.asList("IPHONE 13 PRO");
	
	@Test
	public void submitOrder() throws IOException {

		
		ProductCatalogue productCatalogue = landingPage.loginApplication("iwieczorek@iwie.com", "Testtest1!");
		productCatalogue.addProductToCart(shoppingList);
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.checkCart(driver, shoppingList);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.fillCheckoutInfo("222", "Wieczorek", "Poland");
		ConfirmationPage confirmationPage = checkoutPage.placeOrder();
		Boolean matchText = confirmationPage.confirmation(driver);
		Assert.assertTrue(matchText);

	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("iwieczorek@iwie.com", "Testtest1!");
		OrderPage orderPage = productCatalogue.goToOrders(driver);
		Assert.assertTrue(orderPage.checkOrder(driver, shoppingList));
	}
	
}