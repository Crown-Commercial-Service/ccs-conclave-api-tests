package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.common.Endpoints;
import com.ccs.conclave.api.common.SchemeRegistry;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import static com.ccs.conclave.api.common.SchemeRegistry.*;
import static io.restassured.RestAssured.given;

public class RestRequests {
    private final static Logger logger = Logger.getLogger(RestRequests.class);
    private static String baseURI = System.getProperty("base.url");

    public static Response getSchemeInfo(SchemeRegistry scheme, String identifier) {
        logger.info("getSchemeInfo Endpoint: "+ baseURI);
        String endpoint = baseURI + Endpoints.getSchemeInfoURI + getSchemeCode(scheme) + "/" + identifier;
        logger.info("getSchemeInfo Endpoint: "+ endpoint);
        return get(endpoint);
    }

    public static Response getSchemeNames() {
        String endpoint = baseURI + Endpoints.getSchemeNamesURI;
        return get(endpoint);
    }

    public static Response postSchemeInfo(SchemeRegistry scheme, String identifier) {
        String endpoint = baseURI + Endpoints.postSchemeInfo + getSchemeCode(scheme) + "/" + identifier;
        return post(endpoint);
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
