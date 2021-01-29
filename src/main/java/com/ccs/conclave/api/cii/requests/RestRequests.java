package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.common.Endpoints;
import com.ccs.conclave.api.cii.data.SchemeRegistry;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static io.restassured.RestAssured.given;


public class RestRequests {
    private final static Logger logger = Logger.getLogger(RestRequests.class);
    private static String baseURI = System.getProperty("base.url");
    private static String apiKey = System.getProperty("api.key");

    public static String getBaseURI() {
        return baseURI;
    }

    public static Response getSchemeInfo(SchemeRegistry scheme, String identifier) {
        String endpoint = baseURI + Endpoints.getSchemeInfoURI + "scheme=" + getSchemeCode(scheme) + "&id=" + identifier;
        logger.info("getSchemeInfo Endpoint: " + endpoint);
        return get(endpoint);
    }

    public static Response getSchemes() {
        String endpoint = baseURI + Endpoints.getSchemesURI;
        logger.info("getSchemes Endpoint: " + endpoint);
        return get(endpoint);
    }

    public static Response postSchemeInfo(String requestPayload) {
        String endpoint = baseURI + Endpoints.postSchemeInfo;
        return post(endpoint, requestPayload);
    }

    public static Response get(String baseURI) {
        logger.info(">>> RestRequests::get() >>>");
        Response res = given().header("Apikey", apiKey).expect().defaultParser(Parser.JSON).when().get(baseURI);
        logger.info("RestRequests::get() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response post(String baseURI, String requestPayload) {
        logger.info(">>> RestRequests::post() >>>");
        Response res = given().header("Apikey", apiKey).header("Content-Type", "application/json")
                .body(requestPayload).when().post(baseURI);
        logger.info("RestRequests::post() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response delete(String baseURI) {
        logger.info(">>> RestRequests::get() >>>");
        Response res = given().header("Apikey", apiKey).expect().defaultParser(Parser.JSON).when().delete(baseURI);
        if (res.getStatusCode() == 200 && res.contentType().contains("application/json")) {
            return res;
        } else {
            logger.info("RestRequests::get() call with status code: " + res.getStatusCode());
            return res;
        }
    }
}
