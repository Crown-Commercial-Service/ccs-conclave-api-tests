package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.SignupData;
import com.ccs.conclave.api.common.Endpoints;
import com.ccs.conclave.api.cii.data.SchemeRegistry;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.common.StatusCodes.OK;
import static io.restassured.RestAssured.given;


public class RestRequests {
    private final static Logger logger = Logger.getLogger(RestRequests.class);
    private static final String ciiBaseURI = System.getProperty("cii.base.url");
    private static final String conclaveBaseURI = System.getProperty("conclave.base.url");
    private static final String conclaveLoginURI = System.getProperty("conclave.login.url");
    private static final String loginToken = System.getProperty("login.token");
    private static final String auth0URI = System.getProperty("auth0.url");
    private static final String apiToken = System.getProperty("api.token");
    private static final String deleteToken = System.getProperty("delete.token");
    private static final String clientId = System.getProperty("client.id");
    private static final String clientSecret= System.getProperty("client.secret");

    public static String getCiiBaseURI() {
        return ciiBaseURI;
    }

    public static String getClientId() {
        return clientId;
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public static Response getSchemeInfo(SchemeRegistry scheme, String identifier) {
        String endpoint = ciiBaseURI + Endpoints.getSchemeInfoURI + "scheme=" + getSchemeCode(scheme) + "&id=" + identifier;
        logger.info("getSchemeInfo Endpoint: " + endpoint);
        return get(endpoint);
    }

    public static Response manageIdentifiers(SchemeRegistry scheme, String identifier, String ccsOrgId) {
        String endpoint = Endpoints.adminGetSchemeInfoURI + "scheme=" + getSchemeCode(scheme) + "&id=" + identifier
                + "&ccs_org_id=" + ccsOrgId + "&clientid=" + getClientId();
        logger.info("admin GetSchemeInfo Endpoint: " + endpoint);
        String accessToken = RequestTestEndpoints.getAccessToken(ccsOrgId);
        return getEndpointWithAccessToken(endpoint, accessToken);
    }

    public static Response getRegisteredSchemesInfo(String ccsOrgId) {
        String endpoint = ciiBaseURI + Endpoints.getRegisteredSchemesURI + "ccs_org_id=" + ccsOrgId;
        logger.info("get RegisteredSchemeInfo Endpoint: " + endpoint);
        return get(endpoint);
    }

    public static Response getAllRegisteredSchemesInfo(String ccsOrgId) {
        String endpoint = Endpoints.getAllRegisteredSchemesURI + "ccs_org_id=" + ccsOrgId + "&clientid=" + getClientId();
        logger.info("get RegisteredSchemeInfo Endpoint: " + endpoint);
        String accessToken = RequestTestEndpoints.getAccessToken(ccsOrgId);
        return getEndpointWithAccessToken(endpoint, accessToken);
    }

    public static Response getSchemes() {
        String endpoint = ciiBaseURI + Endpoints.getSchemesURI;
        logger.info("getSchemes Endpoint: " + endpoint);
        return get(endpoint);
    }

    public static Response postSchemeInfo(String requestPayload) {
        String endpoint = ciiBaseURI + Endpoints.postSchemeInfoURI;
        return postToCIIAPI(endpoint, requestPayload);
    }

    public static Response updateScheme(AdditionalSchemeInfo additionalSchemeInfo) {
        String endpoint = ciiBaseURI + Endpoints.updateSchemeURI;
        String accessToken = RequestTestEndpoints.getAccessToken(additionalSchemeInfo.getCcsOrgId());
        return put(endpoint, additionalSchemeInfo, accessToken);
    }

    public static Response deleteScheme(AdditionalSchemeInfo additionalSchemeInfo) {
        String endpoint = ciiBaseURI + Endpoints.deleteSchemeURI;
        String accessToken = RequestTestEndpoints.getAccessToken(additionalSchemeInfo.getCcsOrgId());
        return delete(endpoint, additionalSchemeInfo, accessToken);
    }

    public static void deleteOrganisation(String ccsOrgId) {
        logger.info(">>> RestRequests::deleteOrganisation() >>>");
        if (!ccsOrgId.isEmpty()) {
            Response response = RestRequests.deleteAll(RestRequests.getCiiBaseURI() + Endpoints.deleteOrganisationURI +
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
            Response response = RestRequests.deleteAll(RestRequests.getCiiBaseURI() + Endpoints.deleteOrganisationURI +
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

    private static Response getEndpointWithAccessToken(String endpoint, String accessToken) {
        logger.info(">>> RestRequests::getEndpointWithAccessToken() >>>");
        Response res = given().header("x-api-key", apiToken)
                .header("Authorization", "Bearer " + accessToken)
                .expect().defaultParser(Parser.JSON).when().get(ciiBaseURI + endpoint);
        logger.info("RestRequests::getEndpointWithAccessToken() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response postToCIIAPI(String baseURI, String requestPayload) {
        logger.info(">>> RestRequests::postToCIIAPI() >>>");
        Response res = given().header("x-api-key", apiToken).header("Content-Type", "application/json")
                .body(requestPayload).when().post(baseURI);
        logger.info("RestRequests::postToCIIAPI() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response postToConclaveAPI(String endPoint, Object requestPayload) {
        logger.info(">>> RestRequests::postToConclaveAPI() >>>");
        Response res = given().header("Content-Type", "application/json")
                .body(requestPayload).when().post(conclaveBaseURI + endPoint);
        Assert.assertEquals(res.getStatusCode(), OK.getCode(), "Something went wrong while Conclave post operation!");
        Assert.assertEquals(res.asString().isEmpty(), false, "Something went wrong while Conclave post operation!");
        logger.info("RestRequests::postToConclaveAPI() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response loginToConclaveAPI(String endPoint, Object loginData) {
        logger.info(">>> RestRequests::loginToConclaveAPI() >>>");
        Response res = given()
                .header("x-api-key", loginToken)
                .header("Content-Type", "application/json")
                .body(loginData)
                .when().post(conclaveLoginURI + endPoint);
        Assert.assertEquals(res.getStatusCode(), OK.getCode(), "Something went wrong while Conclave Login post operation!");
        logger.info("RestRequests::loginToConclaveAPI() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response postToAuth0(String endPoint, SignupData signupData) {
        logger.info(">>> RestRequests::postToAuth0() >>>");
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);
        Response res = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .param("connection", signupData.getConnection())
                .param("email", signupData.getEmail())
                .param("password", signupData.getPassword())
                .param("client_id", clientId)
                .when().post(auth0URI + endPoint);
        logger.info("RestRequests::postToAuth0() call with status code: " + res.getStatusCode());
        Assert.assertEquals(res.getStatusCode(), OK.getCode(), "Something went wrong while Conclave Login post operation!");
        Assert.assertEquals(res.asString().contains(signupData.getEmail()), true, "Registered email address is not in Conclave Login post response!");
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
