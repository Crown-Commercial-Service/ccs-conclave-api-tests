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
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;

public class UpdateSchemeTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(UpdateSchemeTests.class);

    @Test
    public void updateScheme_COH_into_DUNS() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);
        // GetScheme response without additional identifiers
        String responseStr = getSchemeInfoWithEmptyAddIdentifiers(schemeInfo, DUN_AND_BRADSTREET_WITH_COH);
        // Get expected SchemeInfo without additional identifiers
        SchemeInfo expectedSchemeInfo = getInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(DUN_AND_BRADSTREET_WITH_COH);
        Assert.assertTrue(additionalSchemesInfo.size() == 1, "Only one additional identifier is expected, please check the test data!");

        logger.info("Performing Post Operation/register organisation with only Primary Identifier");
        Response response = RestRequests.postSchemeInfo(responseStr);

        // verify the post response and ensure only Primary identifier is used for organisation registration
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Adding additional identifier to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo = additionalSchemesInfo.get(0);
        response = RestRequests.updateScheme(getCCSOrgId(), additionalSchemeInfo);
       // verifyResponseCodeForUpdatedResource(response);
        verifyResponseCodeForCreatedResource(response);
        verifyUpdatedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo);
    }

    @Test
    public void updateScheme_COH_and_CHC_into_SC() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY_WITH_COH_CHC);
        // GetScheme response without additional identifiers
        String responseStr = getSchemeInfoWithEmptyAddIdentifiers(schemeInfo, SCOTLAND_CHARITY_WITH_COH_CHC);
        // Get expected SchemeInfo without additional identifiers
        SchemeInfo expectedSchemeInfo = getInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_COH_CHC);

        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(SCOTLAND_CHARITY_WITH_COH_CHC);
        Assert.assertTrue(additionalSchemesInfo.size() == 2, "Two additional identifier are expected, please check the test data!");

        // Perform Post Operation/ register organisation with only Primary Identifier
        Response response = RestRequests.postSchemeInfo(responseStr);

        // verify the post response and ensure only Primary identifier is used for organisation registration
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);

        logger.info("Adding additional identifier1 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        response = RestRequests.updateScheme(getCCSOrgId(), additionalSchemeInfo1);
        verifyResponseCodeForCreatedResource(response);
        verifyUpdatedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo1);

        logger.info("Adding additional identifier2 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        response = RestRequests.updateScheme(getCCSOrgId(), additionalSchemeInfo2);
        verifyResponseCodeForCreatedResource(response);
        verifyUpdatedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo2);
    }

    // Todo @Test
    public void updateScheme_InvalidScheme_into_CHC() {

    }

    // Todo @Test
    public void updateScheme_InvalidOrgId() {

    }


    // Todo @Test
    public void updateScheme_validIdentifierOfAnotherScheme() {

    }
}
