package com.ccs.conclave.api.cii.tests;

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
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class ManageIdentifiersTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(ManageIdentifiersTests.class);

    // NOTE:- the response of manageIdentifiers endpoint is verified using GetSchemeInfo(Get call brings registry info)

    // This test covers the scenario where the additional identifier doesn't know about primary identifier
    @Test
    public void manageIdsGetSchemeInfoForAdditionalIdUnawareOfPrimaryId() throws JSONException {
        // Register Primary Identifier without additional
        SchemeInfo schemeInfo = getInfo(DUN_AND_BRADSTREET_WITH_COH);
        SchemeInfo expectedSchemeInfo = getInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);
        String getSchemeInfoRes = getSchemeInfoWithoutAddIdentifierSection(schemeInfo, DUN_AND_BRADSTREET_WITH_COH);
        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation...");

        // Search for additional identifier
        String additionalIdentifierId = schemeInfo.getAdditionalIdentifiers().get(0).getId();
        Response response = RestRequests.manageIdentifiers(COMPANIES_HOUSE, additionalIdentifierId, getCCSOrgId());

        // Verify search response with expected response
        Response expectedRes = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getAdditionalIdentifiers().get(0).getId());
        verifyResponseCodeForSuccess(expectedRes);

        verifyManageIdentifiersResponse(expectedRes, response);
    }

    // This test covers the scenario where the additional identifier knows about primary identifier
    // manageIdentifiers get call is expected to bring Primary identifier if it is part of same organisation
     @Test
    public void manageIdsGetSchemeInfoForAdditionalIdKnowsPrimaryId() throws JSONException {
        // Register Primary Identifier without additional
        SchemeInfo schemeInfo = getInfo(CHARITIES_COMMISSION_WITH_SC);
        SchemeInfo expectedSchemeInfo = getInfoWithoutAddIdentifiers(CHARITIES_COMMISSION_WITH_SC);
        String getSchemeInfoRes = getSchemeInfoWithoutAddIdentifierSection(schemeInfo, CHARITIES_COMMISSION_WITH_SC);

         // Verify search response with expected response
         Response expectedRes = RestRequests.getSchemeInfo(SCOTLAND_CHARITY, schemeInfo.getAdditionalIdentifiers().get(0).getId());
         verifyResponseCodeForSuccess(expectedRes);

        Response postSchemeRes = RestRequests.postSchemeInfo(getSchemeInfoRes);
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes);
        logger.info("Successfully registered organisation...");

        // Search for additional identifier
        String additionalIdentifierId = schemeInfo.getAdditionalIdentifiers().get(0).getId();
        Response response = RestRequests.manageIdentifiers(SCOTLAND_CHARITY, additionalIdentifierId, getCCSOrgId());

        verifyManageIdentifiersResponse(expectedRes, response);
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
