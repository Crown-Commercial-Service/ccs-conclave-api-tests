package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.*;
import static com.ccs.conclave.api.cii.data.RequestPayloads.getSchemeInfoWithEmptyAddIdentifiers;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.deleteOrganisation;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class DeleteSchemeTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(DeleteSchemeTests.class);

    @Test
    public void deleteScheme_COH_from_DUN() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_COH);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);
        Assert.assertEquals(additionalSchemesInfo.size() , 1, "Only one additional identifier is expected, please check the test data!");

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Deleting additional identifier to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo = additionalSchemesInfo.get(0);
        additionalSchemeInfo.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), additionalSchemeInfo);

        logger.info("Deleting identifier deleted already");
        response = RestRequests.deleteScheme(additionalSchemeInfo);
        verifyInvalidIdResponse(response);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void deleteScheme_COH_and_CHC_from_SC() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);
        Assert.assertEquals(additionalSchemesInfo.size(), 2, "Two additional identifiers are expected, please check the test data!");

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Deleting additional identifier1 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), additionalSchemeInfo1);

        logger.info("Deleting additional identifier2 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        additionalSchemeInfo2.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), additionalSchemeInfo2);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    // User can delete an additional identifier and update again
    @Test
    public void deleteScheme_and_updateScheme() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(CHARITIES_COMMISSION_WITH_COH_AND_SC);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiers(CHARITIES_COMMISSION_WITH_COH_AND_SC);
        Assert.assertEquals(additionalSchemesInfo.size(), 2, "Two additional identifiers are expected, please check the test data!");

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_COH_AND_SC, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Deleting additional identifier1 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), additionalSchemeInfo1);

        logger.info("Updating additional identifier1 to the existing organisation...");
        response = RestRequests.updateScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(getCCSOrgId(), additionalSchemeInfo1);

        logger.info("Deleting additional identifier1 to the existing organisation...");
        response = RestRequests.deleteScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), additionalSchemeInfo1);

        logger.info("Deleting additional identifier2 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        additionalSchemeInfo2.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), additionalSchemeInfo2);

        logger.info("Updating additional identifier2 to the existing organisation...");
        response = RestRequests.updateScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(getCCSOrgId(), additionalSchemeInfo2);

        logger.info("Deleting additional identifier2 to the existing organisation...");
        response = RestRequests.deleteScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), additionalSchemeInfo2);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void deleteSchemeInvalidOrgIdOrIdentifierOrScheme() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_COH);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);
        Assert.assertEquals(additionalSchemesInfo.size(), 1, "Only one additional identifier is expected, please check the test data!");

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Deleting additional identifier of the existing organisation with invalid orgId...");
        AdditionalSchemeInfo additionalSchemeInfo = additionalSchemesInfo.get(0);
        additionalSchemeInfo.setCcsOrgId("12345643435");
        response = RestRequests.deleteScheme(additionalSchemeInfo);
        verifyInvalidIdResponse(response);

        logger.info("Deleting additional identifier of the existing organisation with invalid Id...");
        additionalSchemeInfo.setCcsOrgId(getCCSOrgId());
        additionalSchemeInfo.getIdentifier().setId("RC000000");
        response = RestRequests.deleteScheme(additionalSchemeInfo);
        verifyInvalidIdResponse(response);

        logger.info("Deleting additional identifier of the existing organisation with invalid scheme...");
         additionalSchemeInfo.getIdentifier().setScheme(getSchemeCode(INVALID_SCHEME));
         response = RestRequests.deleteScheme(additionalSchemeInfo);
         verifyInvalidIdResponse(response);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void deleteScheme_PrimaryIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH);

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Deleting Primary identifier of the existing organisation...");
        AdditionalSchemeInfo deleteSchemeInfo = new AdditionalSchemeInfo();
        deleteSchemeInfo.setCcsOrgId(getCCSOrgId());
        deleteSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        response = RestRequests.deleteScheme(deleteSchemeInfo);
        verifyBadRequestResponse(response);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void deleteScheme_addIdentifier_Of_Another_scheme() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(NORTHERN_CHARITY_WITH_COH);
        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Deleting valid additional identifier of another scheme");
        // get only AdditionalIdentifiers from another Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiers(CHARITIES_COMMISSION_WITH_SC);
        AdditionalSchemeInfo additionalSchemeInfo = additionalSchemesInfo.get(0);
        additionalSchemeInfo.setCcsOrgId(getCCSOrgId());
        // Perform deletion of valid additional Identifier of another scheme with valid OrgID
        response = RestRequests.deleteScheme(additionalSchemeInfo);
        verifyInvalidIdResponse(response);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    // Integration Scenario:- Verify admin users can delete hidden additional identifiers
    @Test
    public void deleteSchemeInfoForHiddenAddIdentifiers() {
        SchemeInfo schemeInfoWithoutAddIds = OrgDataProvider.getExpSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);

        // Perform Get call to form the request payload for POST call
        String getSchemeInfo = getSchemeInfoWithEmptyAddIdentifiers(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);
        // get only AdditionalIdentifiers from another Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiers(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);

        // Perform Post Operation without Additional Identifiers
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo);
        verifyPostSchemeInfoResponse(schemeInfoWithoutAddIds, postSchemeRes);

        logger.info("Deleting hidden additional identifiers");
        AdditionalSchemeInfo deleteSchemeInfo = additionalSchemesInfo.get(0);
        deleteSchemeInfo.setCcsOrgId(getCCSOrgId());
        deleteSchemeInfo.setIdentifier(additionalSchemesInfo.get(0).getIdentifier());
        Response response = RestRequests.deleteScheme(deleteSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), deleteSchemeInfo);

        deleteSchemeInfo.setIdentifier(additionalSchemesInfo.get(1).getIdentifier());
        response = RestRequests.deleteScheme(deleteSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), deleteSchemeInfo);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }
}
