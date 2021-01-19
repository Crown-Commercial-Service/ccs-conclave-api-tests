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

public class PostSchemeIdTests extends BaseClass {

    private final static Logger logger = Logger.getLogger(PostSchemeIdTests.class);

    @Test
    public void postSchemeInfoCOH_NoAddIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(COMPANIES_HOUSE);

        // Delete Database entry if the Org. is already registered
        RequestTestEndpoints.deleteOrgIdentifiers(schemeInfo.getIdentifier().getId());

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);

        // verify duplicate check for POST call
        response = RestRequests.postSchemeInfo(response.asString());
        verifyDuplicateResourceResponse(409, response);

        // Todo: verify duplicate check for Get call
        response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyDuplicateResourceResponse(409, response);

        // Delete Database entry if the Org. is already registered
        RequestTestEndpoints.deleteOrgIdentifiers(schemeInfo.getIdentifier().getId());
    }


   // @Test
    public void postSchemeInfoDUNS_With_AddIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC, schemeInfo.getIdentifier().getId());
        // verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
    }

  //  @Test
    public void postSchemeInfoCHC_With_MultipleAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC, schemeInfo.getIdentifier().getId());
        // verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
    }

}
