package com.ccs.conclave.api.cii.requests;

import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

// Endpoints used in this class are for testing  purpose only and won't be deployed in Production. Therefore the tests
// which are depending on these endpoints cannot be executed in Production.
public class RequestTestEndpoints {
    private final static Logger logger = Logger.getLogger(RequestTestEndpoints.class);
    private static String getCCSOrgId = "/api/v1/testing/search/identities/schemes/organisation?id=";
    private static String deleteOrganisation = "/api/v1/testing/identities/schemes/organisation?org_ccs_id=";

    public static void deleteOrgIdentifiers(String id) {
        String ccsOrgId = getCCSOrgId(id);
        Response response = RestRequests.delete(RestRequests.getBaseURI() + deleteOrganisation + ccsOrgId);
        Assert.assertEquals(response.getStatusCode(), 200, "Something went wrong while deleting existing organisation!");
        logger.info("Successfully deleted existing Organisation.");

    }

    public static String getCCSOrgId(String id) {
        String endpoint = RestRequests.getBaseURI() + getCCSOrgId + id;
        Response response = RestRequests.get(endpoint);
        String ccsOrgId = null;
        if (response.getStatusCode() == 200) {
            String res = response.asString();
            ccsOrgId = StringUtils.substringBetween(res, "\"ccsOrgId\":", ",");
        } else {
            logger.info("No existing registered organisation found, good to perform with POST operation.");
        }
        return ccsOrgId;
    }
}
