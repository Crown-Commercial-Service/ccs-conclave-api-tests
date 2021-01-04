package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;

public class GetSchemesTests extends BaseClass {

    @Test
    public void getSchemesTest() throws JsonProcessingException {
        Response response = RestRequests.getSchemeNames();
        verifyGetSchemesResponse(response);
    }
}
