package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import org.testng.annotations.Test;

public class GetSchemeInfoTests extends BaseClass {

    RestRequests restRequests = new RestRequests();

    // Get scheme info from companies house
    @Test(dataProvider = "CompaniesHouse")
    public void getCHSchemeInfo() {
        restRequests.getApi("").then()
                            //.log().headers();
//                            .log().body();
                            //.log().cookies();
                            //.log().all();
                            //.log().status();
                            .log().ifError();
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
