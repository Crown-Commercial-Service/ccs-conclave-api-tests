package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
public class GetSchemeInfoTests extends BaseClass {

    @Test
    public void getCompaniesHouseSchemeInfo() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(COMPANIES_HOUSE);
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test
    public void getDunsSchemeInfoWithoutAdditionalIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET);
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test
    public void getDunsSchemeInfoWithCompaniesHouse() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    //@Test
    public void getNorthernCharitySchemeInfo() {
        // Todo
    }

    //@Test
    public void getScottishCharitySchemeInfo() {
        // Todo
    }

    @Test
    public void getSchemeInfoErrorCode400() {
        String identifier = OrgDataProvider.getInfo(COMPANIES_HOUSE).getIdentifier().getId();
        Response response = RestRequests.getSchemeInfo(INVALID_SCHEME, identifier);
        verifyInvalidGetSchemeResponse(400, response);
    }

    @Test
    public void getSchemeInfoErrorCode401() {
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, "00000000");
        verifyInvalidGetSchemeResponse(401, response);
    }
}
