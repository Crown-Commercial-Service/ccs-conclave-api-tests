package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.cii.pojo.*;
import com.ccs.conclave.api.cii.response.ConclaveLoginResponse;
import com.ccs.conclave.api.cii.response.GetCIIDBDataTestEndpointResponse;
import com.ccs.conclave.api.common.Endpoints;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.Arrays;
import static com.ccs.conclave.api.common.StatusCodes.*;

// Endpoints used in this class are for testing  purpose only and won't be deployed in Production. Therefore the tests
// which are depending on these endpoints cannot be executed in Production.
public class RequestTestEndpoints {
    private final static Logger logger = Logger.getLogger(RequestTestEndpoints.class);

    public static String getRegisteredOrgId(String id) {
        String ccsOrgId = "";
        GetCIIDBDataTestEndpointResponse dbInfo = getRegisteredOrganisations(id);
        if(dbInfo.getDbData().size() > 0) {
            ccsOrgId = getRegisteredOrganisations(id).getDbData().get(0).getCcsOrgId();
        }
        return ccsOrgId;
    }

    private static GetCIIDBDataTestEndpointResponse getRegisteredOrganisations(String id) {
        String endpoint = RestRequests.getBaseURI() + Endpoints.getRegisteredOrgIdsURI + id;
        Response response = RestRequests.get(endpoint);
        GetCIIDBDataTestEndpointResponse dbInfo = new GetCIIDBDataTestEndpointResponse(Arrays.asList());
        if (response.getStatusCode() == OK.getCode()) {
            dbInfo = new GetCIIDBDataTestEndpointResponse(Arrays.asList(response.getBody().as(DBData[].class)));
        } else {
            logger.info("Given id " + id +" is not registered in CII.");
        }
        return dbInfo;
    }

    public static String getAccessToken(String ccsOrgId) {
        return registerOrgAndUser(ccsOrgId);
    }

    private static String registerOrgAndUser(String orgId) {
        String userEmail = getUserEmail();
        String password = "Letmein1234$";

        String conclaveOrgId = postOrgDataForOrgCreation(orgId);
        postUserDataForUserCreation(conclaveOrgId, userEmail);
        signupToAuth0(userEmail, password);
        return loginToConclave(userEmail, password);
    }

    private static String getUserEmail() {
        return "test" + RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@yopmail.com";
    }

    public static String postOrgDataForOrgCreation(String orgId) {
        OrgData orgData = new OrgData();
        orgData.setCiiOrganisationId(orgId);
        Response response = RestRequests.postToConclaveAPI(Endpoints.orgCreationURI, orgData);
        return response.asString();
    }

    public static void postUserDataForUserCreation(String conclaveOrgId, String userEmail) {
        UserData userData = new UserData();
        userData.setOrganisationId(conclaveOrgId);
        userData.setUserName(userEmail);
        RestRequests.postToConclaveAPI(Endpoints.userCreationURI, userData);
    }

    public static void signupToAuth0(String userEmail, String password) {
        SignupData signupData = new SignupData();
        signupData.setEmail(userEmail);
        signupData.setPassword(password);
        RestRequests.postToAuth0(Endpoints.auth0SignupURI, signupData);
    }

    public static String loginToConclave(String userEmail, String password) {
        LoginData loginData = new LoginData();
        loginData.setUsername(userEmail);
        loginData.setPassword(password);
        loginData.setClient_id(RestRequests.getClientId());
        loginData.setClient_secret(RestRequests.getClientSecret());
        Response response = RestRequests.loginToConclaveAPI(Endpoints.loginURI, loginData);

        ConclaveLoginResponse loginResponse = new ConclaveLoginResponse(response.getBody().as(LoginDetails.class));
        String accessToken = loginResponse.getLoginDetails().getAccessToken();
        Assert.assertEquals(accessToken.isEmpty(), false, "Invalid accessToken in login response");
        return accessToken;
    }

//    public static List<AdditionalSchemeInfo> getAdditionalIdentifiersFromDB(String primaryId) {
//        List<AdditionalSchemeInfo> additionalSchemesInfo = new ArrayList<>();
//        GetCIIDBDataTestEndpointResponse dbInfo = RequestTestEndpoints.getRegisteredOrganisations(primaryId);
//        if(dbInfo.getDbData().size() > 0) {
//            for (DBData dbData : dbInfo.getDbData()) {
//                if(dbData.getPrimaryScheme().equals("false")) {
//                    AdditionalSchemeInfo additionalSchemeInfo = new AdditionalSchemeInfo();
//                    Identifier identifier = new Identifier();
//                    additionalSchemeInfo.setCcsOrgId(dbData.getCcsOrgId());
//                    identifier.setId(dbData.getSchemeOrgRegNumber());
//                    identifier.setScheme(dbData.getScheme());
//                    additionalSchemeInfo.setIdentifier(identifier);
//                    additionalSchemesInfo.add(additionalSchemeInfo);
//                }
//            }
//        }
//        return additionalSchemesInfo;
//    }
//
//    public static boolean isInCIIDataBase(String id) {
//        GetCIIDBDataTestEndpointResponse dbInfo = RequestTestEndpoints.getRegisteredOrganisations(id);
//        if(dbInfo.getDbData().size() > 0) {
//            for (DBData dbData : dbInfo.getDbData()) {
//                if (dbData.getSchemeOrgRegNumber().equals(id)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}

