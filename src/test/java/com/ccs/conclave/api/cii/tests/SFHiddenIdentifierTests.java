package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.*;
import static com.ccs.conclave.api.cii.data.RequestPayloads.getSchemeInfoWithFirstAddIdentifier;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class SFHiddenIdentifierTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(SFHiddenIdentifierTests.class);

    @Test
    public void verifyPostWithSFIdentifierForPrimaryId() {
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(COMPANIES_HOUSE);

        SchemeInfo expectedRegisteredSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, expectedSchemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        logger.info("Verify only Primary Id is registered as active..");
        verifyPostSchemeInfoResponse(expectedRegisteredSchemeInfo, postSchemeRes);

        logger.info("Verify only SF Id is registered as inactive..");
        Response allRegisteredIdsRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        verifyAllRegisteredSchemes(allRegisteredIdsRes, expectedSchemeInfo);
    }

    // The Primary Id has no SF identifier but additional identifier has one
    @Test
    public void verifyPostWithSFIdentifierForAdditionalId() {
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH);

        SchemeInfo expectedRegisteredSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(SCOTLAND_CHARITY_WITH_CHC_COH);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH, expectedSchemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        logger.info("Verify only Primary Id is registered as active..");
        verifyPostSchemeInfoResponse(expectedRegisteredSchemeInfo, postSchemeRes);

        logger.info("Verify only SF Id is registered as inactive..");
        Response allRegisteredIdsRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().get(1).setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().get(2).setHidden("true");
        verifyAllRegisteredSchemes(allRegisteredIdsRes, expectedSchemeInfo);
    }

    // It is expected to save only one SF identifier, here the Test data has SF identifier for both Primary and Additional
    @Test
    public void verifyNoDuplicateSFIdentifierIsSaved() {
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_COH);

        SchemeInfo expectedRegisteredSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(DUN_AND_BRADSTREET_WITH_COH);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, expectedSchemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        logger.info("Verify only Primary Id is registered as active..");
        verifyPostSchemeInfoResponse(expectedRegisteredSchemeInfo, postSchemeRes);

        logger.info("Verify only SF Id is registered as inactive..");
        Response allRegisteredIdsRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().get(1).setHidden("true");
        verifyAllRegisteredSchemes(allRegisteredIdsRes, expectedSchemeInfo);
    }

    // register only identifier which has no SF id, but SF is saved because additional identifier has one
    @Test
    public void updateWithSFIdentifier() {
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH);

        String schemeInfoWithFirstAddId = getSchemeInfoWithFirstAddIdentifier(SCOTLAND_CHARITY_WITH_CHC_COH);
        SchemeInfo expRegSchemeInfoWithFirstAddId = getExpSchemeInfoWithFirstAddIdentifier(SCOTLAND_CHARITY_WITH_CHC_COH);

        Response postSchemeRes = RestRequests.postSchemeInfo(schemeInfoWithFirstAddId);
        logger.info("Verify only Primary Id and first Additional Id is registered as active..");
        verifyPostSchemeInfoResponse(expRegSchemeInfoWithFirstAddId, postSchemeRes);

        // verify only first additional identifier is active
        Response allRegisteredIdsRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().get(1).setHidden("true");
        expectedSchemeInfo.getAdditionalIdentifiers().get(2).setHidden("true");
        verifyAllRegisteredSchemes(allRegisteredIdsRes, expectedSchemeInfo);

        logger.info("Add new Additional identifier..");
        AdditionalSchemeInfo updateScheme = new AdditionalSchemeInfo();
        updateScheme.setCcsOrgId(getCCSOrgId());
        updateScheme.setIdentifier(expectedSchemeInfo.getAdditionalIdentifiers().get(1));
        Response updateRes = updateScheme(updateScheme);
        verifyResponseCodeForSuccess(updateRes);

        logger.info("Verify only SF Id is registered as inactive..");
        allRegisteredIdsRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().get(1).setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().get(2).setHidden("true");
        verifyAllRegisteredSchemes(allRegisteredIdsRes, expectedSchemeInfo);
    }

    // Endpoint won't allow to update SF identifier via PUY endpoint
    @Test
    public void updateWithSFIdentifierButIgnored() {
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(COMPANIES_HOUSE);

        SchemeInfo expectedRegisteredSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, expectedSchemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        logger.info("Verify only Primary Id is registered as active..");
        verifyPostSchemeInfoResponse(expectedRegisteredSchemeInfo, postSchemeRes);

        // Try to update SF identifier
        AdditionalSchemeInfo updateScheme = new AdditionalSchemeInfo();
        updateScheme.setCcsOrgId(getCCSOrgId());
        updateScheme.setIdentifier(expectedSchemeInfo.getAdditionalIdentifiers().get(0));
        Response updateRes = updateScheme(updateScheme);
        verifyInvalidIdResponse(updateRes);
    }


    // This test ensures that the SF id is saved while Add/Update operation; scenario is replicated by additional
    // additoional identifier of another scheme
    @Test
    public void verifySFIdSavedWhileUpdate() {
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(CHARITIES_COMMISSION);

        //   SchemeInfo expectedRegisteredSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(CHARITIES_COMMISSION, expectedSchemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        logger.info("Verify only Primary Id is registered as active..");
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes);

        // Try to update another scheme with SF identifier
        AdditionalSchemeInfo updateScheme = new AdditionalSchemeInfo();
        updateScheme.setCcsOrgId(getCCSOrgId());
        SchemeInfo identifierWithSF = getExpectedSchemeInfo(COMPANIES_HOUSE);
        updateScheme.setIdentifier(identifierWithSF.getIdentifier());
        Response updateRes = updateScheme(updateScheme);
        verifyResponseCodeForSuccess(updateRes);


        logger.info("Verify only SF Id is registered as inactive..");
        Response allRegisteredIdsRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        expectedSchemeInfo.getAdditionalIdentifiers().add(identifierWithSF.getIdentifier());
        expectedSchemeInfo.getAdditionalIdentifiers().add(identifierWithSF.getAdditionalIdentifiers().get(0));
        verifyAllRegisteredSchemes(allRegisteredIdsRes, expectedSchemeInfo);
    }
}
