package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.getInfo;
import static com.ccs.conclave.api.cii.data.RequestPayloads.getSchemeInfoWithEmptyAddIdentifiers;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.*;
import static com.ccs.conclave.api.cii.data.RequestPayloads.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

// Tests in this class covers real user scenarios
public class IntegrationTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(IntegrationTests.class);

    // when user Register Primary Identifier and SELECTED AddIdentifiers(eg:only first one), and expected to see only registered
    // identifiers in the manage organisation profile
    @Test
    public void registerOrgAndVerifyIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY_WITH_CHC_COH);

        // Perform Post Operation without Additional Identifiers
        String getSchemeInfoRes = getSchemeInfoWithFirstAddIdentifier(SCOTLAND_CHARITY_WITH_CHC_COH);
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes);
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation with one additional identifier...");


        // Todo: Get registered schemes
    }

    // Duplicate check in Get call is verified here after successful Post operation.
    // Verified both registered primary and additional identifiers get call duplicate check
    @Test
    public void userSearchUsingAlreadyRegisteredIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(NORTHERN_CHARITY_WITH_COH);
        Response getSchemeInfo = getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation...");

        // Search already registered Primary Identifier
        Response getSchemeRes = getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Search already registered Additional Identifier
        getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    // Duplicate check in Get call is verified here after successful Post operation without additional identifiers.
    // When user Register Primary Identifier without AddIdentifiers, another user is not able to use those
    // additional identifiers as cii stores them as hidden identifiers
    @Test
    public void userSearchUsingAddIdentifierOfAlreadyRegisteredIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);

        // Perform Post Operation without Additional Identifiers
        String getSchemeInfo = getSchemeInfoWithEmptyAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo);
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation without additional identifiers...");

        // user Search Using Already Registered AddIdentifier (COH in DUNS) and see duplicate entry error code
        Response getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // user Search Using Already Registered AddIdentifier (CHC in DUNS) and see duplicate entry error code
        getSchemeRes = getSchemeInfo(CHARITIES_COMMISSION, schemeInfo.getAdditionalIdentifiers().get(1).getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void userUpdateAddIdentifierViaAddRegistry() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);
        // Perform Post Operation without Additional Identifiers
        String getSchemeInfoRes = getSchemeInfoWithFirstAddIdentifier(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes);
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation with one additional identifier...");

        // Search for additional identifier
        String identifierNotRegistered = schemeInfo.getAdditionalIdentifiers().get(1).getId();
        Response response = RestRequests.manageIdentifiers(COMPANIES_HOUSE, identifierNotRegistered, getCCSOrgId());
        verifyResponseCodeForSuccess(response);

        // Try to update additional identifier
        AdditionalSchemeInfo updateSchemeInfo = new AdditionalSchemeInfo();
        updateSchemeInfo.setCcsOrgId(getCCSOrgId());
        Identifier identifier = new Identifier();
        identifier.setId(identifierNotRegistered);
        identifier.setScheme(getSchemeCode(COMPANIES_HOUSE));
        updateSchemeInfo.setIdentifier(identifier);
        response = RestRequests.updateScheme(updateSchemeInfo);
        // Outstanding fix CON-717
        verifyResponseCodeForSuccess(response);

        // Todo: get registered schemes
        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    // Integration Scenario: the identifier is already registered and admin search the same
    // and expect a response with 200 OK and then try to add as part of organisation again, which result in
    // failure with status code 405
    @Test
    public void userSearchOrgIdentifierViaAddRegistryAndUpdateTheSameAfterDelete() throws JSONException {
        // Register Primary Identifier with additional identifiers
        SchemeInfo schemeInfo = getInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        Response getSchemeRes = getSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH, schemeInfo.getIdentifier().getId());
        Response getSchemeResForCHC = getSchemeInfo(CHARITIES_COMMISSION, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        verifyResponseCodeForSuccess(getSchemeRes);
        Response postSchemeInfoRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeInfoRes);
        logger.info("Successfully registered organisation...");

        // Search for additional identifier, CHC knows about the Primary Id in data SCOTLAND_CHARITY_WITH_CHC_COH
        String identifierRegistered = schemeInfo.getAdditionalIdentifiers().get(0).getId();
        Response response = RestRequests.manageIdentifiers(CHARITIES_COMMISSION, identifierRegistered, getCCSOrgId());
        verifyResponseCodeForSuccess(response);

        // Try to update(PUT call) without deleting, which result in duplicate resource status code
        AdditionalSchemeInfo updateSchemeInfo = new AdditionalSchemeInfo();
        updateSchemeInfo.setCcsOrgId(getCCSOrgId());
        Identifier identifier = new Identifier();
        identifier.setId(identifierRegistered);
        identifier.setScheme(getSchemeCode(CHARITIES_COMMISSION));
        updateSchemeInfo.setIdentifier(identifier);
        response = RestRequests.updateScheme(updateSchemeInfo);
        verifyResponseCodeForDuplicateResource(response);

        // Delete Scheme and get scheme again and successfully Update it
        response = RestRequests.deleteScheme(updateSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(identifierRegistered, updateSchemeInfo);

        // Todo: get registered schemes

        response = RestRequests.manageIdentifiers(CHARITIES_COMMISSION, identifierRegistered, getCCSOrgId());
        verifyResponseCodeForSuccess(response);
        verifyManageIdentifiersResponse(getSchemeResForCHC, response);

        response = RestRequests.updateScheme(updateSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(identifierRegistered, updateSchemeInfo);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    // Verify admin users can delete hidden additional identifiers
    // NOTE: atm. Endpoint doesn't check admin or normal user
    @Test
    public void adminDeleteIdentifierHiddenAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);

        // Perform Get call to form the request payload for POST call
        String getSchemeInfo = getSchemeInfoWithEmptyAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);

        // Perform Post Operation without Additional Identifiers
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo);
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // verify duplicate check for additional identifier (COH in DUNS) even if not registered
        Response getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // verify duplicate check for additional identifier (CHC in DUNS) even if not registered
        getSchemeRes = getSchemeInfo(CHARITIES_COMMISSION, schemeInfo.getAdditionalIdentifiers().get(1).getId());
        verifyResponseCodeForDuplicateResource(getSchemeRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    // When admin receives a claim form about conflict, admin delete organisation registration so that right user
    // can register again
    // todo @Test
    public void adminDeleteOrganisationAndUserRegAgain() {

    }
}
