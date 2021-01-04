package com.ccs.conclave.api.common;

import java.lang.reflect.Method;

import com.ccs.conclave.api.report.ExtentManager;
import com.ccs.conclave.api.report.ExtentTestManager;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {

    @BeforeMethod
    public void beforeMethod(Method method) {
        ExtentTestManager.startTest(method.getName());
        PropertyConfigurator.configure("log4j.properties");
    }

    @AfterMethod
    protected void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } else {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
        }

        ExtentManager.getReporter().endTest(ExtentTestManager.getTest());
        ExtentManager.getReporter().flush();
    }
}