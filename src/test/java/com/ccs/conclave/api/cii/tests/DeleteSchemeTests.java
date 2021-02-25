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
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;

public class DeleteSchemeTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(DeleteSchemeTests.class);

    @Test
    public void deleteScheme_COH_from_DUN() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(DUN_AND_BRADSTREET_WITH_COH);
        Assert.assertTrue(additionalSchemesInfo.size() == 1, "Only one additional identifier is expected, please check the test data!");

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WITH_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Deleting additional identifier to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo = additionalSchemesInfo.get(0);
        additionalSchemeInfo.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo);
        verifyResponseCodeForUpdatedOrDeletedResource(response);
        verifyDeletedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo);

        logger.info("Deleting identifier deleted already");
        response = RestRequests.deleteScheme(additionalSchemeInfo);
        verifyInvalidIdResponse(response);
    }

    @Test
    public void deleteScheme_COH_and_CHC_from_SC() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY_WITH_COH_CHC);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(SCOTLAND_CHARITY_WITH_COH_CHC);
        Assert.assertTrue(additionalSchemesInfo.size() == 2, "Two additional identifiers are expected, please check the test data!");

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(SCOTLAND_CHARITY_WITH_COH_CHC, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Deleting additional identifier1 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo1);
        verifyResponseCodeForUpdatedOrDeletedResource(response);
        verifyDeletedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo1);

        logger.info("Deleting additional identifier2 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        additionalSchemeInfo2.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo2);
        verifyResponseCodeForUpdatedOrDeletedResource(response);
        verifyDeletedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo2);
    }

    @Test
    public void deleteScheme_and_updateScheme() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_TWO_COH);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(CHARITIES_COMMISSION_WITH_TWO_COH);
        Assert.assertTrue(additionalSchemesInfo.size() == 2, "Two additional identifiers are expected, please check the test data!");

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(CHARITIES_COMMISSION_WITH_TWO_COH, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        // Perform Post Operation
        response = RestRequests.postSchemeInfo(response.asString());
        verifyPostSchemeInfoResponse(schemeInfo, response);
        logger.info("Successful post operation...");

        logger.info("Deleting additional identifier1 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        additionalSchemeInfo1.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo1);
        verifyResponseCodeForUpdatedOrDeletedResource(response);
        verifyDeletedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo1);

        logger.info("Updating additional identifier1 to the existing organisation...");
        response = RestRequests.updateScheme(additionalSchemeInfo1);
        verifyResponseCodeForUpdatedOrDeletedResource(response);
        verifyUpdatedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo1);

        logger.info("Deleting additional identifier1 to the existing organisation...");
        response = RestRequests.deleteScheme(additionalSchemeInfo1);
        verifyResponseCodeForUpdatedOrDeletedResource(response);
        verifyDeletedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo1);

        logger.info("Deleting additional identifier2 to the existing organisation...");
        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        additionalSchemeInfo2.setCcsOrgId(getCCSOrgId());
        response = RestRequests.deleteScheme(additionalSchemeInfo2);
        verifyResponseCodeForUpdatedOrDeletedResource(response);
        verifyDeletedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo2);

        logger.info("Updating additional identifier2 to the existing organisation...");
        response = RestRequests.updateScheme(additionalSchemeInfo2);
        verifyResponseCodeForUpdatedOrDeletedResource(response);
        verifyUpdatedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo2);

        logger.info("Deleting additional identifier2 to the existing organisation...");
        response = RestRequests.deleteScheme(additionalSchemeInfo2);
        verifyResponseCodeForUpdatedOrDeletedResource(response);
        verifyDeletedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo2);
    }

    @Test
    public void deleteScheme_InvalidScheme() {
    }

    // Todo @Test
    public void deleteScheme_InvalidOrgId() {
    }

    // Todo @Test
    public void deleteScheme_PrimaryIdentifier() {
    }
}
