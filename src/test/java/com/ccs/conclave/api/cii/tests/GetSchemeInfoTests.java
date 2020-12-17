package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.cii.response.SchemeInfoResponse;
import com.ccs.conclave.api.common.BaseClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;
import static com.ccs.conclave.api.common.SchemeRegistry.*;

public class GetSchemeInfoTests extends BaseClass {

    private final static Logger logger = Logger.getLogger(GetSchemeInfoTests.class);

    @Test
    public void getCompaniesHouseSchemeInfo() {
        OrgDataProvider orgData = new OrgDataProvider();
        SchemeInfo schemeInfo = orgData.getInfo(COMPANIES_HOUSE);
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(orgData, response.as(SchemeInfoResponse.class));
        logger.info("Response is"+ response.asString());
    }

    @Test
    public void getCharityBaseSchemeInfoWithCompaniesHouse() {
        // Todo
    }
}
