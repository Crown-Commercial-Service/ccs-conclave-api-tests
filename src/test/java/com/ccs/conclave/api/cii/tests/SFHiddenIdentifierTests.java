package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.*;
import static com.ccs.conclave.api.cii.data.RequestPayloads.getSchemeInfoWithFirstAddIdentifier;
import static com.ccs.conclave.api.cii.data.RequestPayloads.getSchemeInfoWithoutAddIdentifierSection;
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

    @Test(description = "The Primary Id has no SF identifier but additional identifier has one")
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


    @Test(description = "It is expected to save unique SF identifier, here the Test data has SF identifier for both Primary and Additional")
    public void verifyUniqueSFIdentifierIsSaved() {
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

    // CON-980: Org with different Salesforce identities should Both be added into CII - POST
    @Test(description = "If Primary and Additional identifiers have different SF id, it is expected to save both SF id")
    public void verifyMultipleSFIdentifierAreSavedViaPost() {
        // Todo: following steps can be uncommented when mock data [DUN_AND_BRADSTREET_WITH_COH_WITH_DIFF_SF_ID]is ready

        if(isMockTestEnabled()) {
//        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_COH_WITH_DIFF_SF_ID);
//
//        SchemeInfo expectedRegisteredSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(DUN_AND_BRADSTREET_WITH_COH_WITH_DIFF_SF_ID);
//
//        // Perform Get call to form the request payload for POST call
//        Response getSchemeRes = getSchemeInfoWithoutAddIdentifierSection(DUN_AND_BRADSTREET_WITH_COH_WITH_DIFF_SF_ID);
//        verifyResponseCodeForSuccess(getSchemeRes);
//
//        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
//        logger.info("Verify only Primary Id is registered as active.");
//        verifyPostSchemeInfoResponse(expectedRegisteredSchemeInfo, postSchemeRes);
//
//        logger.info("Verify additional identifiers - two SF Ids are registered as inactive..");
//        Response allRegisteredIdsRes = getAllRegisteredSchemesInfo(getCCSOrgId());
//        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
//        expectedSchemeInfo.getAdditionalIdentifiers().get(1).setHidden("true");
//        expectedSchemeInfo.getAdditionalIdentifiers().get(2).setHidden("true");
//        verifyAllRegisteredSchemes(allRegisteredIdsRes, expectedSchemeInfo);
        } else {
            logger.info("Skipping this test as there is no actual data for this scenario, this test runs only " +
                    "on Mock service.");
            throw new SkipException("");
        }
    }

    @Test(description = "register only identifier which has no SF id, but SF is saved because additional identifier has one." +
            "Update the additional identifier with SF and ensure SF id is still hidden")
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
        expectedSchemeInfo.getAdditionalIdentifiers().get(2).setHidden("true"); // SF Identifier is hidden
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
        expectedSchemeInfo.getAdditionalIdentifiers().get(2).setHidden("true"); // SF Identifier is hidden
        verifyAllRegisteredSchemes(allRegisteredIdsRes, expectedSchemeInfo);
    }

    // CON-981: Org with different Salesforce identities should Both be added into CII - PUT
    @Test(description = "If Primary and Additional identifiers have different SF, it is expected to save both SF via PUT operation as well")
    public void verifyMultipleSFIdentifierAreSavedViaPUT() {
        // Todo: uncomment below steps when CON-981 is ready for QA
//        SchemeInfo expSchemeInfo1 = getExpectedSchemeInfo(COMPANIES_HOUSE);
//        SchemeInfo expSchemeInfoWithNoAddIds = getExpSchemeInfoWithoutAddIdentifiers(COMPANIES_HOUSE);
//        Response getSchemeInfo = getSchemeInfo(COMPANIES_HOUSE, expSchemeInfo1.getIdentifier().getId());
//
//        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo.asString());
//        logger.info("Verify only Primary Id is registered as active.");
//        verifyPostSchemeInfoResponse(expSchemeInfoWithNoAddIds, postSchemeRes);
//
//        logger.info("Verify additional identifiers - SF Ids is registered as inactive..");
//        Response allRegisteredIdsRes = getAllRegisteredSchemesInfo(getCCSOrgId());
//        expSchemeInfo1.getAdditionalIdentifiers().get(0).setHidden("true");
//        verifyAllRegisteredSchemes(allRegisteredIdsRes, expSchemeInfo1);
//
//        // Adding new identifier which has another SF id associated with it
//        SchemeInfo expSchemeInfo2 = getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_COH);
//        AdditionalSchemeInfo updateScheme = new AdditionalSchemeInfo();
//        updateScheme.setCcsOrgId(getCCSOrgId());
//        updateScheme.setIdentifier(expSchemeInfo2.getIdentifier());
//        Response updateRes = updateScheme(updateScheme);
//        verifyResponseCodeForSuccess(updateRes);
//
//        // create expectedSchemeInfo with COH and DUNS and both SF ids
//        SchemeInfo expectedSchemeInfo = new SchemeInfo();
//        expectedSchemeInfo.setIdentifier(expSchemeInfo1.getIdentifier()); // COH Id as Primary
//        expectedSchemeInfo.getIdentifier().setHidden("false");
//        expSchemeInfo1.getAdditionalIdentifiers().get(0).setHidden("true"); // SF Id1 as additional id
//        expectedSchemeInfo.getAdditionalIdentifiers().add(expSchemeInfo1.getAdditionalIdentifiers().get(0))
//        expSchemeInfo2.getIdentifier().setHidden("false");
//        expectedSchemeInfo.getAdditionalIdentifiers().add(expSchemeInfo2.getIdentifier()); // DUNS Id as Additional Id
//        expSchemeInfo2.getAdditionalIdentifiers().get(1).setHidden("true");
//        expectedSchemeInfo.getAdditionalIdentifiers().add(expSchemeInfo2.getIdentifier()); // DUN'S SF Id as Additional Id
//
//        logger.info("Verify one more SF Id is added and is inactive.");
//        allRegisteredIdsRes = getAllRegisteredSchemesInfo(getCCSOrgId());
//        verifyAllRegisteredSchemes(allRegisteredIdsRes, expectedSchemeInfo);
    }

    @Test(description = "CII PUT endpoint won't allow to update SF identifier via PUT endpoint")
    public void addOrUpdateSFIdentifierIsNotAllowed() {
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

    @Test(description = "This test ensures that the SF id is saved while Add/Update operation; scenario is replicated by additional" +
            "additional identifier of another scheme" )
    public void verifySFIdSavedWhileUpdate() {
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(CHARITIES_COMMISSION);

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
        identifierWithSF.getIdentifier().setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().add(identifierWithSF.getIdentifier());
        // SF id is hidden:true it is set in tht test data
        expectedSchemeInfo.getAdditionalIdentifiers().add(identifierWithSF.getAdditionalIdentifiers().get(0));
        verifyAllRegisteredSchemes(allRegisteredIdsRes, expectedSchemeInfo);
    }
}
