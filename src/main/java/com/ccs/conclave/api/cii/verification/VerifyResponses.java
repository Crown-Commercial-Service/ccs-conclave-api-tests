package com.ccs.conclave.api.cii.verification;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.response.SchemeInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

public class VerifyResponses {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void verifyGetSchemeInfoResponse(OrgDataProvider orgData) {
     SchemeInfoResponse actualResponse = orgData.getSchemeInfoResponse();
     Assert.assertEquals(actualResponse, orgData.getSchemeInfoResponse(), "GetSchemeInfoResponse is not matching!!");
    }
}
