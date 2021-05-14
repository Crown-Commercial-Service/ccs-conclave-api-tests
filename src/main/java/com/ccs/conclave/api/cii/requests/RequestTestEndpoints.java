package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.cii.pojo.DBData;
import com.ccs.conclave.api.cii.pojo.OrgData;
import com.ccs.conclave.api.cii.pojo.SignupData;
import com.ccs.conclave.api.cii.pojo.UserData;
import com.ccs.conclave.api.cii.response.GetCIIDBDataTestEndpointResponse;
import com.ccs.conclave.api.common.Endpoints;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
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
        String endpoint = RestRequests.getCiiBaseURI() + Endpoints.getRegisteredOrgIdsURI + id;
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
        OrgData orgData = new OrgData();
        orgData.setCiiOrganisationId(orgId);
        String conclaveOrgId = postOrgDataForOrgCreation(orgData);

        UserData userData = new UserData();
        userData.setOrganisationId(conclaveOrgId);
        String userEmail = getUserEmail();
        userData.setUserName(userEmail);
        postUserDataForUserCreation(userData);

        SignupData signupData = new SignupData();
        signupData.setEmail(userEmail);
        signupData.setPassword("Letmein1234$");
        // Todo: Fix Auth0 post call
        // signupToAuth0(signupData);

        //Todo - Login Endpoint
        return null;
    }

    private static String getUserEmail() {
        return "api-test" + RandomStringUtils.randomAlphabetic(5) + "@void.com";
    }

    public static String postOrgDataForOrgCreation(OrgData orgData) {
        Response response = RestRequests.postToConclaveAPI(Endpoints.orgCreationURI, orgData);
        return response.asString();
    }

    public static String postUserDataForUserCreation(UserData userData) {
        Response response = RestRequests.postToConclaveAPI(Endpoints.userCreationURI, userData);
        return response.asString();
    }

    public static String signupToAuth0(SignupData signupData) {
        Response response = RestRequests.postToAuth0(Endpoints.auth0SignupURI, signupData);
        return response.asString();
    }

//    public static String login() {
//
//    }

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

