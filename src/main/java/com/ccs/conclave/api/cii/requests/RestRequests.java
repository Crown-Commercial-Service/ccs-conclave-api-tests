package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.common.Endpoints;
import com.ccs.conclave.api.cii.data.SchemeRegistry;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.common.StatusCodes.OK;
import static io.restassured.RestAssured.given;


public class RestRequests {
    private final static Logger logger = Logger.getLogger(RestRequests.class);
    private static final String baseURI = System.getProperty("base.url");
    private static final String apiToken = System.getProperty("api.token");
    private static final String deleteToken = System.getProperty("delete.token");
    private static final String clientId = System.getProperty("client.id");

    public static String getBaseURI() {
        return baseURI;
    }

    public static Response getSchemeInfo(SchemeRegistry scheme, String identifier) {
        String endpoint = baseURI + Endpoints.getSchemeInfoURI + "scheme=" + getSchemeCode(scheme) + "&id=" + identifier;
        logger.info("getSchemeInfo Endpoint: " + endpoint);
        return get(endpoint);
    }

    public static Response manageIdentifiers(SchemeRegistry scheme, String identifier, String ccsOrgId) {
        String endpoint = baseURI + Endpoints.adminGetSchemeInfoURI + "scheme=" + getSchemeCode(scheme) + "&id=" + identifier
                + "&ccs_org_id=" + ccsOrgId;
        logger.info("admin GetSchemeInfo Endpoint: " + endpoint);
        return get(endpoint);
    }

    public static Response getRegisteredSchemesInfo(String ccsOrgId) {
        String endpoint = baseURI + Endpoints.getRegisteredSchemesURI + "ccs_org_id=" + ccsOrgId;
        logger.info("get RegisteredSchemeInfo Endpoint: " + endpoint);
        return get(endpoint);
    }

    public static Response getAllRegisteredSchemesInfo(String ccsOrgId) {
        String endpoint = baseURI + Endpoints.getAllRegisteredSchemesURI + "ccs_org_id=" + ccsOrgId;
        logger.info("get RegisteredSchemeInfo Endpoint: " + endpoint);
        return get(endpoint);
    }


    public static Response getSchemes() {
        String endpoint = baseURI + Endpoints.getSchemesURI;
        logger.info("getSchemes Endpoint: " + endpoint);
        return get(endpoint);
    }

    public static Response postSchemeInfo(String requestPayload) {
        String endpoint = baseURI + Endpoints.postSchemeInfoURI;
        return post(endpoint, requestPayload);
    }

    public static Response updateScheme(AdditionalSchemeInfo additionalSchemeInfo) {
        String endpoint = baseURI + Endpoints.updateSchemeURI;
        String accessToken = RequestTestEndpoints.getAccessToken(additionalSchemeInfo.getCcsOrgId());
        return put(endpoint, additionalSchemeInfo, accessToken);
    }

    public static Response deleteScheme(AdditionalSchemeInfo additionalSchemeInfo) {
        String endpoint = baseURI + Endpoints.deleteSchemeURI;
        String accessToken = RequestTestEndpoints.getAccessToken(additionalSchemeInfo.getCcsOrgId());
        return delete(endpoint, additionalSchemeInfo, accessToken);
    }

    public static void deleteOrganisation(String ccsOrgId) {
        logger.info(">>> RestRequests::deleteOrganisation() >>>");
        if (!ccsOrgId.isEmpty()) {
            Response response = RestRequests.deleteAll(RestRequests.getBaseURI() + Endpoints.deleteOrganisationURI +
                    "ccs_org_id=" + ccsOrgId);
            Assert.assertEquals(response.getStatusCode(), OK.getCode(), "Something went wrong while deleting existing organisation!");
            logger.info("Successfully deleted registered organisation.");
        }
        logger.info("Id " + ccsOrgId + "is not registered with CII!!");
    }

    public static void deleteOrganisationWithIdTestEndPt(String id) {
        logger.info(">>> RestRequests::deleteOrganisation() >>>");
        String ccsOrgId = RequestTestEndpoints.getRegisteredOrgId(id); // This is a test endpoint call
        if (!ccsOrgId.isEmpty()) {
            Response response = RestRequests.deleteAll(RestRequests.getBaseURI() + Endpoints.deleteOrganisationURI +
                    "ccs_org_id=" + ccsOrgId);
            Assert.assertEquals(response.getStatusCode(), OK.getCode(), "Something went wrong while deleting existing organisation!");
            logger.info("Successfully deleted registered organisation.");
        }
        logger.info("Id " + id + "is not registered with CII!!");
    }

    public static Response get(String baseURI) {
        logger.info(">>> RestRequests::get() >>>");
        Response res = given().header("x-api-key", apiToken).expect().defaultParser(Parser.JSON).when().get(baseURI);
        logger.info("RestRequests::get() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response post(String baseURI, String requestPayload) {
        logger.info(">>> RestRequests::post() >>>");
        Response res = given().header("x-api-key", apiToken).header("Content-Type", "application/json")
                .body(requestPayload).when().post(baseURI);
        logger.info("RestRequests::post() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response put(String baseURI, AdditionalSchemeInfo requestPayload, String accessToken) {
        logger.info(">>> RestRequests::put() >>>");
        Response res = given().header("x-api-key", apiToken)
                .header("Authorization", "Bearer " + accessToken)
                .param("ccs_org_id", requestPayload.getCcsOrgId())
                .param("identifier[scheme]", requestPayload.getIdentifier().getScheme())
                .param("identifier[id]", requestPayload.getIdentifier().getId())
                .param("clientid", clientId)
                .when().put(baseURI);
        logger.info("RestRequests::put() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response delete(String baseURI, AdditionalSchemeInfo requestPayload, String accessToken) {
        logger.info(">>> RestRequests::delete() >>>");
        Response res = given().header("x-api-key", apiToken)
                .header("Authorization", "Bearer " + accessToken)
                .param("ccs_org_id", requestPayload.getCcsOrgId())
                .param("identifier[scheme]", requestPayload.getIdentifier().getScheme())
                .param("identifier[id]", requestPayload.getIdentifier().getId())
                .param("clientid", clientId)
                .body(requestPayload).when().delete(baseURI);
        logger.info("RestRequests::delete() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response deleteAll(String baseURI) {
        logger.info(">>> RestRequests::deleteAll() >>>");
        Response res = given().header("x-api-key", deleteToken).expect().defaultParser(Parser.JSON).when().delete(baseURI);
        logger.info("RestRequests::deleteAll() call with status code: " + res.getStatusCode());
        return res;
    }
}
