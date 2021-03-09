package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.RegisteredSchemeInfo;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.getAdditionalIdentifierInfo;
import static com.ccs.conclave.api.cii.data.RequestPayloads.getSchemeInfoWithEmptyAddIdentifiers;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.deleteOrganisation;
import static com.ccs.conclave.api.cii.requests.RestRequests.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class GetRegisteredSchemesTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(GetRegisteredSchemesTests.class);

    @Test
    public void getRegisteredSchemes() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_SC);
        Response getSchemeInfo = getSchemeInfo(CHARITIES_COMMISSION_WITH_SC, schemeInfo.getIdentifier().getId());
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation...");

        RegisteredSchemeInfo expectedIdentifiers = new RegisteredSchemeInfo();
        Identifier identifier = new Identifier();
        identifier.setId(schemeInfo.getIdentifier().getId());
        identifier.setScheme(schemeInfo.getIdentifier().getScheme());
        identifier.setUri(schemeInfo.getIdentifier().getUri());
        identifier.setUri(schemeInfo.getIdentifier().getLegalName());
        expectedIdentifiers.setIdentifier(identifier);
        identifier.setId(schemeInfo.getAdditionalIdentifiers().get(0).getId());
        identifier.setScheme(schemeInfo.getAdditionalIdentifiers().get(0).getScheme());
        identifier.setUri(schemeInfo.getAdditionalIdentifiers().get(0).getUri());
        identifier.setLegalName(schemeInfo.getAdditionalIdentifiers().get(0).getLegalName());
        expectedIdentifiers.getAdditionalIdentifiers().add(identifier);

        logger.info("Get registered schemes...");
        Response getRegisteredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(expectedIdentifiers, getRegisteredSchemesRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getRegisteredSchemesIfAddIdentifiersNotSelected() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        String getSchemeInfo = getSchemeInfoWithEmptyAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);
        SchemeInfo expectedSchemeInfo = OrgDataProvider.getInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation without additional identifiers...");

        RegisteredSchemeInfo expectedIdentifiers = new RegisteredSchemeInfo();
        Identifier identifier = new Identifier();
        identifier.setId(schemeInfo.getIdentifier().getId());
        identifier.setScheme(schemeInfo.getIdentifier().getScheme());
        identifier.setUri(schemeInfo.getIdentifier().getUri());
        identifier.setUri(schemeInfo.getIdentifier().getLegalName());
        expectedIdentifiers.setIdentifier(identifier);

        logger.info("Get registered schemes...");
        Response getRegisteredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(expectedIdentifiers, getRegisteredSchemesRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getRegisteredSchemesAfterUpdate() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        String getSchemeInfo = getSchemeInfoWithEmptyAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);
        SchemeInfo expectedSchemeInfo = OrgDataProvider.getInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);

        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(CHARITIES_COMMISSION_WITH_SC);
        Assert.assertTrue(additionalSchemesInfo.size() == 1, "Two additional identifier are expected, please check the test data!");

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation without additional identifiers...");

        RegisteredSchemeInfo expectedIdentifiers = new RegisteredSchemeInfo();
        Identifier identifier = new Identifier();
        identifier.setId(schemeInfo.getIdentifier().getId());
        identifier.setScheme(schemeInfo.getIdentifier().getScheme());
        identifier.setUri(schemeInfo.getIdentifier().getUri());
        identifier.setUri(schemeInfo.getIdentifier().getLegalName());
        expectedIdentifiers.setIdentifier(identifier);

        logger.info("Get registered schemes...");
        Response getRegisteredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(expectedIdentifiers, getRegisteredSchemesRes);

        logger.info("Adding additional identifier1 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        Response response = RestRequests.updateScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);

        // setting additional identifier for expected result
        identifier.setId(additionalSchemeInfo1.getIdentifier().getId());
        identifier.setScheme(additionalSchemeInfo1.getIdentifier().getScheme());
        identifier.setUri(additionalSchemeInfo1.getIdentifier().getUri());
        identifier.setLegalName(additionalSchemeInfo1.getIdentifier().getLegalName());
        expectedIdentifiers.getAdditionalIdentifiers().add(identifier);

        logger.info("Get registered schemes after updating additional identifier...");
        getRegisteredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(expectedIdentifiers, getRegisteredSchemesRes);

        logger.info("Adding additional identifier2 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        response = RestRequests.updateScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);

        // setting additional identifier for expected result
        identifier.setId(additionalSchemeInfo2.getIdentifier().getId());
        identifier.setScheme(additionalSchemeInfo2.getIdentifier().getScheme());
        identifier.setUri(additionalSchemeInfo2.getIdentifier().getUri());
        identifier.setLegalName(additionalSchemeInfo2.getIdentifier().getLegalName());
        expectedIdentifiers.getAdditionalIdentifiers().add(identifier);

        logger.info("Get registered schemes after updating additional identifier...");
        getRegisteredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(expectedIdentifiers, getRegisteredSchemesRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getRegisteredSchemesAfterDelete() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);
        Response getSchemeInfoRes = getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, schemeInfo.getIdentifier().getId());

        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(DUN_AND_BRADSTREET_WITH_COH);
        Assert.assertTrue(additionalSchemesInfo.size() == 1, "Two additional identifier are expected, please check the test data!");

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation without additional identifiers...");

        RegisteredSchemeInfo expectedIdentifiers = new RegisteredSchemeInfo();
        Identifier identifier = new Identifier();
        identifier.setId(schemeInfo.getIdentifier().getId());
        identifier.setScheme(schemeInfo.getIdentifier().getScheme());
        identifier.setUri(schemeInfo.getIdentifier().getUri());
        identifier.setUri(schemeInfo.getIdentifier().getLegalName());
        expectedIdentifiers.setIdentifier(identifier);

        logger.info("Deleting additional identifier from the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        Response response = RestRequests.deleteScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);

        // setting additional identifier for expected result
        identifier.setId(additionalSchemeInfo1.getIdentifier().getId());
        identifier.setScheme(additionalSchemeInfo1.getIdentifier().getScheme());
        identifier.setUri(additionalSchemeInfo1.getIdentifier().getUri());
        identifier.setLegalName(additionalSchemeInfo1.getIdentifier().getLegalName());
        expectedIdentifiers.getAdditionalIdentifiers().add(identifier);

        logger.info("Get registered schemes after deleting additional identifier...");
        Response getRegisteredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(expectedIdentifiers, getRegisteredSchemesRes);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getRegisteredSchemesWithInvalidOrgId() {
        Response getRegisteredSchemesRes = getRegisteredSchemesInfo("0000000000000000");
        verifyInvalidIdResponse(getRegisteredSchemesRes);
    }
}
