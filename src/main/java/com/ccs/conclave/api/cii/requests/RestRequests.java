package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.common.Endpoints;
import com.ccs.conclave.api.common.SchemeRegistry;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import static com.ccs.conclave.api.common.SchemeRegistry.*;
import static io.restassured.RestAssured.given;

public class RestRequests {
    public static Response getSchemeInfo(SchemeRegistry scheme, String identifier) {
        String baseURI = Endpoints.getSchemeInfoURI + getSchemeCode(scheme) + "/" + identifier;
        return get(baseURI);
    }

    public static Response getSchemeNames() {
        String baseURI = Endpoints.getSchemeNamesURI;
        return get(baseURI);
    }

    public static Response postSchemeInfo(SchemeRegistry scheme, String identifier) {
        String baseURI = Endpoints.postSchemeInfo + getSchemeCode(scheme) + "/" + identifier;
        return post(baseURI);
    }

    private static Response get(String baseURI) {
        Response res = given().expect().defaultParser(Parser.JSON).when().get(baseURI);
        if (res.getStatusCode() == 200 && res.contentType().contains("application/json"))
            return res;
        else
            res.then().log().ifError();
        return null;
    }

    private static Response post(String baseURI) {
        Response res = given().expect().defaultParser(Parser.JSON).when().post(baseURI);
        if (res.getStatusCode() == 200 && res.contentType().contains("application/json"))
            return res;
        else
            res.then().log().ifError();
        return null;
    }
}
