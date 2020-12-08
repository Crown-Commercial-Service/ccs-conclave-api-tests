package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.common.BaseRequest;
import io.restassured.response.Response;

import java.io.File;


public class RestRequests extends BaseRequest {

    public Response getApi(String baseURI) {
        Response res = setUp().get(baseURI);
        if (res.getStatusCode() == 200)
            return res;
        else
            res.then().log().ifError();
        return null;
    }

    public Response postApi(String messageBody, String baseURI) {
        Response res = setUp().when().body(messageBody).post(baseURI);
        if (res.getStatusCode() == 201)
            return res;
        else
            res.then().log().ifError();
        return null;
    }

}
