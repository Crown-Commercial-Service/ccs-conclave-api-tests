package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.common.Endpoints;
import com.ccs.conclave.api.common.SchemeRegistry;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import static com.ccs.conclave.api.common.SchemeRegistry.*;
import static io.restassured.RestAssured.given;

public class RestRequests {
    public static Response getSchemeInfo(SchemeRegistry scheme, String Id) {
        String baseURI = Endpoints.getSchemeInfoURI + getSchemeCode(scheme) + "/" + Id;
        return get(baseURI);
    }

    private static Response get(String baseURI) {
        Response res = given().expect().defaultParser(Parser.JSON).when().get(baseURI);
        if (res.getStatusCode() == 200)
            return res;
        else
            // Todo log error message
            return null;
    }
}
