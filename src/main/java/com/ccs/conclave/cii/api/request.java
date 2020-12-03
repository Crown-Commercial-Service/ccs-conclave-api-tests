package com.ccs.conclave.cii.api;

import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class request {

    public static Response get(String baseURI) {
        Response res = given().expect().defaultParser(Parser.JSON).when().get(baseURI);
        if (res.getStatusCode() == 200)
            return res;
        else
            // TODO: log error
            return null;
    }
 

}
