package iWieczor.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import iWieczor.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

    // ExtentTest object to represent the current test
    ExtentTest test;

    // ExtentReports object to manage the overall report
    ExtentReports extent = ExtentReporterNG.getReportObject();

    // ThreadLocal to handle parallel test execution
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test in the extent report for the current test method
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        ITestListener.super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log test success status in the extent report
        extentTest.get().log(Status.PASS, "Test Passed");
        ITestListener.super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log test failure status in the extent report
        extentTest.get().fail(result.getThrowable());

        // Retrieve the WebDriver instance from the BaseTest class using reflection
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        // Capture a screenshot and attach it to the extent report
        String filePath = null;
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(), driver);
        } catch (IOException e) {
            e.printStackTrace();
        }
        extentTest.get().addScreenCaptureFromPath(filePath.replace("\\", "/"), result.getMethod().getMethodName());
        ITestListener.super.onTestFailure(result);
    }

    // Other methods for skipped, failed within success percentage, failed with timeout

    @Override
    public void onStart(ITestContext context) {
        // Execute when the test suite starts
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the extent report to generate the final report
        extent.flush();
        ITestListener.super.onFinish(context);
    }
}
