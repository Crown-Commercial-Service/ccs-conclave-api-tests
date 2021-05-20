package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.*;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.getSchemeInfo;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class PostSalesForceInfoTests extends BaseClass {

    private final static Logger logger = Logger.getLogger(PostSalesForceInfoTests.class);

    @Test
    public void postSFInfo_with_sfid() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(COMPANIES_HOUSE);

        SchemeInfo sfIdWithURN = getExpSchemeInfoWithOnlySFIdentifier(COMPANIES_HOUSE);
        String sfids = sfIdWithURN.getAdditionalIdentifiers().get(0).getId();
        String sfid = sfids.split("(?=~)")[0];

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfid", sfid);
        verifyPostSFInfoResponse(schemeInfo, postSchemeRes);
    }

    @Test
    public void postSFInfo_with_sfurn() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(DUN_AND_BRADSTREET);

        SchemeInfo sfIdWithURN = getExpSchemeInfoWithOnlySFIdentifier(DUN_AND_BRADSTREET);
        String sfids = sfIdWithURN.getAdditionalIdentifiers().get(0).getId();
        String sfurn = sfids.split("(?=~)")[1];

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", sfurn);
        verifyPostSFInfoResponse(schemeInfo, postSchemeRes);
    }

    @Test
    public void postSFInfo_with_Invalid_sfurn() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_COH);

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", "10045001qa");
        verifyInvalidIdResponse(postSchemeRes);
    }

    @Test
    public void postSFInfo_with_Duplicate_sfid() {

        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(COMPANIES_HOUSE);
        SchemeInfo expectedRegisteredSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, expectedSchemeInfo.getIdentifier().getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(expectedRegisteredSchemeInfo, postSchemeRes);

        SchemeInfo sfIdWithURN = getExpSchemeInfoWithOnlySFIdentifier(COMPANIES_HOUSE);
        String sfids = sfIdWithURN.getAdditionalIdentifiers().get(0).getId();
        String sfid = sfids.split("(?=~)")[0];

        // Perform Post Operation
        Response postSFSchemeRes = RestRequests.postSFInfo("sfid", sfid);
        verifyResponseCodeForDuplicateResource(postSFSchemeRes);
    }

}

