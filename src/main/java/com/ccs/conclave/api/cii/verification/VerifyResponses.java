package com.ccs.conclave.api.cii.verification;

import com.ccs.conclave.api.cii.pojo.OrgIdentifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;

import com.ccs.conclave.api.cii.pojo.Scheme;
import com.ccs.conclave.api.cii.requests.RequestTestEndpoints;
import com.ccs.conclave.api.cii.response.GetSchemeInfoResponse;
import com.ccs.conclave.api.cii.response.PostSchemeInfoResponse;
import com.ccs.conclave.api.cii.response.GetSchemesResponse;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;

public class VerifyResponses {
    private final static Logger logger = Logger.getLogger(VerifyResponses.class);


    public static void verifyGetSchemeInfoResponse(SchemeInfo expectedSchemeInfo, Response response) {
        GetSchemeInfoResponse actualRes = new GetSchemeInfoResponse();
        verifyStatusCode(response, 200);
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
        // Bug CON-439: Assert.assertEquals(actualSchemeInfo.getAddress().getRegion(), expectedSchemeInfo.getAddress().getRegion(), "Wrong address:region in response!" );
        Assert.assertEquals(actualSchemeInfo.getAddress().getPostalCode(), expectedSchemeInfo.getAddress().getPostalCode(), "Wrong address:postalCode in response!");
        Assert.assertEquals(actualSchemeInfo.getAddress().getCountryName(), expectedSchemeInfo.getAddress().getCountryName(), "Wrong address:countryName in response!");

        Assert.assertEquals(actualSchemeInfo.getContactPoint().getName(), expectedSchemeInfo.getContactPoint().getName(), "Wrong contactPoint:name in response!");
        Assert.assertEquals(actualSchemeInfo.getContactPoint().getEmail(), expectedSchemeInfo.getContactPoint().getEmail(), "Wrong contactPoint:email in response!");
        Assert.assertEquals(actualSchemeInfo.getContactPoint().getFaxNumber(), expectedSchemeInfo.getContactPoint().getFaxNumber(), "Wrong contactPoint:telephone in response!");
        Assert.assertEquals(actualSchemeInfo.getContactPoint().getTelephone(), expectedSchemeInfo.getContactPoint().getTelephone(), "Wrong contactPoint:faxNumber in response!");
        Assert.assertEquals(actualSchemeInfo.getContactPoint().getUri(), expectedSchemeInfo.getContactPoint().getUri(), "Wrong contactPoint:uri in response!");
    }

    public static void verifyInvalidGetSchemeResponse(int errorCode, Response response) {
        verifyStatusCode(response, errorCode);
        switch (errorCode) {
            case 400:
                Assert.assertEquals(response.getBody().asString(), "{\"scheme\":[\"can't be blank\",\"No such scheme registered\"]}", "Wrong contactPoint:url in response!");
                break;

            case 401:
                Assert.assertEquals(response.getBody().asString(), "{}", "Wrong contactPoint:url in response!");
                break;
        }
    }

    public static void verifyGetSchemesResponse(Response response) throws IOException {
        verifyStatusCode(response, 200);
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

    private static void verifyStatusCode(Response response, int expectedCode) {
        Assert.assertEquals(response.getStatusCode(), expectedCode, "Unexpected Status code returned!!");
    }

    public static void verifyPostSchemeInfoResponse(SchemeInfo actualSchemeInfo, Response response) {
        PostSchemeInfoResponse actualResponse = new PostSchemeInfoResponse(Arrays.asList(response.getBody().as(OrgIdentifier[].class)));
        Assert.assertTrue(actualResponse.getOrgIdentifiers().size() == 1, "Not expected Post response!");
        Assert.assertTrue(!actualResponse.getOrgIdentifiers().get(0).getCcsOrgId().isEmpty()); // CcsOrgId is not empty
        logger.info("CcsOrgId: " + actualResponse.getOrgIdentifiers());

        String dbCCSOrgId = RequestTestEndpoints.getCCSOrgId(actualSchemeInfo.getIdentifier().getId());
        Assert.assertEquals(actualResponse.getOrgIdentifiers().get(0).getCcsOrgId(), dbCCSOrgId, "ccsOrgId is different, something wrong with registering organisation!");
    }


    public static void verifyDuplicateResourceResponse(int statusCode, Response response) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Unexpected Status code returned for Duplicate Entries!!");
    }
}
