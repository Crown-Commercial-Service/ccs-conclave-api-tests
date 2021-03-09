package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class GetSchemeInfoTests extends BaseClass {

    @Test
    public void getCompaniesHouseSchemeInfo() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(COMPANIES_HOUSE);
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test //Bug: CON-470 FIXED
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

    @Test //Bug: CON-470 -FIXED
    public void getDunsSchemeInfoWithCOHAndCHC() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-450 -FIXED
    public void getGBCharitiesSchemeInfo() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION);
        Response response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-452 -FIXED
    public void getGBCharitiesSchemeInfoWithTwoCOH() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_TWO_COH);
        Response response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_TWO_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-452 -FIXED
    public void getGBCharitiesSchemeInfoWithSC() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_SC);
        Response response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_SC, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-452 -FIXED
    public void getGBCharitiesSchemeInfoWithKnownAndUnknownIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS);
        Response response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-489 -FIXED
    public void getNorthernCharitySchemeInfo() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(NORTHERN_CHARITY);
        Response response = RestRequests.getSchemeInfo(NORTHERN_CHARITY, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-439
    public void getNorthernCharitySchemeInfoWithCOH() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(NORTHERN_CHARITY_WITH_COH);
        Response response = RestRequests.getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-489 -FIXED
    public void getScottishCharitySchemeInfo() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY);
        Response response = RestRequests.getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test // Bug: CON-452 -FIXED
    public void getScottishCharitySchemeInfoWithCOHAndCHC() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        Response response = RestRequests.getSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
    }

    @Test
    public void getSchemeInfoWithInvalidSchemeOrID() {
        String identifier = OrgDataProvider.getInfo(COMPANIES_HOUSE).getIdentifier().getId();
        Response response = RestRequests.getSchemeInfo(INVALID_SCHEME, identifier);
        verifyInvalidIdResponse(response);

        // with invalid identifier Bug: CON-450 -FIXED
        response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, "00000000");
        verifyInvalidIdResponse(response);
    }

    @Test // Bug: CON-533 - FIXED
    //Note : Duplicate check for already registered additional identifier
    public void getDuplicateWithAdditionalIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);

        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        RestRequests.postSchemeInfo(response.asString());

        response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(response);
    }

    @Test // Bug: CON-662 - Fixed
    public void getSchemeInfoWithAlreadyRegIDAndInvalidScheme() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(COMPANIES_HOUSE);

        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        RestRequests.postSchemeInfo(response.asString());

        response = RestRequests.getSchemeInfo(NORTHERN_CHARITY, schemeInfo.getIdentifier().getId());
        verifyInvalidIdResponse(response);
    }

    @Test // Bug: CON-662 - Fixed
    public void getSchemeInfoWithSpaceInSchemeID() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(COMPANIES_HOUSE);

        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        RestRequests.postSchemeInfo(response.asString());

        String IdWithSpace = schemeInfo.getIdentifier().getId() + " ";
        response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, IdWithSpace);
        verifyResponseCodeForSuccess(response);
    }

    @Test
    public void getInactieDunsSchemeInfo() {
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET, "238637735");
        verifyInvalidIdResponse(response);
    }


}
