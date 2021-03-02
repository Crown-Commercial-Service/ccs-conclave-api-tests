package com.ccs.conclave.api.cii.requests;

import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.DBData;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.response.GetCIIDBDataTestEndpointResponse;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ccs.conclave.api.common.StatusCodes.*;

// Endpoints used in this class are for testing  purpose only and won't be deployed in Production. Therefore the tests
// which are depending on these endpoints cannot be executed in Production.
public class RequestTestEndpoints {
    private final static Logger logger = Logger.getLogger(RequestTestEndpoints.class);
    private static final String getRegisteredOrgIdsURI = "/api/v1/testing/search/identities/schemes/organisation?id=";

    public static String getRegisteredOrgId(String id) {
        String ccsOrgId = "";
        GetCIIDBDataTestEndpointResponse dbInfo = getRegisteredOrganisations(id);
        if(dbInfo.getDbData().size() > 0) {
            ccsOrgId = getRegisteredOrganisations(id).getDbData().get(0).getCcsOrgId();
        }
        return ccsOrgId;
    }

    public static GetCIIDBDataTestEndpointResponse getRegisteredOrganisations(String id) {
        String endpoint = RestRequests.getBaseURI() + getRegisteredOrgIdsURI + id;
        Response response = RestRequests.get(endpoint);
        GetCIIDBDataTestEndpointResponse dbInfo = new GetCIIDBDataTestEndpointResponse(Arrays.asList());
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
                    additionalSchemeInfo.setCcsOrgId(dbData.getCcsOrgId());
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

