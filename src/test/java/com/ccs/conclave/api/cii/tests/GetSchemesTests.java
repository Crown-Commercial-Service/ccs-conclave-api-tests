package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;

public class GetSchemesTests extends BaseClass {

    @Test
    public void getSchemesTest() throws IOException {
        Response response = RestRequests.getSchemes();
        verifyGetSchemesResponse(response);
    }
}