package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyResponses.verifyGetSchemeInfoResponse;
public class GetSchemeInfoTests extends BaseClass {

    @Test
    public void getCompaniesHouseSchemeInfo() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(COMPANIES_HOUSE);
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test //Bug: CON-470
    public void getDunsSchemeInfoWithoutAdditionalIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET);
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test
    public void getDunsSchemeInfoWithCOH() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test //Bug: CON-470
    public void getDunsSchemeInfoWithCOHAndCHC() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-450
    public void getGBCharitiesSchemeInfo() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION);
        Response response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-452
    public void getGBCharitiesSchemeInfoWithTwoAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_ADD_IDENTIFIER);
        Response response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_ADD_IDENTIFIER, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-452
    public void getGBCharitiesSchemeInfoWithSC() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_SC);
        Response response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_SC, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test //Bug: CON-489
    public void getNorthernCharitySchemeInfo() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(NORTHERN_CHARITY);
        Response response = RestRequests.getSchemeInfo(NORTHERN_CHARITY, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test
    public void getNorthernCharitySchemeInfoWithAddIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(NORTHERN_CHARITY_WITH_ADD_IDENTIFIER);
        Response response = RestRequests.getSchemeInfo(NORTHERN_CHARITY_WITH_ADD_IDENTIFIER, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-489
    public void getScottishCharitySchemeInfo() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY);
        Response response = RestRequests.getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-452
    public void getScottishCharitySchemeInfoWithAddIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY_WITH_ADD_IDENTIFIER);
        Response response = RestRequests.getSchemeInfo(SCOTLAND_CHARITY_WITH_ADD_IDENTIFIER, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test
    public void getSchemeInfoErrorCode400() {
        String identifier = OrgDataProvider.getInfo(COMPANIES_HOUSE).getIdentifier().getId();
        Response response = RestRequests.getSchemeInfo(INVALID_SCHEME, identifier);
        verifyInvalidGetSchemeResponse(400, response);
    }

    @Test // Bug: CON-450
    public void getSchemeInfoErrorCode401() {
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, "00000000");
        verifyInvalidGetSchemeResponse(401, response);
    }
}
