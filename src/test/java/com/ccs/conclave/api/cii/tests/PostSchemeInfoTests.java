package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.*;
import static com.ccs.conclave.api.cii.data.RequestPayloads.*;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.deleteOrganisation;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class PostSchemeInfoTests extends BaseClass {

    private final static Logger logger = Logger.getLogger(PostSchemeInfoTests.class);

    @Test
    public void postSchemeInfoCOH_NoAddIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(COMPANIES_HOUSE);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, getSchemeRes); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        logger.info("verifying duplicate organisation registration.....");

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        logger.info("verifying duplicate organisation registration in Get Scheme info.....");

        // verify duplicate check for Get call
        getSchemeRes = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        logger.info("deleting org. identifier after test..");

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(schemeInfo.getIdentifier().getId());
    }

    @Test // NOTE:- Duplicate check for additional identifiers are added in this test
    public void postSchemeInfoDUNS_With_MultipleAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, getSchemeRes); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        // verify duplicate check for additional identifier (COH in DUNS)
        getSchemeRes = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // verify duplicate check for additional identifier (CHC in DUNS)
        getSchemeRes = RestRequests.getSchemeInfo(CHARITIES_COMMISSION, schemeInfo.getAdditionalIdentifiers().get(1).getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // verify duplicate check for Get call
        getSchemeRes = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(schemeInfo.getIdentifier().getId());
    }

    @Test
    public void postSchemeInfoCHC_With_MultipleAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_TWO_COH);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_TWO_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, getSchemeRes); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        // verify duplicate check for Get call
        getSchemeRes = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_TWO_COH, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(schemeInfo.getIdentifier().getId());
    }

    @Test
    public void postSchemeInfoSC_With_NoAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = RestRequests.getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, getSchemeRes); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        // verify duplicate check for Get call
        getSchemeRes = RestRequests.getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(schemeInfo.getIdentifier().getId());
    }

    @Test
    public void postSchemeInfoNIC_With_OneAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(NORTHERN_CHARITY_WITH_COH);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = RestRequests.getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, getSchemeRes); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        // verify duplicate check for Get call
        getSchemeRes = RestRequests.getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(schemeInfo.getIdentifier().getId());
    }

    @Test
    public void postSchemeInfoWithInvalidPrimaryScheme() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(COMPANIES_HOUSE);

        //Modify the response to update Valid Scheme of Primary Scheme with Invalid Scheme
        String responseStr = getSchemeInfoWithInvalidPrimaryScheme(COMPANIES_HOUSE);
        Response response = RestRequests.postSchemeInfo(responseStr);
        //Verify the response with Invalid Primary Scheme
        verifyInvalidSchemeResponse(response);
    }

    @Test
    public void postSchemeInfoWithInvalidPrimaryID() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WALES);

        // Modify the response to update Valid Identifier of Primary Scheme with Invalid Identifier
        String responseStr = getSchemeInfoWithInvalidPrimaryID(DUN_AND_BRADSTREET_WALES);
        Response response = RestRequests.postSchemeInfo(responseStr);
        // Verify the response with Invalid Primary Identifier
        verifyInvalidIdResponse(response);
    }

    @Test //CON-543 - Fixed
    public void postSchemeInfoWithNoAdditionalIdentifierSection() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(NORTHERN_CHARITY_WITH_COH);
        String responseStr = getSchemeInfoWithoutAddIdentifierSection(NORTHERN_CHARITY_WITH_COH);

        //Modify the response to Remove The Additional Identifier Section
        SchemeInfo expectedSchemeInfo = getInfoWithoutAddIdentifiers(NORTHERN_CHARITY_WITH_COH);
        Response response = RestRequests.postSchemeInfo(responseStr);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);
        deleteOrganisation(schemeInfo.getIdentifier().getId());
    }

    @Test
    public void postSchemeInfoWithInvalidAdditionalIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);
        SchemeInfo expectedSchemeInfo = getInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);

        // Modify the response to pass Invalid Additional Identifier
        String responseStr = getSchemeInfoWithInvalidAddIdentifier(DUN_AND_BRADSTREET_WITH_COH);
        Response response = RestRequests.postSchemeInfo(responseStr);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(schemeInfo.getIdentifier().getId());
    }

    @Test
    public void postSchemeInfoWithAddIdentifierOfAnotherScheme() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);
        SchemeInfo expectedSchemeInfo = getInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);

        // Modify The Response to Update Additional Identifier With Valid Additional Identifier of Another Scheme
        String responseStr = getSchemeInfoWithAddIdentifierofAnotherScheme(DUN_AND_BRADSTREET_WITH_COH);
        Response response = RestRequests.postSchemeInfo(responseStr);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(schemeInfo.getIdentifier().getId());
    }
}


