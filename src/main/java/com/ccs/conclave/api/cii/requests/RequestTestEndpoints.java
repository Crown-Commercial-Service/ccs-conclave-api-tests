package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.DBData;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.response.GetCIIDBDataTestEndpointResponse;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ccs.conclave.api.common.StatusCodes.*;


// Endpoints used in this class are for testing  purpose only and won't be deployed in Production. Therefore the tests
// which are depending on these endpoints cannot be executed in Production.
public class RequestTestEndpoints {
    private final static Logger logger = Logger.getLogger(RequestTestEndpoints.class);
    private static String getCCSOrgId = "/api/v1/testing/search/identities/schemes/organisation?id=";
    private static String deleteOrganisation = "/api/v1/testing/identities/schemes/organisation?org_ccs_id=";

    public static void deleteOrgIdentifiers(String id) {
        String ccsOrgId = getRegisteredOrgId(id);
        if(!ccsOrgId.isEmpty()) {
            Response response = RestRequests.deleteAll(RestRequests.getBaseURI() + deleteOrganisation + ccsOrgId);
            Assert.assertEquals(response.getStatusCode(), OK.getCode(), "Something went wrong while deleting existing organisation!");
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
        if (response.getStatusCode() == OK.getCode()) {
            dbInfo = new GetCIIDBDataTestEndpointResponse(Arrays.asList(response.getBody().as(DBData[].class)));
        } else {
            logger.info("Given id " + id +" is not registered in CII.");
        }
        return dbInfo;
    }

    public static List<AdditionalSchemeInfo> getAdditionalIdentifiersFromDB(String primaryId) {
        List<AdditionalSchemeInfo> additionalSchemesInfo = new ArrayList<>();
        GetCIIDBDataTestEndpointResponse dbInfo = RequestTestEndpoints.getRegisteredOrganisations(primaryId);
        if(dbInfo.getDbData().size() > 0) {
            for (DBData dbData : dbInfo.getDbData()) {
                if(dbData.getPrimaryScheme().equals("false")) {
                    AdditionalSchemeInfo additionalSchemeInfo = new AdditionalSchemeInfo();
                    Identifier identifier = new Identifier();
                    additionalSchemeInfo.setCcs_org_id(dbData.getCcsOrgId());
                    identifier.setId(dbData.getSchemeOrgRegNumber());
                    identifier.setScheme(dbData.getScheme());
                    additionalSchemeInfo.setIdentifier(identifier);
                    additionalSchemesInfo.add(additionalSchemeInfo);
                }
            }
        }
        return additionalSchemesInfo;
    }

    public static boolean isInCIIDataBase(String id) {
        GetCIIDBDataTestEndpointResponse dbInfo = RequestTestEndpoints.getRegisteredOrganisations(id);
        if(dbInfo.getDbData().size() > 0) {
            for (DBData dbData : dbInfo.getDbData()) {
                if (dbData.getSchemeOrgRegNumber().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }
}

