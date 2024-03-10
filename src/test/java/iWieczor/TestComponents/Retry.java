package iWieczor.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    int count = 0;  // Counter to keep track of the number of retries
    int maxTry = 1; // Maximum number of retries allowed

    @Override
    public boolean retry(ITestResult result) {
        // Check if the count is less than the maximum number of retries
        if (count < maxTry) {
            count++; // Increment the retry count
            return true; // Retry the test
        }
        return false; // Do not retry the test if the maximum retries are reached
    }
}
