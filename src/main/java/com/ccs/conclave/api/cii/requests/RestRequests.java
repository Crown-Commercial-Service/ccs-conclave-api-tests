package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.common.BaseRequest;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class RestRequests extends BaseRequest {

    public Response getApi(String baseURI) {

        Response res = setUp().get(baseURI);
        if (res.getStatusCode() == 200)
            return res;
        else
            // Todo log error message
            return null;
    }

    public Response postApi(File messageBody, String baseURI) {
        Response res = setUp().when().body(messageBody).post(baseURI);
        if (res.getStatusCode() == 201)
            return res;
        else
            // Todo log error message
            return null;
    }

}
