package com.ccs.conclave.api.common;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int counter = 1;
    int retryMaxLimit = 2;

    public boolean retry(ITestResult result) {
        if (counter < retryMaxLimit) {
            counter++;
            return true;
        }
        return false;
    }

}