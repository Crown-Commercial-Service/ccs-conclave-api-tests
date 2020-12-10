package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;
import static com.ccs.conclave.api.common.SchemeRegistry.*;

public class GetSchemeInfoTests {

    @Test
    public void getCompaniesHouseSchemeInfo() throws JsonProcessingException {
        OrgDataProvider orgData = new OrgDataProvider();
        SchemeInfo schemeInfo = orgData.getInfo(COMPANIES_HOUSE);
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        orgData.setSchemeInfoResponse(response.asString());
        verifyGetSchemeInfoResponse(orgData);
    }

    @Test
    public void getCCSchemeInfoWithCH() {
    }

    @Test
    public void getCCSchemeInfoWithoutCH() {
    }
}
