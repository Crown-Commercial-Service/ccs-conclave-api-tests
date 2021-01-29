package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.cii.pojo.DBData;
import com.ccs.conclave.api.cii.response.GetCIIDBDataTestEndpointResponse;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.Arrays;

// Endpoints used in this class are for testing  purpose only and won't be deployed in Production. Therefore the tests
// which are depending on these endpoints cannot be executed in Production.
public class RequestTestEndpoints {
    private final static Logger logger = Logger.getLogger(RequestTestEndpoints.class);
    private static String getCCSOrgId = "/api/v1/testing/search/identities/schemes/organisation?id=";
    private static String deleteOrganisation = "/api/v1/testing/identities/schemes/organisation?org_ccs_id=";

    public static void deleteOrgIdentifiers(String id) {
        String ccsOrgId = getRegisteredOrgId(id);
        if(!ccsOrgId.isEmpty()) {
            Response response = RestRequests.delete(RestRequests.getBaseURI() + deleteOrganisation + ccsOrgId);
            Assert.assertEquals(response.getStatusCode(), 200, "Something went wrong while deleting existing organisation!");
            logger.info("Successfully deleted if Organisation already exists.");
        }
    }

    private static String getRegisteredOrgId(String id) {
        String ccsOrgId = "";
        if(getRegisteredOrganisations(id) != null) {
            ccsOrgId = getRegisteredOrganisations(id).getDbData().get(0).getCcsOrgId();
        }
        return ccsOrgId;
    }

    public static GetCIIDBDataTestEndpointResponse getRegisteredOrganisations(String id) {
        String endpoint = RestRequests.getBaseURI() + getCCSOrgId + id;
        Response response = RestRequests.get(endpoint);
        GetCIIDBDataTestEndpointResponse dbInfo = null;
        if (response.getStatusCode() == 200) {
            dbInfo = new GetCIIDBDataTestEndpointResponse(Arrays.asList(response.getBody().as(DBData[].class)));
        } else {
            logger.info("Given id " + id +" is not registered in CII.");
        }
        return dbInfo;
    }
}
