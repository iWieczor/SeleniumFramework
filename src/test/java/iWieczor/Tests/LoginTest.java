package iWieczor.Tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import iWieczor.TestComponents.BaseTest;
import iWieczor.pageobjects.ProductCatalogue;

public class LoginTest extends BaseTest {
	String login = "iwieczorek@iwie.com";
	String password = "Testtest1!";

	@Test
	public void validLogin() {
		ProductCatalogue productCatalogue = landingPage.loginApplication(login, password);
		Assert.assertEquals(landingPage.getToastMessage(), "Login Successfully");
		landingPage.toastDisappear();
		productCatalogue.signOut(driver);
		Assert.assertEquals(landingPage.getToastMessage(), "Logout Successfully");
	}

	@Test
	public void invalidCredentials() {

		List<String> errorMessages = landingPage.invalidEmailText();

		String expectedEmptyEmailError = "*Email is required";
		String expectedEmptyPasswordError = "*Password is required";
		String expectedInvalidEmailError = "*Enter Valid Email";

		Assert.assertEquals(errorMessages.get(0), expectedEmptyEmailError);
		Assert.assertEquals(errorMessages.get(1), expectedEmptyPasswordError);
		Assert.assertEquals(errorMessages.get(2), expectedInvalidEmailError);
	}

	@Test
	public void invalidLogin() {
		landingPage.loginApplication(login, "invalid password");
		Assert.assertEquals(landingPage.getToastMessage(), "Incorrect email or password.");
	}

}
