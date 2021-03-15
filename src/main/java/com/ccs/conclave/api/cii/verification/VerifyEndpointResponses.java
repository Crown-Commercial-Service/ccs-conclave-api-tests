package com.ccs.conclave.api.cii.verification;

import com.ccs.conclave.api.cii.pojo.*;
import com.ccs.conclave.api.cii.requests.RequestTestEndpoints;
import com.ccs.conclave.api.cii.response.*;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.common.StatusCodes.*;

public class VerifyEndpointResponses {
    private final static Logger logger = Logger.getLogger(VerifyEndpointResponses.class);
    private static String ccsOrgId;

    public static void verifyGetSchemeInfoResponse(SchemeInfo expectedSchemeInfo, Response response) {
        GetSchemeInfoResponse actualRes = new GetSchemeInfoResponse();
        verifyResponseCodeForSuccess(response);
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

    public static void verifyGetSchemesResponse(Response response) {
        verifyResponseCodeForSuccess(response);
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

    public static void verifyPostSchemeInfoResponse(SchemeInfo expectedSchemeInfo, Response response) {
        verifyResponseCodeForCreatedResource(response);
        PostSchemeInfoResponse actualResponse = new PostSchemeInfoResponse(Arrays.asList(response.getBody().as(OrgIdentifier[].class)));
        Assert.assertTrue(actualResponse.getOrgIdentifier().size() == 1, "Not expected Post response!");
        ccsOrgId = actualResponse.getOrgIdentifier().get(0).getCcsOrgId();
        Assert.assertTrue(!actualResponse.getOrgIdentifier().get(0).getCcsOrgId().isEmpty()); // CcsOrgId is not empty
        logger.info("CcsOrgId: " + actualResponse.getOrgIdentifier().get(0).getCcsOrgId());

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
                    "Additional identifier/s is not registered for id " + expectedSchemeInfo.getIdentifier().getId());

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
            if (actualAdditionalSchemeInfo.getIdentifier().getId().equals(expectedAdditionalSchemeInfo.getIdentifier().getId())) {
                Assert.assertEquals(actualAdditionalSchemeInfo.getCcsOrgId(), expectedAdditionalSchemeInfo.getCcsOrgId(), "Wrong ccsOrgId in additional identifier!!");
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
            if (actualAdditionalSchemeInfo.getIdentifier().getId().equals(deletedAdditionalSchemeInfo.getIdentifier().getId())) {
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

    public static void verifyResponseCodeForSuccess(Response response) {
        Assert.assertEquals(response.getStatusCode(), OK.getCode(), "Unexpected Status code returned for deleted Resource!!");
    }

    public static void verifyBadRequestResponse(Response response) {
        Assert.assertEquals(response.getStatusCode(), BAD_REQUEST.getCode(), "Unexpected Status code returned for Invalid Scheme!!");
    }

    public static void verifyInvalidIdResponse(Response response) {
        Assert.assertEquals(response.getStatusCode(), NOT_FOUND.getCode(), "Unexpected Status code returned for Invalid Id!!");
    }

    public static String getCCSOrgId() {
        return ccsOrgId;
    }

    public static void verifyManageIdentifiersResponse(Response expectedRes, Response actualRes) throws JSONException {
        // Address, ContactPoint and name are not part of ManageIdentifiers get call response
        String actualResIdentifiers = actualRes.asString().split("address")[0].split("identifier")[1];
        String expectedResIdentifiers = expectedRes.asString().split("address")[0].split("identifier")[1];
        JSONAssert.assertEquals(expectedResIdentifiers, actualResIdentifiers, JSONCompareMode.STRICT);
    }

    public static void verifyRegisteredSchemes(Response actualRes, SchemeInfo expectedSchemeInfo, int selectedAddIds) {
        verifyResponseCodeForSuccess(actualRes);
        GetRegisteredSchemesResponse registeredSchemeInfoRes = new GetRegisteredSchemesResponse(Arrays.asList(actualRes.getBody().as(RegisteredSchemeInfo[].class)));
        RegisteredSchemeInfo actualSchemeInfo = registeredSchemeInfoRes.getRegisteredSchemesInfo().get(0);
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getId(), expectedSchemeInfo.getIdentifier().getId(), "Invalid Id returned via get registered schemes!!");
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getScheme(), expectedSchemeInfo.getIdentifier().getScheme(), "Invalid scheme returned via get registered schemes!!!!");
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getUri(), expectedSchemeInfo.getIdentifier().getUri(), "Invalid uri returned via get registered schemes!!!!");
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getLegalName(), expectedSchemeInfo.getIdentifier().getLegalName(), "Invalid Legal name returned via get registered schemes!!!!");

        Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().size(), selectedAddIds, "Wrong number of additional identifiers!!!!");

        int addIdentifierPresent = 0;
        for (int i = 0; i < selectedAddIds; i++) {
            Identifier expectedAddIdentifier = expectedSchemeInfo.getAdditionalIdentifiers().get(i);
            for (Identifier actualAddIdentifier : actualSchemeInfo.getAdditionalIdentifiers()) {
                if (actualAddIdentifier.getId().equals(expectedAddIdentifier.getId()) &&
                        actualAddIdentifier.getScheme().equals(expectedAddIdentifier.getScheme())) {
                    ++addIdentifierPresent;
                    Assert.assertEquals(actualAddIdentifier.getId(), expectedAddIdentifier.getId(), "Invalid Id for add identifier returned via get registered schemes!!!!");
                    Assert.assertEquals(actualAddIdentifier.getScheme(), expectedAddIdentifier.getScheme(), "Invalid scheme for add identifier returned via get registered schemes!!");
                    Assert.assertEquals(actualAddIdentifier.getUri(), expectedAddIdentifier.getUri(), "Invalid uri for add identifier returned via get registered schemes!!");
                    Assert.assertEquals(actualAddIdentifier.getLegalName(), expectedAddIdentifier.getLegalName(), "Invalid legal name for add identifier returned via get registered schemes!!");
                }
            }
            Assert.assertEquals(addIdentifierPresent, 1, "Additional identifier is not returned as part of Get Registered Schemes!!!!");
            --addIdentifierPresent;
        }
    }

    public static void verifyAllRegisteredSchemes(Response actualRes, SchemeInfo expectedSchemeInfo) {
        verifyResponseCodeForSuccess(actualRes);
        GetRegisteredSchemesResponse registeredSchemeInfoRes = new GetRegisteredSchemesResponse(Arrays.asList(actualRes.getBody().as(RegisteredSchemeInfo[].class)));
        RegisteredSchemeInfo actualSchemeInfo = registeredSchemeInfoRes.getRegisteredSchemesInfo().get(0);
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getId(), expectedSchemeInfo.getIdentifier().getId(), "Invalid Id returned via get registered schemes!!");
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getScheme(), expectedSchemeInfo.getIdentifier().getScheme(), "Invalid scheme returned via get registered schemes!!!!");
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getUri(), expectedSchemeInfo.getIdentifier().getUri(), "Invalid uri returned via get registered schemes!!!!");
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getLegalName(), expectedSchemeInfo.getIdentifier().getLegalName(), "Invalid Legal name returned via get registered schemes!!!!");

        Assert.assertEquals(expectedSchemeInfo.getAdditionalIdentifiers().size(), actualSchemeInfo.getAdditionalIdentifiers().size(), "Wrong number of additional identifiers!!!!");

        // The order of additional identifier may be different in the response
        int addIdentifierPresent = 0;
        for (Identifier expectedAddIdentifier : expectedSchemeInfo.getAdditionalIdentifiers()) {
            for (Identifier actualAddIdentifier : actualSchemeInfo.getAdditionalIdentifiers()) {
                if (actualAddIdentifier.getId().equals(expectedAddIdentifier.getId()) &&
                        actualAddIdentifier.getScheme().equals(expectedAddIdentifier.getScheme())) {
                    ++addIdentifierPresent;
                    Assert.assertEquals(actualAddIdentifier.getId(), expectedAddIdentifier.getId(), "Invalid Id for add identifier returned via get registered schemes!!!!");
                    Assert.assertEquals(actualAddIdentifier.getScheme(), expectedAddIdentifier.getScheme(), "Invalid scheme for add identifier returned via get registered schemes!!");
                    Assert.assertEquals(actualAddIdentifier.getUri(), expectedAddIdentifier.getUri(), "Invalid uri for add identifier returned via get registered schemes!!");
                    Assert.assertEquals(actualAddIdentifier.getLegalName(), expectedAddIdentifier.getLegalName(), "Invalid legal name for add identifier returned via get registered schemes!!");
                    Assert.assertEquals(actualAddIdentifier.getHidden(), expectedAddIdentifier.getHidden(), "Hidden value in add identifier is returned via get registered schemes!!");
                }
            }
            Assert.assertEquals(addIdentifierPresent, 1, "Additional identifier is not returned as part of Get All Registered Schemes!!!!");
            --addIdentifierPresent;
        }
    }
}

