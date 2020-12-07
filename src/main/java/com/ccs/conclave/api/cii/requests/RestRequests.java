package com.ccs.conclave.api.cii.requests;

import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestRequests {
    public Response get(String baseURI) {
        Response res = given().expect().defaultParser(Parser.JSON).when().get(baseURI);
        if (res.getStatusCode() == 200)
            return res;
        else
            // Todo log error message
            return null;
    }
}
