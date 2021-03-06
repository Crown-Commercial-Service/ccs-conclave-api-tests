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
import static com.ccs.conclave.api.cii.data.RequestPayloads.*;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.deleteOrganisation;
import static com.ccs.conclave.api.cii.requests.RestRequests.getSchemeInfo;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.getCCSOrgId;

public class UpdateSchemeTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(UpdateSchemeTests.class);

    @Test
    public void updateScheme_COH_into_DUNS() {
        // GetScheme response without additional identifiers
        String responseStr = getSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);
        // Get expected SchemeInfo without additional identifiers
        SchemeInfo expectedSchemeInfo = getExpSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiersExceptSF(DUN_AND_BRADSTREET_WITH_COH);
        Assert.assertEquals(additionalSchemesInfo.size(), 1, "Only one additional identifier is expected, please check the test data!");

        logger.info("Performing Post Operation/register organisation with only Primary Identifier");
        Response response = RestRequests.postSchemeInfo(responseStr);

        // verify the post response and ensure only Primary identifier is used for organisation registration
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Adding additional identifier to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo = additionalSchemesInfo.get(0);
        additionalSchemeInfo.setCcsOrgId(getCCSOrgId());
        response = RestRequests.updateScheme(additionalSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(getCCSOrgId(), additionalSchemeInfo);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void updateScheme_COH_and_CHC_into_SC() {
        // Get expected SchemeInfo without additional identifiers
        SchemeInfo expectedSchemeInfo = getExpSchemeInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);

        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiersExceptSF(SCOTLAND_CHARITY_WITH_CHC_COH);
        Assert.assertEquals(additionalSchemesInfo.size(), 2, "Two additional identifier are expected, please check the test data!");

        // GetScheme response without additional identifiers
        String responseStr = getSchemeInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);
        // Perform Post Operation/ register organisation with only Primary Identifier
        Response response = RestRequests.postSchemeInfo(responseStr);

        // verify the post response and ensure only Primary identifier is used for organisation registration
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);

        logger.info("Adding additional identifier1 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        response = RestRequests.updateScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(getCCSOrgId(), additionalSchemeInfo1);

        logger.info("Adding additional identifier2 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        additionalSchemeInfo2.setCcsOrgId(getCCSOrgId());
        response = RestRequests.updateScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(getCCSOrgId(), additionalSchemeInfo2);

        logger.info("Try Update again without deleting...");
        response = RestRequests.updateScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(getCCSOrgId(), additionalSchemeInfo2);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void updateSchemeInvalidSchemeOrID() {
        // GetScheme response without additional identifiers
        String responseStr = getSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);
        // Get expected SchemeInfo without additional identifiers
        SchemeInfo expectedSchemeInfo = getExpSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiersExceptSF(INVALID_SCHEME);
        Assert.assertEquals(additionalSchemesInfo.size(), 1, "Only one additional identifier is expected, please check the test data!");

        logger.info("Performing Post Operation/register organisation with only Primary Identifier");
        Response response = RestRequests.postSchemeInfo(responseStr);

        // verify the post response and ensure only Primary identifier is used for organisation registration
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Adding additional identifier with invalid scheme to the existing organisation...");
        AdditionalSchemeInfo invalidSchemeInfo = additionalSchemesInfo.get(0);
        invalidSchemeInfo.setCcsOrgId(getCCSOrgId());
        response = RestRequests.updateScheme(invalidSchemeInfo);
        verifyInvalidIdResponse(response);

        logger.info("Adding valid additional identifier to the invalid organisation...");
        AdditionalSchemeInfo additionalSchemeInfo = additionalSchemesInfo.get(0);
        additionalSchemeInfo.setCcsOrgId("0101001010100");
        response = RestRequests.updateScheme(additionalSchemeInfo);
        verifyInvalidIdResponse(response);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test(description = "Org admin can add any additional identifier as part of his organisation with his own risk." +
            "CII is not checking whether the additional identifier admin choose is as part of his organisation or not." +
            "So this test expects a success response.")
    public void updateScheme_validIdentifierOfAnotherScheme() {
        // GetScheme response without additional identifiers
        String responseStr = getSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);
        // Get expected SchemeInfo without additional identifiers
        SchemeInfo expectedSchemeInfo = getExpSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);

        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiersExceptSF(CHARITIES_COMMISSION_WITH_COH);
        Assert.assertEquals(additionalSchemesInfo.size(), 1, "Two additional identifier are expected, please check the test data!");

        // Perform Post Operation/ register organisation with only Primary Identifier
        Response response = RestRequests.postSchemeInfo(responseStr);

        // verify the post response and ensure only Primary identifier is used for organisation registration
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);

        logger.info("Adding additional identifier1 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        response = RestRequests.updateScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(getCCSOrgId(), additionalSchemeInfo1);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }


    // Defect: CON-764:FIXED
    @Test
    public void updatePrimaryIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(COMPANIES_HOUSE);
        SchemeInfo schemeInfoWithoutSF = OrgDataProvider.getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE);
        Response schemeInfoRes = getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfoWithoutSF, schemeInfoRes);

        Response response = RestRequests.postSchemeInfo(schemeInfoRes.asString());
        verifyPostSchemeInfoResponse(schemeInfoWithoutSF, response);

        logger.info("Update Primary scheme successfully...");
        AdditionalSchemeInfo updateSchemeInfo = new AdditionalSchemeInfo();
        updateSchemeInfo.setCcsOrgId(getCCSOrgId());
        updateSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        response = RestRequests.updateScheme(updateSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(getCCSOrgId(), updateSchemeInfo);
    }

    @Test(description = "Try to update using existing identifier as part of another organisation and expect duplicate error code")
    public void updateAlreadyExistingIdentifier() {
        SchemeInfo schemeInfo1 = getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_COH);
        SchemeInfo schemeInfoWithoutSF = OrgDataProvider.getExpSchemeInfoWithoutSFIdentifier(DUN_AND_BRADSTREET_WITH_COH);
        Response schemeInfoRes = getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, schemeInfo1.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfoWithoutSF, schemeInfoRes);
        Response response1 = RestRequests.postSchemeInfo(schemeInfoRes.asString());
        verifyPostSchemeInfoResponse(schemeInfoWithoutSF, response1);

        // Register another identifier with only Primary Identifier
        SchemeInfo schemeInfo2 = getExpectedSchemeInfo(CHARITIES_COMMISSION);
        Response schemeInfoRes2 = getSchemeInfo(CHARITIES_COMMISSION, schemeInfo2.getIdentifier().getId());
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(CHARITIES_COMMISSION);
        Response response2 = RestRequests.postSchemeInfo(schemeInfoRes2.asString());
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response2);

        logger.info("Update identifier already existing...");
        AdditionalSchemeInfo updateSchemeInfo1 = new AdditionalSchemeInfo();
        updateSchemeInfo1.setCcsOrgId(getCCSOrgId());
        updateSchemeInfo1.setIdentifier(schemeInfo1.getIdentifier());
        Response response3 = RestRequests.updateScheme(updateSchemeInfo1);
        verifyResponseCodeForDuplicateResource(response3);

        AdditionalSchemeInfo updateSchemeInfo2 = new AdditionalSchemeInfo();
        updateSchemeInfo2.setCcsOrgId(getCCSOrgId());
        updateSchemeInfo2.setIdentifier(schemeInfo1.getAdditionalIdentifiers().get(0));
        Response response4 = RestRequests.updateScheme(updateSchemeInfo2);
        verifyResponseCodeForDuplicateResource(response4);
    }
}
