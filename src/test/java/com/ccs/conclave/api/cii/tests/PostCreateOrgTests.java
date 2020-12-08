package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class PostCreateOrgTests extends BaseClass {

    RestRequests restRequests = new RestRequests();
    File jsonDataInFile = new File("src/main/java/com/ccs/conclave/api/payload/payload.json");


    // Get scheme info from companies house
    @Test(dataProvider = "CompaniesHouse")
    public void postOrgReg() {
        Response response = restRequests.postApi(jsonDataInFile,"https://reqres.in/api/users/");
        response.then().log().status();
        Assert.assertEquals(response.getStatusCode(),201);
    }

}
