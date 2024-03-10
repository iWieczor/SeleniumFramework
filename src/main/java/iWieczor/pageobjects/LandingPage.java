package iWieczor.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import iWieczor.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

    WebDriver driver;

    // Constructor to initialize the WebDriver and set up PageFactory
    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // WebElements representing email, password, submit button, and toast message
    @FindBy(id = "userEmail")
    WebElement emailEle;
    @FindBy(id = "userPassword")
    WebElement passwordEle;
    @FindBy(id = "login")
    WebElement submit;
    @FindBy(css = "#toast-container")
    WebElement ToastMessage;

    // Method to log in to the application and return a ProductCatalogue object
    public ProductCatalogue loginApplication(String email, String password) {
        emailEle.sendKeys(email);
        passwordEle.sendKeys(password);
        submit.click();
        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
        return productCatalogue;
    }

    // Method to get the text of the toast message
    public String getToastMessage() {
        waitForWebElementToAppear(ToastMessage);
        return ToastMessage.getText();
    }

    // Method to wait for the toast message to disappear
    public void toastDisappear() {
        waitForWebElementToDisappear(ToastMessage);
    }

    // Method to navigate to the landing page
    public void goTo() {
        driver.get("https://rahulshettyacademy.com/client");
    }

    // Method to get a list of error messages for invalid login attempts
    public List<String> invalidEmailText() {
        submit.click();

        // Get text for empty email and password
        String emptyEmailText = (driver.findElement(By.cssSelector("div[class='form-group'] div[class='invalid-feedback'] div"))).getText();
        String emptyPasswordText = (driver.findElement(By.cssSelector("div[class='form-group mb-4'] div[class='invalid-feedback'] div"))).getText();

        // Enter an invalid email and click submit
        emailEle.sendKeys("invalid Email");
        submit.click();

        // Get text for invalid email
        String invalidEmailText = (driver.findElement(By.cssSelector("div[class='form-group'] div[class='invalid-feedback'] div"))).getText();

        // Create a list to store error messages
        List<String> loginErrorsMsgList = new ArrayList<>();
        loginErrorsMsgList.add(emptyEmailText);
        loginErrorsMsgList.add(emptyPasswordText);
        loginErrorsMsgList.add(invalidEmailText);

        return loginErrorsMsgList;
    }
}
