package com.ccs.conclave.api.cii.verification;

import com.ccs.conclave.api.cii.pojo.*;
import com.ccs.conclave.api.cii.requests.RequestTestEndpoints;
import com.ccs.conclave.api.cii.response.GetCIIDBDataTestEndpointResponse;
import com.ccs.conclave.api.cii.response.GetSchemeInfoResponse;
import com.ccs.conclave.api.cii.response.PostSchemeInfoResponse;
import com.ccs.conclave.api.cii.response.GetSchemesResponse;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.common.StatusCodes.*;

public class VerifyResponses {
    private final static Logger logger = Logger.getLogger(VerifyResponses.class);
    private static String ccsOrgId;

    public static void verifyGetSchemeInfoResponse(SchemeInfo expectedSchemeInfo, Response response) {
        GetSchemeInfoResponse actualRes = new GetSchemeInfoResponse();
        verifyStatusCode(response, OK.getCode());
        actualRes.setSchemeInfo(response.as(SchemeInfo.class));
        verifySchemeInfo(actualRes.getSchemeInfo(), expectedSchemeInfo);
    }

    private static void verifySchemeInfo(SchemeInfo actualSchemeInfo, SchemeInfo expectedSchemeInfo) {
        logger.info("SchemeInfo:Name " + actualSchemeInfo.getName());
        Assert.assertEquals(actualSchemeInfo.getName(), expectedSchemeInfo.getName(), "Wrong SchemeInfo:Name in response!");

        logger.info("SchemeInfo:Scheme " + actualSchemeInfo.getIdentifier().getScheme());
        logger.info("SchemeInfo:Id " + actualSchemeInfo.getIdentifier().getId());
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getScheme(), expectedSchemeInfo.getIdentifier().getScheme(), "Wrong Identifier:scheme in response!");
        // Bug:CON-488 Fixed
        Assert.assertTrue(actualSchemeInfo.getIdentifier().getId().contains(expectedSchemeInfo.getIdentifier().getId()), "Wrong Identifier:id in response!");
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getLegalName(), expectedSchemeInfo.getIdentifier().getLegalName(), "Wrong Identifier:legalName in response!");
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getUri(), expectedSchemeInfo.getIdentifier().getUri(), "Wrong Identifier:url in response!");

        Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().size(), expectedSchemeInfo.getAdditionalIdentifiers().size(), "AdditionalIdentifier array size is invalid!");
        for (int i = 0; i < expectedSchemeInfo.getAdditionalIdentifiers().size(); i++) {
            logger.info("Additional Identifier :ID " + actualSchemeInfo.getAdditionalIdentifiers().get(0).getId());
            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(i).getId(), expectedSchemeInfo.getAdditionalIdentifiers().get(i).getId(), "Wrong Additional Identifier:id in response!");
            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(i).getScheme(), expectedSchemeInfo.getAdditionalIdentifiers().get(i).getScheme(), "Wrong Additional Identifier:scheme in response!");
            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(i).getLegalName(), expectedSchemeInfo.getAdditionalIdentifiers().get(i).getLegalName(), "Wrong Additional Identifier:legal name in response!");
            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(i).getUri(), expectedSchemeInfo.getAdditionalIdentifiers().get(i).getUri(), "Wrong Additional Identifier:Uri in response!");
        }

        logger.info("Address:StreetAddress " + actualSchemeInfo.getAddress().getStreetAddress());
        Assert.assertEquals(actualSchemeInfo.getAddress().getStreetAddress(), expectedSchemeInfo.getAddress().getStreetAddress(), "Wrong address:streetAddress in response!");
        Assert.assertEquals(actualSchemeInfo.getAddress().getLocality(), expectedSchemeInfo.getAddress().getLocality(), "Wrong address:locality in response!");
        logger.info("Address:region " + actualSchemeInfo.getAddress().getRegion());
        Assert.assertEquals(actualSchemeInfo.getAddress().getRegion(), expectedSchemeInfo.getAddress().getRegion(), "Wrong address:region in response!");
        Assert.assertEquals(actualSchemeInfo.getAddress().getPostalCode(), expectedSchemeInfo.getAddress().getPostalCode(), "Wrong address:postalCode in response!");
        Assert.assertEquals(actualSchemeInfo.getAddress().getCountryName(), expectedSchemeInfo.getAddress().getCountryName(), "Wrong address:countryName in response!");

        verifyContactPoint(expectedSchemeInfo, actualSchemeInfo);
    }

    // Verify Contact point is not empty if expected
    private static void verifyContactPoint(SchemeInfo expectedSchemeInfo, SchemeInfo actualSchemeInfo) {
        if (!expectedSchemeInfo.getContactPoint().getName().isEmpty())
            Assert.assertTrue(!(actualSchemeInfo.getContactPoint().getName().isEmpty()), "Wrong contactPoint:name in response!");
        else
            Assert.assertTrue((actualSchemeInfo.getContactPoint().getName().isEmpty()), "Wrong contactPoint:name in response!");

        if (!expectedSchemeInfo.getContactPoint().getEmail().isEmpty())
            Assert.assertTrue(!(actualSchemeInfo.getContactPoint().getEmail().isEmpty()), "Wrong contactPoint:email in response!");
        else
            Assert.assertTrue((actualSchemeInfo.getContactPoint().getEmail().isEmpty()), "Wrong contactPoint:email in response!");

        if (!expectedSchemeInfo.getContactPoint().getFaxNumber().isEmpty())
            Assert.assertTrue(!(actualSchemeInfo.getContactPoint().getFaxNumber().isEmpty()), "Wrong contactPoint:faxNumber in response!");
        else
            Assert.assertTrue((actualSchemeInfo.getContactPoint().getFaxNumber().isEmpty()), "Wrong contactPoint:FaxNumber in response!");

        if (!expectedSchemeInfo.getContactPoint().getTelephone().isEmpty())
            Assert.assertTrue(!(actualSchemeInfo.getContactPoint().getTelephone().isEmpty()), "Wrong contactPoint:telephone in response!");
        else
            Assert.assertTrue((actualSchemeInfo.getContactPoint().getTelephone().isEmpty()), "Wrong contactPoint:telephone in response!");

        if (!expectedSchemeInfo.getContactPoint().getUri().isEmpty())
            Assert.assertTrue(!(actualSchemeInfo.getContactPoint().getUri().isEmpty()), "Wrong contactPoint:uri in response!");
        else
            Assert.assertTrue((actualSchemeInfo.getContactPoint().getUri().isEmpty()), "Wrong contactPoint:uri in response!");
    }

    public static void verifyGetSchemesResponse(Response response) throws IOException {
        verifyStatusCode(response, OK.getCode());
        logger.info("GetSchemesResponse" + response.asString());

        GetSchemesResponse schemesResponse = new GetSchemesResponse(Arrays.asList(response.getBody().as(Scheme[].class)));
        Assert.assertEquals(schemesResponse.getSchemes().size(), 5, "Wrong number of schemes are returned!");

        Scheme scheme = schemesResponse.getSchemes().get(0);
        Assert.assertEquals(scheme.getScheme(), getSchemeCode(COMPANIES_HOUSE), "Invalid SchemeCode!");
        Assert.assertEquals(scheme.getSchemeName(), getSchemeName(COMPANIES_HOUSE), "Invalid SchemeName!");
        Assert.assertEquals(scheme.getSchemeCountryCode(), getSchemeCountryCode(COMPANIES_HOUSE), "Invalid CountryCode!");

        scheme = schemesResponse.getSchemes().get(1);
        Assert.assertEquals(scheme.getScheme(), getSchemeCode(DUN_AND_BRADSTREET), "Invalid SchemeCode!");
        Assert.assertEquals(scheme.getSchemeName(), getSchemeName(DUN_AND_BRADSTREET), "Invalid SchemeName!");
        Assert.assertEquals(scheme.getSchemeCountryCode(), getSchemeCountryCode(DUN_AND_BRADSTREET), "Invalid CountryCode!");

        scheme = schemesResponse.getSchemes().get(2);
        Assert.assertEquals(scheme.getScheme(), getSchemeCode(CHARITIES_COMMISSION), "Invalid SchemeCode!");
        Assert.assertEquals(scheme.getSchemeName(), getSchemeName(CHARITIES_COMMISSION), "Invalid SchemeName!");
        Assert.assertEquals(scheme.getSchemeCountryCode(), getSchemeCountryCode(CHARITIES_COMMISSION), "Invalid CountryCode!");

        scheme = schemesResponse.getSchemes().get(3);
        Assert.assertEquals(scheme.getScheme(), getSchemeCode(SCOTLAND_CHARITY), "Invalid SchemeCode!");
        Assert.assertEquals(scheme.getSchemeName(), getSchemeName(SCOTLAND_CHARITY), "Invalid SchemeName!");
        Assert.assertEquals(scheme.getSchemeCountryCode(), getSchemeCountryCode(SCOTLAND_CHARITY), "Invalid CountryCode!");

        scheme = schemesResponse.getSchemes().get(4);
        Assert.assertEquals(scheme.getScheme(), getSchemeCode(NORTHERN_CHARITY), "Invalid SchemeCode!");
        Assert.assertEquals(scheme.getSchemeName(), getSchemeName(NORTHERN_CHARITY), "Invalid SchemeName!");
        Assert.assertEquals(scheme.getSchemeCountryCode(), getSchemeCountryCode(NORTHERN_CHARITY), "Invalid CountryCode!");
    }

    private static void verifyStatusCode(Response response, int code) {
        Assert.assertEquals(response.getStatusCode(), code, "Unexpected Status code returned!!");
    }

    public static void verifyPostSchemeInfoResponse(SchemeInfo expectedSchemeInfo, Response response) {
        verifyResponseCodeForCreatedResource(response);
        PostSchemeInfoResponse actualResponse = new PostSchemeInfoResponse(Arrays.asList(response.getBody().as(OrgIdentifier[].class)));
        Assert.assertTrue(actualResponse.getOrgIdentifier().size() == 1, "Not expected Post response!");
        ccsOrgId = actualResponse.getOrgIdentifier().get(0).getCcsOrgId();
        Assert.assertTrue(!actualResponse.getOrgIdentifier().get(0).getCcsOrgId().isEmpty()); // CcsOrgId is not empty
        logger.info("CcsOrgId: " + actualResponse.getOrgIdentifier());

        GetCIIDBDataTestEndpointResponse dbInfo = RequestTestEndpoints.getRegisteredOrganisations(expectedSchemeInfo.getIdentifier().getId());
        Assert.assertTrue(dbInfo.getDbData().size() >= 1, "No CII Database entry for the org registration for id :" + expectedSchemeInfo.getIdentifier().getId());

        // verify ccsOrgId
        Assert.assertEquals(dbInfo.getDbData().get(0).getCcsOrgId(), actualResponse.getOrgIdentifier().get(0).getCcsOrgId(), "Registered OrgId is wrong!");

        // verify scheme, id and isPrimaryScheme
        Assert.assertEquals(dbInfo.getDbData().get(0).getSchemeOrgRegNumber(), expectedSchemeInfo.getIdentifier().getId(), "Registered Id is wrong!");
        Assert.assertEquals(dbInfo.getDbData().get(0).getScheme(), expectedSchemeInfo.getIdentifier().getScheme(), "Registered Scheme is wrong!");
        Assert.assertEquals(dbInfo.getDbData().get(0).getPrimaryScheme(), "true", "IsPrimary flag is wrong!");

        if (expectedSchemeInfo.getAdditionalIdentifiers().size() > 0) {
            Assert.assertEquals(dbInfo.getDbData().size() - 1, expectedSchemeInfo.getAdditionalIdentifiers().size(),
                    "Additional identifier/s is not registered for id" + expectedSchemeInfo.getIdentifier().getId());

            for (int i = 1; i < dbInfo.getDbData().size(); i++) {
                // verify ccsOrgId
                Assert.assertEquals(dbInfo.getDbData().get(i).getCcsOrgId(), actualResponse.getOrgIdentifier().get(0).getCcsOrgId(), "Registered OrgId is wrong!");

                // verify scheme, id and isPrimaryScheme for additional identifiers
                Assert.assertEquals(dbInfo.getDbData().get(i).getSchemeOrgRegNumber(), expectedSchemeInfo.getAdditionalIdentifiers().get(i - 1).getId(), "Registered Id is wrong!");
                Assert.assertEquals(dbInfo.getDbData().get(i).getScheme(), expectedSchemeInfo.getAdditionalIdentifiers().get(i - 1).getScheme(), "Registered Scheme is wrong!");
                Assert.assertEquals(dbInfo.getDbData().get(i).getPrimaryScheme(), "false", "IsPrimary flag is wrong!");
            }
        }
    }

    public static void verifyUpdatedScheme(String primaryId, AdditionalSchemeInfo expectedAdditionalSchemeInfo) {
        Assert.assertTrue(isIdentifierRegisteredAlready(expectedAdditionalSchemeInfo.getIdentifier().getId()), "Expected additional identifier is not in CII DB!!");

        // Get additional identifiers using the Primary Identifier from CII Database
        List<AdditionalSchemeInfo> actualAdditionalSchemesInfo = RequestTestEndpoints.getAdditionalIdentifiersFromDB(primaryId);
        Assert.assertTrue(actualAdditionalSchemesInfo.size() > 0, "Expected additional identifier as part of " + primaryId + "is not in CII DB!!");

        for (AdditionalSchemeInfo actualAdditionalSchemeInfo : actualAdditionalSchemesInfo) {
            if (actualAdditionalSchemeInfo.getIdentifier().getId() == expectedAdditionalSchemeInfo.getIdentifier().getId()) {
                Assert.assertEquals(actualAdditionalSchemeInfo.getCcs_org_id(), expectedAdditionalSchemeInfo.getCcs_org_id(), "Wrong ccsOrgId in additional identifier!!");
                Assert.assertEquals(actualAdditionalSchemeInfo.getIdentifier().getScheme(), expectedAdditionalSchemeInfo.getIdentifier().getScheme(), "Wrong scheme in additional identifier!!");
            }
        }
    }

    public static boolean isIdentifierRegisteredAlready(String id) {
        return RequestTestEndpoints.isInCIIDataBase(id);
    }


    public static void verifyDeletedScheme(String identifier, AdditionalSchemeInfo deletedAdditionalSchemeInfo) {
        List<AdditionalSchemeInfo> actualAdditionalSchemesInfo = RequestTestEndpoints.getAdditionalIdentifiersFromDB(identifier);
        Assert.assertTrue(!RequestTestEndpoints.isInCIIDataBase(deletedAdditionalSchemeInfo.getIdentifier().getId()), "Deleted additional identifier is in CII DB!!");

        // verify deleted identifier exists in actualAdditionalSchemesInfo
        for (AdditionalSchemeInfo actualAdditionalSchemeInfo : actualAdditionalSchemesInfo) {
            if (actualAdditionalSchemeInfo.getIdentifier().getId() == deletedAdditionalSchemeInfo.getIdentifier().getId()) {
                Assert.fail("Deleted Scheme call failed!!");
            }
        }
    }

    public static void verifyResponseCodeForCreatedResource(Response response) {
        Assert.assertEquals(response.getStatusCode(), CREATED.getCode(), "Unexpected Status code returned for created!!");
    }

    public static void verifyResponseCodeForDuplicateResource(Response response) {
        Assert.assertEquals(response.getStatusCode(), DUPLICATE_RESOURCE.getCode(), "Unexpected Status code returned for Duplicate Resource!!");
    }

    public static void verifyResponseCodeForUpdatedOrDeletedResource(Response response) {
        Assert.assertEquals(response.getStatusCode(), OK.getCode(), "Unexpected Status code returned for deleted Resource!!");
    }

    public static void verifyInvalidSchemeResponse(Response response) {
        Assert.assertEquals(response.getStatusCode(), BAD_REQUEST.getCode(), "Unexpected Status code returned for Invalid Scheme!!");
    }

    public static void verifyInvalidIdResponse(Response response) {
        Assert.assertEquals(response.getStatusCode(), NOT_FOUND.getCode(), "Unexpected Status code returned for Invalid Id!!");
    }

    public static String getCCSOrgId() {
        return ccsOrgId;
    }
}

