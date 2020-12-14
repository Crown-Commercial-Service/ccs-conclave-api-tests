package com.ccs.conclave.api.cii.verification;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.response.SchemeInfoResponse;
import org.testng.Assert;

public class VerifyResponses {

    public static void verifyGetSchemeInfoResponse(OrgDataProvider actualRes, SchemeInfoResponse expectedRes) {
        Assert.assertEquals(actualRes, expectedRes, "GetSchemeInfoResponse is not matching!!");
    }
}
