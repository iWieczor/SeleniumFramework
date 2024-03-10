package iWieczor.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import iWieczor.pageobjects.LandingPage;

public class BaseTest {

    // Path for the PurchaseOrder.json file
    protected static String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "test" + File.separator + "java" + File.separator + "iWieczor" + File.separator + "data"
            + File.separator + "PurchaseOrder.json";

    // WebDriver and LandingPage objects
    public WebDriver driver;
    public LandingPage landingPage;

    // Method to initialize the WebDriver
    public WebDriver initializeDriver() throws IOException {
        // Load properties from GlobalData.properties file
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "\\src\\main\\java\\iWieczor\\resources\\GlobalData.properties");
        prop.load(fis);

        // Get browser name from system property or properties file
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
                : prop.getProperty("browser");

        // Initialize the WebDriver based on the browser
        if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }

        // Maximize the window and set implicit wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        return driver;
    }

    // Method to capture a screenshot and save it to the reports directory
    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(
                System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";

    }

    // Method to launch the application before each test method
    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    // Method to close the WebDriver after each test method
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
