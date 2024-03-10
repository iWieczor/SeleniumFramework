package iWieczor.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import iWieczor.AbstractComponents.AbstractComponent;

public class ConfirmationPage extends AbstractComponent {

    WebDriver driver;

    // Constructor to initialize the WebDriver
    public ConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // Method to check if the confirmation message matches the expected message
    public Boolean confirmation(WebDriver driver) {
        // Get the text of the confirmation message
        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();

        // Check if the confirmation message matches the expected message
        Boolean match = confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER.");
        return match;
    }
}
