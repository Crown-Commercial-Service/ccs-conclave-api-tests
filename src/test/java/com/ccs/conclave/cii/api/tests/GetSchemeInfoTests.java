package com.ccs.conclave.cii.api.tests;

import com.ccs.comclave.cii.api.listeners.BaseClass;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class GetSchemeInfoTests extends BaseClass {

    @Test
    public void myFirstTest(){
         String response = "Hello";
        Reporter.log("Response is: " + response, true);
    }

}
