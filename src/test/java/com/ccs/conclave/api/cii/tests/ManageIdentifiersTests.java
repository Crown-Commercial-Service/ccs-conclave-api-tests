package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.*;
import static com.ccs.conclave.api.cii.data.RequestPayloads.*;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.getSchemeInfo;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class ManageIdentifiersTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(ManageIdentifiersTests.class);

    // NOTE:- the response of manageIdentifiers endpoint is verified using GetSchemeInfo(Get call brings registry info)

    // This test covers the scenario where the additional identifier doesn't know about primary identifier
    @Test
    public void manageIdsGetSchemeInfoForAdditionalIdUnawareOfPrimaryId() throws JSONException {
        // Register Primary Identifier without additional
        SchemeInfo schemeInfo = getInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        SchemeInfo schemeInfoWithoutAddIds = getInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);
        String getSchemeInfoWithoutAddIdsRes = getSchemeInfoWithoutAddIdentifierSection(SCOTLAND_CHARITY_WITH_CHC_COH);
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoWithoutAddIdsRes);
        verifyPostSchemeInfoResponse(schemeInfoWithoutAddIds, postSchemeRes);
        logger.info("Successfully registered organisation...");

        // Search for additional identifier, COH doesn't know about other identifiers
        String additionalIdentifierId = schemeInfo.getAdditionalIdentifiers().get(1).getId();
        Response response = RestRequests.manageIdentifiers(COMPANIES_HOUSE, additionalIdentifierId, getCCSOrgId());
        verifyResponseCodeForSuccess(response);

        // Get call using additional identifier
        Response expectedRes = getSchemeInfo(COMPANIES_HOUSE, additionalIdentifierId);
        verifyResponseCodeForSuccess(expectedRes);
        // Verify search response with response of get call
        verifyManageIdentifiersResponse(expectedRes, response);
    }

    // This test covers the scenario where the additional identifier knows about primary identifier
    // manageIdentifiers get call is expected to bring Primary identifier if it is part of same organisation
    @Test
    public void manageIdsGetSchemeInfoForAdditionalIdKnowsPrimaryId() throws JSONException {
        // Register Primary Identifier without additional
        SchemeInfo schemeInfo = getInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        SchemeInfo schemeInfoWithoutAddIds = getInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);
        String getSchemeInfoWithoutAddIdsRes = getSchemeInfoWithoutAddIdentifierSection(SCOTLAND_CHARITY_WITH_CHC_COH);

        Response getSchemeForCHC = getSchemeInfo(CHARITIES_COMMISSION, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        verifyResponseCodeForSuccess(getSchemeForCHC);

        Response postSchemeInfoRes = RestRequests.postSchemeInfo(getSchemeInfoWithoutAddIdsRes);
        verifyPostSchemeInfoResponse(schemeInfoWithoutAddIds, postSchemeInfoRes);
        logger.info("Successfully registered organisation...");

        // Search for additional identifier, CHC knows about the Primary Id in data SCOTLAND_CHARITY_WITH_CHC_COH
        String additionalIdentifierId = schemeInfo.getAdditionalIdentifiers().get(0).getId();
        Response response = RestRequests.manageIdentifiers(CHARITIES_COMMISSION, additionalIdentifierId, getCCSOrgId());
        verifyResponseCodeForSuccess(response);

        // Verify search response with expected response
        verifyManageIdentifiersResponse(getSchemeForCHC, response);
    }

    @Test
    public void manageIdsGetSchemeInfoWithValidIdentifierNotPartOfOrg() throws JSONException {
        // Register Primary Identifier without additional
        SchemeInfo schemeInfo = getInfo(SCOTLAND_CHARITY_WITH_CHC_COH);

        Response getSchemeRes = getSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        Response postSchemeInfoRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeInfoRes);
        logger.info("Successfully registered organisation...");

        // Search for additional identifier, CHC knows about the Primary Id in data SCOTLAND_CHARITY_WITH_CHC_COH
        String dunsID = getInfo(DUN_AND_BRADSTREET).getIdentifier().getId();
        Response response = RestRequests.manageIdentifiers(DUN_AND_BRADSTREET, dunsID, getCCSOrgId());
        verifyResponseCodeForSuccess(response);

        // Verify search response with expected response
        Response expectedRes = getSchemeInfo(DUN_AND_BRADSTREET, dunsID);
        verifyManageIdentifiersResponse(expectedRes, response);
    }

    // Integration Scenario: This test covers the scenario where the identifier is already registered and admin search the same
    // and expect a response with 200 OK and then try add as part of organisation again, which result in
    // failure with status code 405
    @Test
    public void manageIdsGetSchemeInfoForIdAlreadyRegisteredAndUpdateTheSame() throws JSONException {
        // Register Primary Identifier without additional
        SchemeInfo schemeInfo = getInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        Response getSchemeRes = getSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);
        Response postSchemeInfoRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeInfoRes);
        logger.info("Successfully registered organisation...");

        // Search for additional identifier, CHC knows about the Primary Id in data SCOTLAND_CHARITY_WITH_CHC_COH
        String identifierRegistered = schemeInfo.getAdditionalIdentifiers().get(0).getId();
        Response response = RestRequests.manageIdentifiers(CHARITIES_COMMISSION, identifierRegistered, getCCSOrgId());
        verifyResponseCodeForSuccess(response);

        // Try to update without deleting
        AdditionalSchemeInfo updateSchemeInfo = new AdditionalSchemeInfo();
        updateSchemeInfo.setCcsOrgId(getCCSOrgId());
        Identifier identifier = new Identifier();
        identifier.setId(identifierRegistered);
        identifier.setScheme(getSchemeCode(CHARITIES_COMMISSION));
        updateSchemeInfo.setIdentifier(identifier);
        response = RestRequests.updateScheme(updateSchemeInfo);
        verifyResponseCodeForDuplicateResource(response);

        // Delete Scheme and Update it
        response = RestRequests.deleteScheme(updateSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(identifierRegistered, updateSchemeInfo);
        response = RestRequests.updateScheme(updateSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(identifierRegistered, updateSchemeInfo);
    }

    @Test
    public void getSchemeInfoWithInvalidIdentifierOrScheme() {
        // Register Primary Identifier without additional
        SchemeInfo schemeInfo = getInfo(DUN_AND_BRADSTREET_IRELAND);

        Response getSchemeRes = getSchemeInfo(DUN_AND_BRADSTREET_IRELAND, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        Response postSchemeInfoRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeInfoRes);
        logger.info("Successfully registered organisation...");

        // search using invalid id
        String invalidId = getInfo(INVALID_SCHEME).getIdentifier().getId();
        Response response = RestRequests.manageIdentifiers(DUN_AND_BRADSTREET, invalidId, getCCSOrgId());
        verifyInvalidIdResponse(response);

        // search using invalid Scheme
        String validId = getInfo(COMPANIES_HOUSE).getIdentifier().getId();
        response = RestRequests.manageIdentifiers(INVALID_SCHEME, validId, getCCSOrgId());
        verifyInvalidIdResponse(response);

        // search using invalid ccsOrgId
        response = RestRequests.manageIdentifiers(DUN_AND_BRADSTREET, validId, "0000000000000000000");
        verifyInvalidIdResponse(response);
    }
}
