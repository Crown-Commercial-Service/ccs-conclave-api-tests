package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.common.Endpoints;
import com.ccs.conclave.api.cii.data.SchemeRegistry;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static io.restassured.RestAssured.given;


public class RestRequests {
    private final static Logger logger = Logger.getLogger(RestRequests.class);
    private static String baseURI = System.getProperty("base.url");
    private static String apiKey = System.getProperty("api.key");



    public static Response getSchemeInfo(SchemeRegistry scheme, String identifier) {
        String endpoint = baseURI + Endpoints.getSchemeInfoURI + "scheme_id=" + getSchemeCode(scheme) + "&organisation_id=" + identifier;
        logger.info("getSchemeInfo Endpoint: "+ endpoint);
        return get(endpoint);
    }

    public static Response getSchemes() {
        String endpoint = baseURI + Endpoints.getSchemesURI;
        logger.info("getSchemes Endpoint: "+ endpoint);
        return get(endpoint);
    }

    public static Response postSchemeInfo(SchemeRegistry scheme, String identifier) {
        String endpoint = baseURI + Endpoints.postSchemeInfo + getSchemeCode(scheme) + "/" + identifier;
        logger.info("postSchemeInfo Endpoint: "+ endpoint);
        return post(endpoint);
    }

    private static Response get(String baseURI) {
        logger.info(">>> RestRequests::get() >>>");
        Response res = given().header("Apikey", apiKey).expect().defaultParser(Parser.JSON).when().get(baseURI);
        if (res.getStatusCode() == 200 && res.contentType().contains("application/json")) {
            return res;
        } else {
            logger.info("RestRequests::get() call with status code: " + res.getStatusCode());
            return res;
        }
    }

    private static Response post(String baseURI) {
        logger.info(">>> RestRequests::post() >>>");
        Response res = given().expect().defaultParser(Parser.JSON).when().post(baseURI);
        if (res.getStatusCode() == 200 && res.contentType().contains("application/json")) {
            return res;
        } else {
            logger.info("RestRequests::post() call with status code: " + res.getStatusCode());
            return res;
        }
    }
}
