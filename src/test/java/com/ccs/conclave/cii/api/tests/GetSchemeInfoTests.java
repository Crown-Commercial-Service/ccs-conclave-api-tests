package com.ccs.conclave.cii.api.tests;

import com.ccs.conclave.api.ExtentReports.BaseClass;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class GetSchemeInfoTests extends BaseClass {

    @Test
    public void myFirstTests() {
        String response = "Hello";
        Reporter.log("Response is: " + response, true);
    }
}
