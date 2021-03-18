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

import static com.ccs.conclave.api.cii.data.OrgDataProvider.getInfoWithOnlyAdditionalIdentifier;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.deleteOrganisation;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class SFIdentifierTests extends BaseClass {
    private final static Logger logger = Logger.getLogger(SFIdentifierTests.class);

    @Test
    public void deleteScheme_COH_from_DUN() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);
        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getInfoWithOnlyAdditionalIdentifier(DUN_AND_BRADSTREET_WITH_COH);
        Assert.assertEquals(additionalSchemesInfo.size() , 1, "Only one additional identifier is expected, please check the test data!");

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
        verifyResponseCodeForSuccess(response);
        verifyDeletedScheme(getCCSOrgId(), additionalSchemeInfo);

        logger.info("Deleting identifier deleted already");
        response = RestRequests.deleteScheme(additionalSchemeInfo);
        verifyInvalidIdResponse(response);

        // Delete Database entry if the Org. is already registered
        deleteOrganisation(getCCSOrgId());
    }

}
