package com.ccs.conclave.api.cii.tests;

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
import static com.ccs.conclave.api.cii.requests.RestRequests.getSchemeInfo;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class PostSchemeInfoTests extends BaseClass {

    private final static Logger logger = Logger.getLogger(PostSchemeInfoTests.class);

    @Test
    public void postSchemeInfoCOH_NoAddIdentifier() {
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(COMPANIES_HOUSE);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, expectedSchemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes);

        logger.info("verifying duplicate organisation registration.....");

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        logger.info("verifying duplicate organisation registration in Get Scheme info.....");

        // verify duplicate check for Get call
        getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, expectedSchemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        logger.info("deleting org. identifier after test..");

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void postSchemeInfoDUNS_With_MultipleAddIdentifiers() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(DUN_AND_BRADSTREET_WITH_CHC_AND_COH, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        // verify duplicate check for additional identifier (COH in DUNS)
        getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // verify duplicate check for additional identifier (CHC in DUNS)
        getSchemeRes = getSchemeInfo(CHARITIES_COMMISSION, schemeInfo.getAdditionalIdentifiers().get(1).getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // verify duplicate check for Get call
        getSchemeRes = getSchemeInfo(DUN_AND_BRADSTREET_WITH_CHC_AND_COH, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void postSchemeInfoCHC_With_MultipleAddIdentifiers() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(CHARITIES_COMMISSION_WITH_COH_AND_SC);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(CHARITIES_COMMISSION_WITH_COH_AND_SC, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        // verify duplicate check for Get call
        getSchemeRes = getSchemeInfo(CHARITIES_COMMISSION_WITH_COH_AND_SC, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void postSchemeInfoSC_With_NoAddIdentifiers() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(SCOTLAND_CHARITY);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        // verify duplicate check for Get call
        getSchemeRes = getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void postSchemeInfoNIC_With_OneAddIdentifiers() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(NORTHERN_CHARITY_WITH_COH);
        SchemeInfo schemeInfoWithoutSF = getExpSchemeInfoWithoutSFIdentifier(NORTHERN_CHARITY_WITH_COH);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfoWithoutSF, getSchemeRes);

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        // verify duplicate check for Get call
        getSchemeRes = getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void postSchemeInfoSC_Without_Prefix() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(SCOTLAND_CHARITY);
        SchemeInfo schemeInfoWithoutSF = getExpSchemeInfoWithoutSFIdentifier(SCOTLAND_CHARITY);

        // Perform Get call to form the request payload for POST call
        String IdWithPrefix = schemeInfo.getIdentifier().getId();
        String IdWithoutPrefix = IdWithPrefix.replaceAll("[^0-9]", "");
        Response getSchemeRes = getSchemeInfo(SCOTLAND_CHARITY, IdWithoutPrefix);
        verifyGetSchemeInfoResponse(schemeInfoWithoutSF, getSchemeRes);

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        // verify duplicate check for Get call
        getSchemeRes = getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void postSchemeInfoNIC_Without_Prefix() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(NORTHERN_CHARITY_WITH_COH);

        // Perform Get call to form the request payload for POST call
        String IdWithPrefix = schemeInfo.getIdentifier().getId();
        String IdWithoutPrefix = IdWithPrefix.replaceAll("[^0-9]", "");
        Response getSchemeRes = getSchemeInfo(NORTHERN_CHARITY_WITH_COH, IdWithoutPrefix);
        verifyGetSchemeInfoResponse(schemeInfo, getSchemeRes); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for POST call
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyResponseCodeForDuplicateResource(postSchemeRes);

        // verify duplicate check for Get call
        getSchemeRes = getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }


    @Test
    public void postSchemeInfoWithInvalidPrimarySchemeOrID() throws InterruptedException {
        String responseStr = getSchemeInfoWithInvalidPrimaryScheme();
        Response response = RestRequests.postSchemeInfo(responseStr);
        verifyInvalidIdResponse(response);

        // Modify the response to update Valid Identifier of Primary Scheme with Invalid Identifier
        responseStr = getSchemeInfoWithInvalidPrimaryID();
        response = RestRequests.postSchemeInfo(responseStr);
        verifyInvalidIdResponse(response);
    }

    @Test
    public void postSchemeInfoWithInvalidAdditionalIdentifier() {
        SchemeInfo expectedSchemeInfo = getExpSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);

        // Modify the response to pass Invalid Additional Identifier
        String responseStr = getSchemeInfoWithInvalidAddIdentifier(DUN_AND_BRADSTREET_WITH_COH);
        Response response = RestRequests.postSchemeInfo(responseStr);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void postSchemeInfoWithAddIdentifierOfAnotherScheme() {
        SchemeInfo expectedSchemeInfo = getExpSchemeInfoWithoutAddIdentifiers(CHARITIES_COMMISSION_WITH_COH);

        // Modify The Response to Update Additional Identifier With Valid Additional Identifier of Another Scheme
        String responseStr = getSchemeInfoWithAddIdentifierOfAnotherScheme(CHARITIES_COMMISSION_WITH_COH);
        Response response = RestRequests.postSchemeInfo(responseStr);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test // CON-543 - Fixed
    public void postSchemeInfoWithNoAdditionalIdentifierSection() throws InterruptedException {
        SchemeInfo expectedSchemeInfo = getExpSchemeInfoWithoutAddIdentifiers(CHARITIES_COMMISSION_WITH_COH);

        // Modify the response to Remove The Additional Identifier Section
        String responseStr = getSchemeInfoWithoutAddIdentifierSection(CHARITIES_COMMISSION_WITH_COH);
        Response response = RestRequests.postSchemeInfo(responseStr);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);
        deleteOrganisation(getCCSOrgId());
    }
}


