package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import com.ccs.conclave.api.utilities.JsonMgr;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PostCreateOrgTests extends BaseClass {

    RestRequests restRequests = new RestRequests();


    // Create Organisation registration
    @Test(dataProvider = "CompaniesHouse")
    public void postOrgReg() {
        String payload = JsonMgr.readFile("payload.json");
        Response response = restRequests.postApi(payload, "");
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(), 201);
    }

}
