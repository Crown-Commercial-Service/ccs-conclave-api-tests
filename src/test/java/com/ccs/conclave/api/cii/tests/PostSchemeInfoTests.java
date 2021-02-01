package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RequestTestEndpoints;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;

public class PostSchemeInfoTests extends BaseClass {

    private final static Logger logger = Logger.getLogger(PostSchemeInfoTests.class);

    @Test
    public void postSchemeInfoCOH_NoAddIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(COMPANIES_HOUSE);

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);

        logger.info("verifying duplicate organisation registration.....");

        // verify duplicate check for POST call
        //response = RestRequests.postSchemeInfo(response.asString());
        //verifyDuplicateResourceResponse(409, response);

        logger.info("verifying duplicate organisation registration in Get Scheme info.....");

        // verify duplicate check for Get call
        // response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        // verifyDuplicateResourceResponse(409, response);

        logger.info("deleting org. identifier after test..");

        // Delete Database entry if the Org. is already registered
        RequestTestEndpoints.deleteOrgIdentifiers(schemeInfo.getIdentifier().getId());
    }

    @Test // NOTE:- Duplicate check for additional identifiers are added in this test
    public void postSchemeInfoDUNS_With_MultipleAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);

        // verify duplicate check for POST call
        //response = RestRequests.postSchemeInfo(response.asString());
        //verifyDuplicateResourceResponse(409, response);

        // verify duplicate check for additional identifier (COH in DUNS)
        // response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        // verifyDuplicateResourceResponse(409, response);

        // verify duplicate check for additional identifier (CHC in DUNS)
        // response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION, schemeInfo.getAdditionalIdentifiers().get(1).getId());
        // verifyDuplicateResourceResponse(409, response);

        // verify duplicate check for Get call
        // response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC, schemeInfo.getIdentifier().getId());
        // verifyDuplicateResourceResponse(409, response);

        // Delete Database entry if the Org. is already registered
        RequestTestEndpoints.deleteOrgIdentifiers(schemeInfo.getIdentifier().getId());
    }

    @Test
    public void postSchemeInfoCHC_With_MultipleAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_TWO_COH);

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_TWO_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);

        // verify duplicate check for POST call
        //response = RestRequests.postSchemeInfo(response.asString());
        //verifyDuplicateResourceResponse(409, response);

        // verify duplicate check for Get call
        // response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_TWO_COH, schemeInfo.getIdentifier().getId());
        // verifyDuplicateResourceResponse(409, response);

        // Delete Database entry if the Org. is already registered
        RequestTestEndpoints.deleteOrgIdentifiers(schemeInfo.getIdentifier().getId());
    }

    @Test
    public void postSchemeInfoSC_With_NoAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY);

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);

        // verify duplicate check for POST call
        //response = RestRequests.postSchemeInfo(response.asString());
        //verifyDuplicateResourceResponse(409, response);

        // verify duplicate check for Get call
        // response = RestRequests.getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getIdentifier().getId());
        // verifyDuplicateResourceResponse(409, response);

        // Delete Database entry if the Org. is already registered
        RequestTestEndpoints.deleteOrgIdentifiers(schemeInfo.getIdentifier().getId());
    }
    @Test
    public void postSchemeInfoNIC_With_OneAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(NORTHERN_CHARITY_WITH_COH);

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);

        // verify duplicate check for POST call
        //response = RestRequests.postSchemeInfo(response.asString());
        //verifyDuplicateResourceResponse(409, response);

        // verify duplicate check for Get call
        // response = RestRequests.getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        // verifyDuplicateResourceResponse(409, response);

        // Delete Database entry if the Org. is already registered
        RequestTestEndpoints.deleteOrgIdentifiers(schemeInfo.getIdentifier().getId());
    }
}
