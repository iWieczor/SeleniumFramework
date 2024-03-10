package iWieczor.Tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import iWieczor.TestComponents.BaseTest;
import iWieczor.TestComponents.Retry;
import iWieczor.pageobjects.ProductCatalogue;

public class LoginTest extends BaseTest {
    String login = "iwieczorek@iwie.com";
    String password = "Testtest1!";

    @Test(groups = { "Login" }, retryAnalyzer = Retry.class)
    public void validLogin() {
        // Perform a valid login
        ProductCatalogue productCatalogue = landingPage.loginApplication(login, password);

        // Assert that the login was successful by checking the toast message
        Assert.assertEquals(landingPage.getToastMessage(), "Login Successfully");

        // Dismiss the toast message
        landingPage.toastDisappear();

        // Sign out and assert the sign-out toast message
        productCatalogue.signOut(driver);
        Assert.assertEquals(landingPage.getToastMessage(), "Logout Successfully");

        // Dismiss the toast message
        landingPage.toastDisappear();
    }

    @Test(groups = { "Login" }, retryAnalyzer = Retry.class)
    public void invalidCredentials() {
        // Perform a login with empty and invalid credentials
        List<String> errorMessages = landingPage.invalidEmailText();

        // Expected error messages for empty and invalid credentials
        String expectedEmptyEmailError = "*Email is required";
        String expectedEmptyPasswordError = "*Password is required";
        String expectedInvalidEmailError = "*Enter Valid Email";

        // Assert that the error messages match the expected ones
        Assert.assertEquals(errorMessages.get(0), expectedEmptyEmailError);
        Assert.assertEquals(errorMessages.get(1), expectedEmptyPasswordError);
        Assert.assertEquals(errorMessages.get(2), expectedInvalidEmailError);
    }

    @Test(groups = { "Login" }, retryAnalyzer = Retry.class)
    public void invalidLogin() {
        // Perform an invalid login and assert the error message
        landingPage.loginApplication(login, "invalid password");
        Assert.assertEquals(landingPage.getToastMessage(), "Incorrect email or password.");
    }
}
