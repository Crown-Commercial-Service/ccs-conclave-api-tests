package com.ccs.conclave.api.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.data.SchemeRegistry;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.report.ExtentManager;
import com.ccs.conclave.api.report.ExtentTestManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.LogStatus;

import static com.ccs.conclave.api.cii.requests.RestRequests.*;

public class BaseClass {
    private final static Logger logger = Logger.getLogger(BaseClass.class);
    protected static Properties properties;

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

    @AfterSuite
    protected void clearTestData() {
        logger.info("Clearing test data...");
        for(SchemeRegistry registry : SchemeRegistry.values()) {
            SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(registry);

            // Delete Database entry if the Org. is already registered
            deleteOrganisationWithIdTestEndPt(schemeInfo.getIdentifier().getId());
            for (Identifier id : schemeInfo.getAdditionalIdentifiers()) {
                deleteOrganisationWithIdTestEndPt(id.getId());
            }
        }
        logger.info("Cleared all test data...");
    }

    @BeforeSuite
    protected void loadTestData() throws IOException {
        InputStream inputStream;
        System.setProperty("mock.tests", "True");
        if (System.getProperty("mock.tests").equals("True")) {
            logger.info("Loading mock endpoints...");
            inputStream = getClass().getClassLoader().getResourceAsStream("mockEndpoints.properties");
            OrgDataProvider.initMockTestDataProvider();
        } else {
            logger.info("Loading real endpoints...");
            inputStream = getClass().getClassLoader().getResourceAsStream("realEndpoints.properties");
            OrgDataProvider.initRegistryTestDataProvider();
        }
        if (inputStream != null) {
            properties = new Properties();
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file not found in the classpath!");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}