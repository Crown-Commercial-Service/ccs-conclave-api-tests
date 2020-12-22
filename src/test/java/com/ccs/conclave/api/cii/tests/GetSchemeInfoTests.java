package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.cii.response.SchemeInfoResponse;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;
import static com.ccs.conclave.api.common.SchemeRegistry.*;

public class GetSchemeInfoTests extends BaseClass {

    @Test
    public void getCompaniesHouseSchemeInfo() {
        OrgDataProvider orgData = new OrgDataProvider();
        SchemeInfo schemeInfo = orgData.getInfo(COMPANIES_HOUSE);
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(COMPANIES_HOUSE, orgData, response.as(SchemeInfoResponse.class));
    }

    @Test
    public void getCharityBaseSchemeInfoWithCompaniesHouse() {
        // Todo
    }
}
