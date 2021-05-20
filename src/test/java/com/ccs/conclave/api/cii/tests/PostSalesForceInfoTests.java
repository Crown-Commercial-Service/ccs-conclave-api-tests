package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.getExpSchemeInfoWithOnlySFIdentifier;
import static com.ccs.conclave.api.cii.data.OrgDataProvider.getExpectedSchemeInfo;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.COMPANIES_HOUSE;
import static com.ccs.conclave.api.cii.requests.RestRequests.getRegisteredSchemesInfo;
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

        logger.info("Get registered schemes...");
        Response registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifySFRegisteredSchemesAsAdditionalIdentifiers(registeredSchemesRes, schemeInfo, 1);

    }

    @Test
    public void postSFInfo_with_sfurn() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(COMPANIES_HOUSE);

        SchemeInfo sfIdWithURN = getExpSchemeInfoWithOnlySFIdentifier(COMPANIES_HOUSE);
        String sfids = sfIdWithURN.getAdditionalIdentifiers().get(0).getId();
        String sfurn = sfids.split("(?=~)")[1];

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", sfurn);
        verifyPostSFInfoResponse(schemeInfo, postSchemeRes);

        logger.info("Get registered schemes...");
        Response registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifySFRegisteredSchemesAsAdditionalIdentifiers(registeredSchemesRes, schemeInfo, 1);
    }

}


