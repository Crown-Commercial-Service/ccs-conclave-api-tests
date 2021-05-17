package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.getOnlySFIdentifier;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.COMPANIES_HOUSE;

public class PostRegisterBuyerTests extends BaseClass {

    private final static Logger logger = Logger.getLogger(PostRegisterBuyerTests.class);

    @Test
    public void postRegisterBuyerInfo_sfid() {

        String sfIdWithURN = getOnlySFIdentifier(COMPANIES_HOUSE);
        String sfid = sfIdWithURN.split("(?=~)")[0];

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postRegisterBuyerInfo("sfid", sfid);
    }

}


