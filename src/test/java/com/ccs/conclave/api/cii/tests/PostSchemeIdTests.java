package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.cii.response.PostSchemeInfoResponse;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import static com.ccs.conclave.api.cii.verification.VerifyResponses.verifyPostSchemeInfoResponse;
import static com.ccs.conclave.api.common.SchemeRegistry.COMPANIES_HOUSE;

public class PostSchemeIdTests extends BaseClass {

    private final static Logger logger = Logger.getLogger(PostSchemeIdTests.class);

    @Test
    public void postCompaniesHouseSchemeInfo() {
        OrgDataProvider orgData = new OrgDataProvider();
        SchemeInfo schemeInfo = orgData.getInfo(COMPANIES_HOUSE);
        Response response = RestRequests.postSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        PostSchemeInfoResponse postSchemeInfoResponse = response.as(PostSchemeInfoResponse.class);
        verifyPostSchemeInfoResponse(COMPANIES_HOUSE, orgData, postSchemeInfoResponse);

        String orgId = postSchemeInfoResponse.getSchemeInfo().getOrgIdentifier();
        // Todo: verifyOrgIdentifier(orgId);
        // Todo: connect to DB and verify scheme identifier table entries

    }


}
