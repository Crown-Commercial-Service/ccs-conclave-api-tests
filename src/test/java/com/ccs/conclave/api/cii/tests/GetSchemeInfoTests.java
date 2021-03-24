package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.*;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class GetSchemeInfoTests extends BaseClass {

    @Test
    public void getCompaniesHouseSchemeInfo() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE);
        Response response = getSchemeInfo(COMPANIES_HOUSE, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test //Bug: CON-470 FIXED
    public void getDunsSchemeInfoWithoutAdditionalIdentifier() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(DUN_AND_BRADSTREET);
        Response response = getSchemeInfo(DUN_AND_BRADSTREET, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test
    public void getDunsSchemeInfoWithCOH() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(DUN_AND_BRADSTREET_WITH_COH);
        Response response = getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test //Bug: CON-470 -FIXED
    public void getDunsSchemeInfoWithCOHAndCHC() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);
        Response response = getSchemeInfo(DUN_AND_BRADSTREET_WITH_CHC_AND_COH, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test // Bug: CON-450 -FIXED
    public void getGBCharitiesSchemeInfo() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(CHARITIES_COMMISSION);
        Response response = getSchemeInfo(CHARITIES_COMMISSION, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

//    @Test // Bug: CON-452 -FIXED
//    public void getGBCharitiesSchemeInfoWithTwoCOH() {
//        SchemeInfo expSchemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_TWO_COH);
//        Response response = getSchemeInfo(CHARITIES_COMMISSION_WITH_TWO_COH, expSchemeInfo.getIdentifier().getId());
//        verifyGetSchemeInfoResponse(expSchemeInfo, response);
//    }

    @Test // Bug: CON-452 -FIXED
    public void getGBCharitiesSchemeInfoWithCOHAndSC() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(CHARITIES_COMMISSION_WITH_COH_AND_SC);
        Response response = getSchemeInfo(CHARITIES_COMMISSION_WITH_COH_AND_SC, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test // Bug: CON-452 -FIXED
    public void getGBCharitiesSchemeInfoWithKnownAndUnknownIdentifiers() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(CHARITIES_COMMISSION_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS);
        Response response = getSchemeInfo(CHARITIES_COMMISSION_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test // Bug: CON-489 -FIXED
    public void getNorthernCharitySchemeInfo() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(NORTHERN_CHARITY);
        Response response = getSchemeInfo(NORTHERN_CHARITY, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test // Bug: CON-439
    public void getNorthernCharitySchemeInfoWithCOH() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(NORTHERN_CHARITY_WITH_COH);
        Response response = getSchemeInfo(NORTHERN_CHARITY_WITH_COH, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test // Bug: CON-489 -FIXED
    public void getScottishCharitySchemeInfo() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(SCOTLAND_CHARITY);
        Response response = getSchemeInfo(SCOTLAND_CHARITY, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test // Bug: CON-452 -FIXED
    public void getScottishCharitySchemeInfoWithCOHAndCHC() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(SCOTLAND_CHARITY_WITH_CHC_COH);
        Response response = getSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test
    public void getSchemeInfoWithInvalidSchemeOrID() {
        String identifier = getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE).getIdentifier().getId();
        Response response = getSchemeInfo(INVALID_SCHEME, identifier);
        verifyInvalidIdResponse(response);

        // with invalid identifier Bug: CON-450 -FIXED
        response = getSchemeInfo(COMPANIES_HOUSE, "00000000");
        verifyInvalidIdResponse(response);
    }

    @Test // Bug: CON-533 - FIXED
    //Note : Duplicate check for already registered additional identifier
    public void getDuplicateWithAdditionalIdentifier() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(DUN_AND_BRADSTREET_WITH_COH);

        Response response = getSchemeInfo(COMPANIES_HOUSE, expSchemeInfo.getAdditionalIdentifiers().get(0).getId());
        postSchemeInfo(response.asString());

        response = getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, expSchemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(response);
    }

    @Test // Bug: CON-662 - Fixed
    public void getSchemeInfoWithAlreadyRegIDAndInvalidScheme() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE);

        Response response = getSchemeInfo(COMPANIES_HOUSE, expSchemeInfo.getIdentifier().getId());
        postSchemeInfo(response.asString());

        response = getSchemeInfo(NORTHERN_CHARITY, expSchemeInfo.getIdentifier().getId());
        verifyInvalidIdResponse(response);
    }

    @Test // Bug: CON-662 - Fixed
    public void getSchemeInfoWithSpaceInSchemeID() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE);

        Response response = getSchemeInfo(COMPANIES_HOUSE, expSchemeInfo.getIdentifier().getId());
        postSchemeInfo(response.asString());

        String IdWithSpace = expSchemeInfo.getIdentifier().getId() + " ";
        response = getSchemeInfo(COMPANIES_HOUSE, IdWithSpace);
        verifyResponseCodeForSuccess(response);
    }

    @Test
    public void getInactiveDunsSchemeInfo() {
        Response response = getSchemeInfo(DUN_AND_BRADSTREET, "238637735");
        verifyInvalidIdResponse(response);
    }

    @Test
    public void getInactiveNICCharitiesSchemeInfo() {
        Response response = getSchemeInfo(NORTHERN_CHARITY, "100203");
        verifyInvalidIdResponse(response);
    }

    @Test
    public void getActiveSCCharitiesWithInactiveAndActiveAdditionalIDInfo() {
        SchemeInfo expSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(SCOTLAND_CHARITY_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS);
        Response response = getSchemeInfo(SCOTLAND_CHARITY_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS, expSchemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(expSchemeInfo, response);
    }

    @Test
    public void getInactiveCompaniesHouseSchemeInfo() {
        Response response = getSchemeInfo(COMPANIES_HOUSE, "7773187");
        verifyInvalidIdResponse(response);
    }

}



