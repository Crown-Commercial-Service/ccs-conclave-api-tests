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

import static com.ccs.conclave.api.cii.data.OrgDataProvider.getAdditionalIdentifierInfo;
import static com.ccs.conclave.api.cii.data.RequestPayloads.getSchemeInfoWithEmptyAddIdentifiers;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class GetAllRegisteredSchemesTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(GetAllRegisteredSchemesTests.class);

    @Test
    public void getRegisteredSchemes() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(NORTHERN_CHARITY_WITH_COH);
        Response getSchemeInfo = getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation...");

        logger.info("Get All registered schemes...");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        verifyAllRegisteredSchemes(registeredSchemesRes, schemeInfo, 1);

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

        logger.info("Get registered schemes...");
        Response registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 0);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getRegisteredSchemesAfterUpdate() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        String getSchemeInfo = getSchemeInfoWithEmptyAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);
        SchemeInfo expectedSchemeInfo = OrgDataProvider.getInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);

        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        Assert.assertEquals(additionalSchemesInfo.size(), 2, "Two additional identifier are expected, please check the test data!");

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation without additional identifiers...");

        logger.info("Get registered schemes...");
        Response registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 0);

        logger.info("Adding additional identifier1 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        Response response = RestRequests.updateScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);

        logger.info("Get registered schemes after updating additional identifier...");
        registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 1);

        logger.info("Adding additional identifier2 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        additionalSchemeInfo2.setCcsOrgId(getCCSOrgId());
        response = RestRequests.updateScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);

        logger.info("Get registered schemes after updating additional identifier...");
        registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 2);

        logger.info("update again and verify registered schemes...");
        response = RestRequests.updateScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);
        registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 2);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getRegisteredSchemesAfterDelete() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);
        Response getSchemeInfoRes = getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, schemeInfo.getIdentifier().getId());

        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(DUN_AND_BRADSTREET_WITH_COH);
        Assert.assertEquals(additionalSchemesInfo.size(), 1, "Two additional identifier are expected, please check the test data!");

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes.asString());
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation without additional identifiers...");

        logger.info("Get registered schemes...");
        Response registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 1);

        logger.info("Deleting additional identifier from the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        Response response = RestRequests.deleteScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);

        logger.info("Get registered schemes after deleting additional identifier...");
        registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 0);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getRegisteredSchemesWithInvalidOrgId() {
        Response getRegisteredSchemesRes = getRegisteredSchemesInfo("0000000000000000");
        verifyInvalidIdResponse(getRegisteredSchemesRes);
    }
}
