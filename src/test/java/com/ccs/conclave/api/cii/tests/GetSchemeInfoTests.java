package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.common.SchemeRegistry.*;

public class GetSchemeInfoTests extends BaseClass {

    @Test
    public void getCompaniesHouseSchemeInfo() {
        OrgDataProvider orgData = new OrgDataProvider();
        OrgDataProvider.SchemeInfo schemeInfo = orgData.getInfo(COMPANIES_HOUSE);
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier());
        // response deserialization
    }

    @Test
    public void getCCSchemeInfoWithCH() {

    }


    @Test
    public void getCCSchemeInfoWithoutCH() {
    }
}
