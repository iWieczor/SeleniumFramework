package iWieczor.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

    // Method to get the ExtentReports object
    public static ExtentReports getReportObject() {
        // Define the path for the Extent report
        String path = System.getProperty("user.dir") + "\\reports\\index.html";

        // Create an ExtentSparkReporter object and configure it
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Result");
        reporter.config().setDocumentTitle("Test Result");

        // Create an ExtentReports object and attach the ExtentSparkReporter
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);

        // Set system information for the report
        extent.setSystemInfo("Tester", "Igor Wieczorek");

        // Return the ExtentReports object
        return extent;
    }
}
