package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.common.BaseClass;
import org.testng.annotations.Test;

public class GetSchemeInfoTests extends BaseClass {

    // Get scheme info from companies house
    @Test(dataProvider = "CompaniesHouse")
    public void getCHSchemeInfo() {
    }

    // Get scheme info from charities commission with charities house identifier
    @Test(dataProvider = "CharityCommissionWithCH")
    public void getCCSchemeInfoWithCH() {
    }


    @Test
    // Get scheme info from charities commission without charities house identifier
    public void getCCSchemeInfoWithoutCH() {
    }
}
