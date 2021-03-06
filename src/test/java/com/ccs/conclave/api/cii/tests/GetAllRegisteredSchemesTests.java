package com.ccs.conclave.api.cii.tests;

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
import static com.ccs.conclave.api.cii.requests.RestRequests.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class GetAllRegisteredSchemesTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(GetAllRegisteredSchemesTests.class);

    @Test
        public void getAllRegisteredSchemes() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(NORTHERN_CHARITY_WITH_COH);
        SchemeInfo schemeInfoWithoutSFId = getExpSchemeInfoWithoutSFIdentifier(NORTHERN_CHARITY_WITH_COH);

        Response getSchemeInfo = getSchemeInfo(NORTHERN_CHARITY_WITH_COH, schemeInfo.getIdentifier().getId());
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo.asString());
        verifyPostSchemeInfoResponse(schemeInfoWithoutSFId, postSchemeRes);
        logger.info("Successfully registered organisation...");

        // set hidden = false as organisation is registered with additional identifier
        schemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");
        // SF id is always hidden=true, which is set on the test data

        logger.info("Get All registered schemes...");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        verifyAllRegisteredSchemes(registeredSchemesRes, schemeInfo);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getAllRegisteredSchemesIfAddIdentifiersNotSelected() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        SchemeInfo expectedPostRes = getExpSchemeInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);

        String getSchemeInfo = getSchemeInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo);
        verifyPostSchemeInfoResponse(expectedPostRes, postSchemeRes);
        logger.info("Successfully registered organisation without additional identifiers...");

        // set hidden = true because no additional identifiers used for registration
        schemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        schemeInfo.getAdditionalIdentifiers().get(1).setHidden("true");
        // SF id is always hidden=true, which is set on the test data

        logger.info("Get registered schemes...");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        verifyAllRegisteredSchemes(registeredSchemesRes, schemeInfo);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getAllRegisteredSchemesAfterUpdate() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(SCOTLAND_CHARITY_WITH_CHC_COH);
        SchemeInfo expectedPosRes = getExpSchemeInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);

        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiersExceptSF(SCOTLAND_CHARITY_WITH_CHC_COH);
        Assert.assertEquals(additionalSchemesInfo.size(), 2, "Two additional identifier are expected, please check the test data!");

        String getSchemeInfo = getSchemeInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_CHC_COH);

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfo);
        verifyPostSchemeInfoResponse(expectedPosRes, postSchemeRes);
        logger.info("Successfully registered organisation without additional identifiers...");

        // set hidden = true because no additional identifiers used for registration
        SchemeInfo expectedSchemeInfo = schemeInfo;
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        expectedSchemeInfo.getAdditionalIdentifiers().get(1).setHidden("true");
        // SF id is always hidden=true, which is set on the test data

        logger.info("Get registered schemes...");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);

        logger.info("Adding additional identifier1 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        Response response = RestRequests.updateScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);

        logger.info("Adding additional identifier2 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        additionalSchemeInfo2.setCcsOrgId(getCCSOrgId());
        response = RestRequests.updateScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);

        // set hidden = true because no additional identifiers used for registration
        expectedSchemeInfo = schemeInfo;
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().get(1).setHidden("false");

        logger.info("Get registered schemes...");
        registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);

        logger.info("update again and verify registered schemes...");
        response = RestRequests.updateScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);
        registeredSchemesRes = getRegisteredSchemesInfo(getCCSOrgId());
        verifyRegisteredSchemes(registeredSchemesRes, schemeInfo, 2);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getAllRegisteredSchemesAfterDelete() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_COH);
        Response getSchemeInfoRes = getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, schemeInfo.getIdentifier().getId());
        SchemeInfo schemeInfoWithoutSFId = getExpSchemeInfoWithoutSFIdentifier(DUN_AND_BRADSTREET_WITH_COH);
        SchemeInfo expSchemeInfoWithOnlySFId = getExpSchemeInfoWithOnlySFIdentifier(DUN_AND_BRADSTREET_WITH_COH);
        SchemeInfo expectedSchemeInfoWithoutAddIds = getExpSchemeInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);

        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getExpSchemeInfoWithOnlyAddIdentifiersIncludeSF(DUN_AND_BRADSTREET_WITH_COH);
        Assert.assertEquals(additionalSchemesInfo.size(), 2, "Two additional identifier are expected including SF, please check the test data!");

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes.asString());
        verifyPostSchemeInfoResponse(schemeInfoWithoutSFId, postSchemeRes);
        logger.info("Successfully registered organisation without additional identifiers...");

        // set hidden = true because no additional identifiers used for registration
        schemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");
        // SF id is always hidden=true, which is set on the test data

        logger.info("Get registered schemes...");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        verifyAllRegisteredSchemes(registeredSchemesRes, schemeInfo);

        logger.info("Deleting additional identifier from the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        Response response = RestRequests.deleteScheme(additionalSchemeInfo1);
        verifyResponseCodeForSuccess(response);

        logger.info("Get all registered schemes...");
        registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        verifyAllRegisteredSchemes(registeredSchemesRes, expSchemeInfoWithOnlySFId);

        // after deleting the additional identifier only SF id is left as additional identifier
        logger.info("Deleting SF identifier from the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        additionalSchemeInfo2.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo2);
        verifyResponseCodeForSuccess(response);

        // after deleting the additional identifier get all registered schemes won't return the same
        logger.info("Get all registered schemes...");
        registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId());
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfoWithoutAddIds);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

    @Test
    public void getAllRegisteredSchemesWithInvalidOrgId() {
        Response getRegisteredSchemesRes = getRegisteredSchemesInfo("0000000000000000");
        verifyInvalidIdResponse(getRegisteredSchemesRes);
    }
}
