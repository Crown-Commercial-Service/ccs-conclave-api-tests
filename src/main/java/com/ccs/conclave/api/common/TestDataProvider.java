package com.ccs.conclave.api.common;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
    @DataProvider(name = "CharityCommissionWithCH")
    public Object[][] ccWithCH(){
        return new Object[][] {{"1234"}, {"CompanyName"}};
    }
}
