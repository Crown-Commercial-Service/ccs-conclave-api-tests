package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import static com.ccs.conclave.api.cii.data.OrgDataProvider.*;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class ManageIdentifiersTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(ManageIdentifiersTests.class);

    // This test case covers the scenario where the additional identifier doesn't know about primary identifier
    @Test
    public void getAdminSchemeInfoForAdditionalIdUnawareOfPrimaryId() {
        // Register Charity Commission Identifier without COH
        SchemeInfo schemeInfo = getInfo(CHARITIES_COMMISSION_WITH_COH);
        SchemeInfo expectedSchemeInfo = getInfoWithoutAddIdentifiers(CHARITIES_COMMISSION_WITH_COH);

        String getSchemeInfoRes = getSchemeInfoWithoutAddIdentifierSection(schemeInfo, CHARITIES_COMMISSION_WITH_COH);
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation...");

        // Search for additional identifier
        String additionalIdentifierId = schemeInfo.getAdditionalIdentifiers().get(0).getId();
        Response response = RestRequests.adminGetSchemeInfo(COMPANIES_HOUSE, additionalIdentifierId, getCCSOrgId());

        // Verify search response with expected response
        //SchemeInfo expectedSchemeInfo = getInfo(COMPANIES_HOUSE_IN_CHARITIES_COMMISSION_AS_ADDITIONAL_ID);
        Response expectedRes = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        verifyResponseCodeForSuccess(expectedRes);

        verifyManageIdentifiersResponse(expectedRes, response);
    }

    // This test case covers the scenario where the additional identifier knows about primary identifier
    // but admin get call is not expected to bring Primary identifier
    // @Test
    public void getAdminSchemeInfoForAdditionalIdKnowsPrimaryId() {
        // Register Charity Commission Identifier without COH
        SchemeInfo schemeInfo = getInfo(DUN_AND_BRADSTREET_WITH_COH_AND_CHC);
        String getSchemeInfoRes = getSchemeInfoWithoutAddIdentifierSection(schemeInfo, DUN_AND_BRADSTREET_WITH_COH_AND_CHC);
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes);
        verifyPostSchemeInfoResponse(schemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation...");

        // Search for additional identifier knows about Primary Id
        String additionalIdentifierId = schemeInfo.getAdditionalIdentifiers().get(0).getId();
        Response response = RestRequests.adminGetSchemeInfo(CHARITIES_COMMISSION_IN_DUN_AND_BRADSTREET_AS_ADDITIONAL_ID, additionalIdentifierId, getCCSOrgId());

        // Verify search response with expected response
        SchemeInfo expectedSchemeInfo = getInfo(COMPANIES_HOUSE_IN_CHARITIES_COMMISSION_AS_ADDITIONAL_ID);
        verifyGetSchemeInfoResponse(expectedSchemeInfo, response);
    }

    //@Test
    public void getSchemeInfoWithAdditionalIdAlreadyRegistered() {

    }

    // @Test
    public void getSchemeInfoWithAdditionalIdAlreadyExistsInOrg() {

    }

    // @Test
    public void getSchemeInfoWithValidIdentifierNotPartOfOrg() {

    }

    // @Test
    public void getSchemeInfoWithInvalidIdentifier() {
    }

    // Todo @Test
    public void getSchemeInfoWithInvalidOrgId() {
    }

    // Todo @Test
    public void getSchemeInfoWithInvalidScheme() {
    }

}
