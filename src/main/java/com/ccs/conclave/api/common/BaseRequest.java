package com.ccs.conclave.api.common;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseRequest {
    protected RequestSpecification requestSpecification;
    protected String mediaType = "application/json";


    public RequestSpecification setUp() {
        requestSpecification = given().accept(mediaType).contentType(ContentType.JSON).log().ifValidationFails();
        return requestSpecification;
    }

}
