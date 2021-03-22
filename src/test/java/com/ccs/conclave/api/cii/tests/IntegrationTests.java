package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.getExpSchemeInfoWithoutSFIdentifier;
import static com.ccs.conclave.api.cii.data.OrgDataProvider.getExpectedSchemeInfo;
import static com.ccs.conclave.api.cii.data.RequestPayloads.getSchemeInfoWithoutAddIdentifiers;
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
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        SchemeInfo schemeInfoWithOneAddId = OrgDataProvider.getExpSchemeInfoWithFirstAddIdentifier(SCOTLAND_CHARITY_WITH_CHC_COH);

        // Perform Post Operation without Additional Identifiers
        String getSchemeInfoRes = getSchemeInfoWithFirstAddIdentifier(SCOTLAND_CHARITY_WITH_CHC_COH);
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes);
        verifyPostSchemeInfoResponse(schemeInfoWithOneAddId, postSchemeRes);
        logger.info("Successfully registered organisation with one additional identifier...");

        logger.info("Get registered schemes after registration to verify only Primary id and" +
                " selected Additional identifier is registered...");
        Response registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 1);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    // Duplicate check in Get call is verified here after successful Post operation.
    // Verified both registered primary and additional identifiers get call duplicate check
    @Test
    public void userSearchUsingAlreadyRegisteredIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(NORTHERN_CHARITY_WITH_COH);
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
    public void userSearchUsingAddIdentifierOfAlreadyRegisteredPrimaryIdentifier() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);
        SchemeInfo schemeInfoWithoutAddIds = OrgDataProvider.getExpSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);
        // Perform Post Operation without Additional Identifiers
        String getSchemeInfo = getSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo);
        verifyPostSchemeInfoResponse(schemeInfoWithoutAddIds, postSchemeRes);
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
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);
        SchemeInfo schemeInfoWithOneAddId = OrgDataProvider.getExpSchemeInfoWithFirstAddIdentifier(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);
        // Perform Post Operation without Additional Identifiers
        String getSchemeInfoRes = getSchemeInfoWithFirstAddIdentifier(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes);
        verifyPostSchemeInfoResponse(schemeInfoWithOneAddId, postSchemeRes);
        logger.info("Successfully registered organisation with one additional identifier...");

        // org. admin search for additional identifier via manage org profile
        Identifier identifierNotRegistered = schemeInfo.getAdditionalIdentifiers().get(1);
        Response response = RestRequests.manageIdentifiers(COMPANIES_HOUSE, identifierNotRegistered.getId(), getCCSOrgId());
        verifyResponseCodeForSuccess(response);

        // Try to update additional identifier
        AdditionalSchemeInfo updateSchemeInfo = new AdditionalSchemeInfo();
        updateSchemeInfo.setCcsOrgId(getCCSOrgId());
        updateSchemeInfo.setIdentifier(identifierNotRegistered);
        response = RestRequests.updateScheme(updateSchemeInfo);
        // fixed CON-717
        verifyResponseCodeForSuccess(response);

        logger.info("Get registered schemes after updating...");
        Response registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 2);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    // Integration Scenario: user can update registered scheme in cii after updating source
    @Test
    public void userUpdatesAdditionalIdentifierAfterModifiedTheSource() {
        // Register Primary Identifier with additional identifiers
        SchemeInfo schemeInfo = getExpectedSchemeInfo(CHARITIES_COMMISSION_WITH_SC);
        Response getSchemeRes = getSchemeInfo(CHARITIES_COMMISSION_WITH_SC, schemeInfo.getIdentifier().getId());

        Response getSchemeResAddId = getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        Response postSchemeInfoRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeInfoRes);
        logger.info("Successfully registered organisation...");

        // Search for additional identifier
        Identifier identifierRegistered = schemeInfo.getAdditionalIdentifiers().get(0);
        Response response = RestRequests.manageIdentifiers(SCOTLAND_CHARITY, identifierRegistered.getId(), getCCSOrgId());
        verifyResponseCodeForSuccess(response);

        // Try to update(PUT call) without deleting, user can update
        AdditionalSchemeInfo updateSchemeInfo = new AdditionalSchemeInfo();
        updateSchemeInfo.setCcsOrgId(getCCSOrgId());
        updateSchemeInfo.setIdentifier(identifierRegistered);
        response = RestRequests.updateScheme(updateSchemeInfo);
        verifyResponseCodeForSuccess(response);

        // Delete Scheme and get scheme again and successfully Update it
        response = RestRequests.deleteScheme(updateSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), updateSchemeInfo);

        logger.info("Get registered schemes...");
        Response registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 0);

        // Search for additional identifier and add again
        response = RestRequests.manageIdentifiers(SCOTLAND_CHARITY, identifierRegistered.getId(), getCCSOrgId());
        verifyResponseCodeForSuccess(response);
        verifyManageIdentifiersResponse(getSchemeResAddId, response);

        response = RestRequests.updateScheme(updateSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyUpdatedScheme(getCCSOrgId(), updateSchemeInfo);

        logger.info("Get registered schemes...");
        registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 1);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    // Verify admin users can delete hidden additional identifiers
    // NOTE: atm. Endpoint doesn't check admin or normal user
    @Test
    public void adminDeleteIdentifierHiddenAddIdentifiers() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_COH);
        SchemeInfo schemeInfoWithoutAddIds = OrgDataProvider.getExpSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);
        // Perform Get call to form the request payload for POST call
        String getSchemeInfo = getSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);

        // Perform Post Operation without Additional Identifiers
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo);
        verifyPostSchemeInfoResponse(schemeInfoWithoutAddIds, postSchemeRes);

        logger.info("Get all registered schemes ensure the additional identifier is in cii database...");
        Response registeredAllSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        schemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        verifyAllRegisteredSchemes(registeredAllSchemesRes, schemeInfo);

        AdditionalSchemeInfo deleteSchemeInfo = new AdditionalSchemeInfo();
        deleteSchemeInfo.setCcsOrgId(getCCSOrgId());
        deleteSchemeInfo.setIdentifier(schemeInfo.getAdditionalIdentifiers().get(0));

        // Delete Scheme hidden identifier
        Response response = RestRequests.deleteScheme(deleteSchemeInfo);
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), deleteSchemeInfo);

        logger.info("Get all registered schemes ensure the additional identifier is deleted from cii database...");
        registeredAllSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        verifyAllRegisteredSchemes(registeredAllSchemesRes, schemeInfoWithoutAddIds);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    // When admin receives a claim form about conflict, admin delete organisation registration so that right user
    // can register again
    @Test
    public void adminDeleteOrganisationAndUserRegisterAgain() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(COMPANIES_HOUSE);
        Response getSchemeInfo = getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());

        // Perform Post Operation without Additional Identifiers
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);

        // delete organisation and register again
        deleteOrganisation(getCCSOrgId());
        postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);
    }

    // Defect CON-764:FIXED
     @Test
    public void userUpdatesPrimaryIdentifierAfterModifiedTheSource() {
        // Register Primary Identifier with additional identifiers
        SchemeInfo schemeInfo = getExpectedSchemeInfo(CHARITIES_COMMISSION_WITH_SC);
        Response getSchemeRes = getSchemeInfo(CHARITIES_COMMISSION_WITH_SC, schemeInfo.getIdentifier().getId());

        Response postSchemeInfoRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeInfoRes);
        logger.info("Successfully registered organisation...");

        // Search for Primary identifier
        Identifier identifierRegistered = schemeInfo.getIdentifier();
        Response response = RestRequests.manageIdentifiers(CHARITIES_COMMISSION_WITH_SC, identifierRegistered.getId(), getCCSOrgId());
        verifyResponseCodeForSuccess(response);

        // Try to update(PUT call) without deleting, user can update
        AdditionalSchemeInfo updateSchemeInfo = new AdditionalSchemeInfo();
        updateSchemeInfo.setCcsOrgId(getCCSOrgId());
        updateSchemeInfo.setIdentifier(identifierRegistered);
        response = RestRequests.updateScheme(updateSchemeInfo);
        verifyResponseCodeForSuccess(response);

        // verify Primary Identifier after update
        response = RestRequests.getRegisteredSchemesInfo(getCCSOrgId());
        verifyResponseCodeForSuccess(response);
        verifyRegisteredSchemes(response, schemeInfo, 1);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void ccsAdminDeleteSalesForceIdentifierOfOrg() {
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(COMPANIES_HOUSE);
        // As SF is there as part of Test data need to ensure SF Identifier is not registered as hidden:false
        SchemeInfo expectedRegisteredSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, expectedSchemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(expectedRegisteredSchemeInfo, postSchemeRes);

        // verify All registered identifiers including hidden once
        Response allRegSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        expectedSchemeInfo.getIdentifier().setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        verifyAllRegisteredSchemes(allRegSchemesRes, expectedSchemeInfo);

        logger.info("admin deleted salesforce id....");
        AdditionalSchemeInfo deleteSchemeInfo = new AdditionalSchemeInfo();
        deleteSchemeInfo.setIdentifier(expectedSchemeInfo.getAdditionalIdentifiers().get(0));
        deleteSchemeInfo.setCcsOrgId(getCCSOrgId());
        Response deleteRes = deleteScheme(deleteSchemeInfo);
        verifyDeletedScheme(getCCSOrgId(), deleteSchemeInfo);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void orgAdminUpdatesAddIdentifierAndSalesForceIdSaved() {
        SchemeInfo expectedSchemeInfo = getExpectedSchemeInfo(COMPANIES_HOUSE);
        // As SF is there as part of Test data need to ensure SF Identifier is not registered as hidden:false
        SchemeInfo expectedRegisteredSchemeInfo = getExpSchemeInfoWithoutSFIdentifier(COMPANIES_HOUSE);

        // Perform Get call to form the request payload for POST call
        Response getSchemeRes = getSchemeInfo(COMPANIES_HOUSE, expectedSchemeInfo.getIdentifier().getId());
        verifyResponseCodeForSuccess(getSchemeRes);

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeRes.asString());
        verifyPostSchemeInfoResponse(expectedRegisteredSchemeInfo, postSchemeRes);

        // verify All registered identifiers including hidden once
        Response allRegSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        expectedSchemeInfo.getIdentifier().setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        verifyAllRegisteredSchemes(allRegSchemesRes, expectedSchemeInfo);

        logger.info("admin deleted salesforce id....");
        AdditionalSchemeInfo deleteSchemeInfo = new AdditionalSchemeInfo();
        deleteSchemeInfo.setIdentifier(expectedSchemeInfo.getAdditionalIdentifiers().get(0));
        deleteSchemeInfo.setCcsOrgId(getCCSOrgId());
        Response deleteRes = deleteScheme(deleteSchemeInfo);
        verifyDeletedScheme(getCCSOrgId(), deleteSchemeInfo);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }


}
