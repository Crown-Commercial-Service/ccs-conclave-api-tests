package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.SignupData;
import com.ccs.conclave.api.common.BaseClass;
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


public class RestRequests extends BaseClass {
    private final static Logger logger = Logger.getLogger(RestRequests.class);
    private static final String ciiBaseURI = System.getProperty("cii.base.url");
    private static final String ciiMockBaseURI = System.getProperty("cii.mock.base.url");
    private static final String conclaveBaseURI = System.getProperty("conclave.base.url");
    private static final String conclaveLoginURI = System.getProperty("conclave.login.url");
    private static final String loginToken = System.getProperty("login.token");
    private static final String auth0URI = System.getProperty("auth0.url");
    private static final String apiToken = System.getProperty("api.token");
    private static final String migrationToken = System.getProperty("migration.token");
    private static final String deleteToken = System.getProperty("delete.token");
    private static final String clientId = System.getProperty("client.id");
    private static final String clientSecret= System.getProperty("client.secret");

    public static String getBaseURI() {
        if(isMockTestEnabled()) {
            return ciiMockBaseURI;
        }
        return ciiBaseURI;
    }

    public static String getClientId() {
        return clientId;
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public static Response getSchemeInfo(SchemeRegistry scheme, String identifier) {
        String path = getBaseURI() + Endpoints.getSchemeInfoURI;
        if (isMockTestEnabled()) {
            path += "scheme=" + getSchemeCode(scheme) + "&id=" + identifier;
        } else {
            path = path.replace("{{scheme-id}}", getSchemeCode(scheme));
            path = path.replace("{{identifier-id}}", identifier);
        }
        logger.info("getSchemeInfo Endpoint: " + path);
        return get(path);
    }

    public static Response manageIdentifiers(SchemeRegistry scheme, String identifier, String ccsOrgId) {
        String path = getBaseURI() + Endpoints.adminGetSchemeInfoURI;
        if(isMockTestEnabled()) {
            path = "scheme=" + getSchemeCode(scheme) + "&id=" + identifier
                    + "&ccs_org_id=" + ccsOrgId + "&clientid=" + getClientId();
        } else {
            path = path.replace("{{ccs_org_id}}", ccsOrgId);
            path = path.replace("{{scheme-id}}", getSchemeCode(scheme));
            path = path.replace("{{identifier-id}}", identifier);
        }
        logger.info("admin GetSchemeInfo Endpoint: " + path);
        String accessToken = RequestTestEndpoints.getAccessToken(ccsOrgId);
        return getEndpointWithAccessToken(path, accessToken);
    }

    public static Response getRegisteredSchemesInfo(String ccsOrgId) {
        String path = getBaseURI() + Endpoints.getRegisteredSchemesURI;
        if(isMockTestEnabled()) {
            path += "ccs_org_id=" + ccsOrgId;
        } else {
            path = path.replace("{{ccs_org_id}}", ccsOrgId);
        }
        logger.info("get RegisteredSchemeInfo Endpoint: " + path);
        return get(path);
    }

    public static Response getAllRegisteredSchemesInfo(String ccsOrgId) {
        String path = getBaseURI() + Endpoints.getAllRegisteredSchemesURI;
        if(isMockTestEnabled()) {
            path += "ccs_org_id=" + ccsOrgId;
            return getEndpointWithNoAccessToken(path);
        } else {
            path += "ccs_org_id=" + ccsOrgId + "&clientid=" + getClientId();
        }
        logger.info("get All RegisteredSchemeInfo Endpoint: " + path);
        String accessToken = RequestTestEndpoints.getAccessToken(ccsOrgId);
        return getEndpointWithAccessToken(path, accessToken);
    }

    public static Response getSchemes() {
        String endpoint = getBaseURI() + Endpoints.getSchemesURI;
        logger.info("getSchemes Endpoint: " + endpoint);
        return get(endpoint);
    }

    public static Response postSchemeInfo(String requestPayload) {
        String endpoint = getBaseURI() + Endpoints.postSchemeInfoURI;
        return postToCIIAPI(endpoint, requestPayload);
    }

    public static Response postSFInfo(String accountIdType, String accountId) {
        String endpoint = null;
        if (isMockTestEnabled()) {
            endpoint = ciiMockBaseURI + Endpoints.postRegisterBuyerURI + "account_id_type=" + accountIdType + "&account_id=" + accountId;
        } else {
            endpoint = ciiBaseURI + Endpoints.postRegisterBuyerURI + "account_id_type=" + accountIdType + "&account_id=" + accountId;
        }
        return postSFInfoToCIIMOCK(endpoint);
    }

    public static Response updateScheme(AdditionalSchemeInfo additionalSchemeInfo) {
        String accessToken = RequestTestEndpoints.getAccessToken(additionalSchemeInfo.getCcsOrgId());
        return put(additionalSchemeInfo, accessToken);
    }

    public static Response deleteScheme(AdditionalSchemeInfo additionalSchemeInfo) {
        String path = getBaseURI() + Endpoints.deleteSchemeURI;
        String accessToken = RequestTestEndpoints.getAccessToken(additionalSchemeInfo.getCcsOrgId());
        return delete(path, additionalSchemeInfo, accessToken);
    }

    public static void deleteOrganisation(String ccsOrgId) {
        if (!ccsOrgId.isEmpty()) {
            String path = getBaseURI() + Endpoints.deleteOrganisationURI;
            if (isMockTestEnabled()) {
                path += "ccs_org_id=" + ccsOrgId;
            } else {
                path = path.replace("{{ccs_org_id}}", ccsOrgId);
            }
            Response response = RestRequests.deleteAll(path);
            Assert.assertEquals(response.getStatusCode(), OK.getCode(), "Something went wrong while deleting existing organisation!");
            logger.info("Successfully deleted registered organisation.");
        }
        logger.info("Id " + ccsOrgId + "is not registered with CII!!");
    }

    public static Response get(String path) {
        Response res = given().header("x-api-key", apiToken).expect().defaultParser(Parser.JSON).when().get(path);
        logger.info("RestRequests::get() call with status code: " + res.getStatusCode());
        return res;
    }

    private static Response getEndpointWithAccessToken(String path, String accessToken) {
        Response res = given().header("x-api-key", apiToken)
                .header("Authorization", "Bearer " + accessToken)
                .expect().defaultParser(Parser.JSON).when().get(path);
        logger.info("RestRequests::getEndpointWithAccessToken() call with status code: " + res.getStatusCode());
        return res;
    }

    private static Response getEndpointWithNoAccessToken(String path) {
        Response res = given().header("x-api-key", apiToken)
                .header("Content-Type", "application/json")
                .expect().defaultParser(Parser.JSON).when().get(path);
        logger.info("RestRequests::getEndpointWithNoAccessToken() call with status code: " + res.getStatusCode());
        return res;
    }


    public static Response postToCIIAPI(String path, String requestPayload) {
        Response res = given().header("x-api-key", apiToken).header("Content-Type", "application/json")
                .body(requestPayload).when().post(path);
        logger.info("RestRequests::postToCIIAPI() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response postSFInfoToCII(String path) {
        Response res = given().header("x-api-key", migrationToken).header("Content-Type", "application/json")
                .when().post(path);
        logger.info("RestRequests::postSFInfoToCII() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response postSFInfoToCIIMOCK(String path) {
        Response res = given().header("x-api-key", apiToken).header("Content-Type", "application/json")
                .when().post(path);
        logger.info("RestRequests::postSFInfoToCII() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response postToConclaveAPI(String endPoint, Object requestPayload) {
        Response res = given().header("Content-Type", "application/json")
                .body(requestPayload).when().post(conclaveBaseURI + endPoint);
        Assert.assertEquals(res.getStatusCode(), OK.getCode(), "Something went wrong while Conclave post operation!");
        Assert.assertEquals(res.asString().isEmpty(), false, "Something went wrong while Conclave post operation!");
        logger.info("RestRequests::postToConclaveAPI() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response loginToConclaveAPI(String endPoint, Object loginData) {
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

    public static Response put(AdditionalSchemeInfo requestPayload, String accessToken) {
        String path = getBaseURI() + Endpoints.updateSchemeURI;
        Response res;
        if (isMockTestEnabled()) {
            res = given().header("x-api-key", apiToken)
                    .header("Authorization", "Bearer " + accessToken)
                    .param("ccs_org_id", requestPayload.getCcsOrgId())
                    .param("identifier[scheme]", requestPayload.getIdentifier().getScheme())
                    .param("identifier[id]", requestPayload.getIdentifier().getId())
                    .param("clientid", clientId)
                    .when().put(path);
        } else {
            path = path.replace("{{ccs_org_id}}", requestPayload.getCcsOrgId());
            path = path.replace("{{scheme-id}}", requestPayload.getIdentifier().getScheme());
            path = path.replace("{{identifier-id}}", requestPayload.getIdentifier().getId());
            res = given().header("x-api-key", apiToken)
                    .header("Authorization", "Bearer " + accessToken)
                    .when().put(path);
        }
        logger.info("RestRequests::put() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response delete(String path, AdditionalSchemeInfo requestPayload, String accessToken) {
        Response res;
        if (isMockTestEnabled()) {
            res = given().header("x-api-key", apiToken)
                    .header("Authorization", "Bearer " + accessToken)
                    .param("ccs_org_id", requestPayload.getCcsOrgId())
                    .param("identifier[scheme]", requestPayload.getIdentifier().getScheme())
                    .param("identifier[id]", requestPayload.getIdentifier().getId())
                    .param("clientid", clientId)
                    .body(requestPayload).when().delete(path);
        } else {
            path = path.replace("{{ccs_org_id}}", requestPayload.getCcsOrgId());
            path = path.replace("{{scheme-id}}", requestPayload.getIdentifier().getScheme());
            path = path.replace("{{identifier-id}}", requestPayload.getIdentifier().getId());
            res = given().header("x-api-key", apiToken)
                    .header("Authorization", "Bearer " + accessToken)
                    .when().delete(path);
        }
        logger.info("RestRequests::delete() call with status code: " + res.getStatusCode());
        return res;
    }

    public static Response deleteAll(String path) {
        Response res;
        if(isMockTestEnabled())
            res = given().header("x-api-key", apiToken).expect().defaultParser(Parser.JSON).when().delete(path);
        else
            res = given().header("x-api-key", deleteToken).expect().defaultParser(Parser.JSON).when().delete(path);

        logger.info("RestRequests::deleteAll() call with status code: " + res.getStatusCode());
        return res;
    }
}
