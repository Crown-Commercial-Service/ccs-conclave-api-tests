package com.ccs.conclave.api.cii.verification;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;

import com.ccs.conclave.api.cii.pojo.Scheme;
import com.ccs.conclave.api.cii.response.GetSchemeInfoResponse;
import com.ccs.conclave.api.cii.response.PostSchemeInfoResponse;
import com.ccs.conclave.api.cii.response.GetSchemesResponse;
import com.ccs.conclave.api.cii.data.SchemeRegistry;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;


public class VerifyResponses {
    private final static Logger logger = Logger.getLogger(VerifyResponses.class);

    public static void verifyGetSchemeInfoResponse(SchemeRegistry scheme, SchemeInfo schemeInfo, Response response) {
        verifyStatusCode(response, 200);

        GetSchemeInfoResponse actualRes = response.as(GetSchemeInfoResponse.class);
        Assert.assertEquals(actualRes.getSchemeInfo(), schemeInfo, "Wrong GetSchemeInfo response!" );
    }

    public static void verifyInvalidGetSchemeInfoResponse(Response response) {

    }

    public static void verifyGetSchemesResponse(Response response) throws IOException {
        verifyStatusCode(response, 200);

        GetSchemesResponse schemesResponse = new GetSchemesResponse(Arrays.asList(response.getBody().as(Scheme[].class)));
        Assert.assertEquals(schemesResponse.getSchemes().size(), 5, "Wrong number of schemes are returned!");

        Scheme scheme = schemesResponse.getSchemes().get(0);
        Assert.assertEquals(scheme.getSchemeRegisterCode(), getSchemeCode(COMPANIES_HOUSE), "Invalid SchemeCode!");
        Assert.assertEquals(scheme.getSchemeName(), getSchemeName(COMPANIES_HOUSE), "Invalid SchemeName!");
        Assert.assertEquals(scheme.getSchemeUri(), getSchemeURL(COMPANIES_HOUSE), "Invalid SchemeURL!");
        Assert.assertEquals(scheme.getSchemeIdentifier(), getSchemeIdentifier(COMPANIES_HOUSE), "Invalid SchemeIdentifier!");
        Assert.assertEquals(scheme.getSchemeCountryCode(), getSchemeCountryCode(COMPANIES_HOUSE), "Invalid CountryCode!");

        scheme = schemesResponse.getSchemes().get(1);
        Assert.assertEquals(scheme.getSchemeRegisterCode(), getSchemeCode(DUN_AND_BRADSTREET), "Invalid SchemeCode!");
        Assert.assertEquals(scheme.getSchemeName(), getSchemeName(DUN_AND_BRADSTREET), "Invalid SchemeName!");
        Assert.assertEquals(scheme.getSchemeUri(), getSchemeURL(DUN_AND_BRADSTREET), "InvalidWrong SchemeURL!");
        Assert.assertEquals(scheme.getSchemeIdentifier(), getSchemeIdentifier(DUN_AND_BRADSTREET), "Invalid SchemeIdentifier!");
        Assert.assertEquals(scheme.getSchemeCountryCode(), getSchemeCountryCode(DUN_AND_BRADSTREET), "Invalid CountryCode!");

        scheme = schemesResponse.getSchemes().get(2);
        Assert.assertEquals(scheme.getSchemeRegisterCode(), getSchemeCode(CHARITIES_COMMISSION), "Invalid SchemeCode!");
        Assert.assertEquals(scheme.getSchemeName(), getSchemeName(CHARITIES_COMMISSION), "Invalid SchemeName!");
        Assert.assertEquals(scheme.getSchemeUri(), getSchemeURL(CHARITIES_COMMISSION), "Invalid SchemeURL!");
        Assert.assertEquals(scheme.getSchemeIdentifier(), getSchemeIdentifier(CHARITIES_COMMISSION), "Invalid SchemeIdentifier!");
        Assert.assertEquals(scheme.getSchemeCountryCode(), getSchemeCountryCode(CHARITIES_COMMISSION), "Invalid CountryCode!");

        scheme = schemesResponse.getSchemes().get(3);
        Assert.assertEquals(scheme.getSchemeRegisterCode(), getSchemeCode(SCOTLAND_CHARITY), "Invalid SchemeCode!");
        Assert.assertEquals(scheme.getSchemeName(), getSchemeName(SCOTLAND_CHARITY), "Invalid SchemeName!");
        Assert.assertEquals(scheme.getSchemeUri(), getSchemeURL(SCOTLAND_CHARITY), "Invalid SchemeURL!");
        Assert.assertEquals(scheme.getSchemeIdentifier(), getSchemeIdentifier(SCOTLAND_CHARITY), "Invalid SchemeIdentifier!");
        Assert.assertEquals(scheme.getSchemeCountryCode(), getSchemeCountryCode(SCOTLAND_CHARITY), "Invalid CountryCode!");

        scheme = schemesResponse.getSchemes().get(4);
        Assert.assertEquals(scheme.getSchemeRegisterCode(), getSchemeCode(NORTHERN_ISLAND_CHARITY), "Invalid SchemeCode!");
        Assert.assertEquals(scheme.getSchemeName(), getSchemeName(NORTHERN_ISLAND_CHARITY), "Invalid SchemeName!");
        Assert.assertEquals(scheme.getSchemeUri(), getSchemeURL(NORTHERN_ISLAND_CHARITY), "Invalid SchemeURL!");
        Assert.assertEquals(scheme.getSchemeIdentifier(), getSchemeIdentifier(NORTHERN_ISLAND_CHARITY), "Invalid SchemeIdentifier!");
        Assert.assertEquals(scheme.getSchemeCountryCode(), getSchemeCountryCode(NORTHERN_ISLAND_CHARITY), "Invalid CountryCode!");
    }

    private static void verifyStatusCode(Response response, int expectedCode) {
        Assert.assertEquals(response.getStatusCode(), expectedCode, "Unexpected Status code returned!!");
    }

    public static void verifyPostSchemeInfoResponse(SchemeRegistry scheme, OrgDataProvider expectedRes, PostSchemeInfoResponse actualRes) {
        Assert.assertEquals(actualRes.getSchemeInfo(), expectedRes.getInfo(scheme), "Wrong PostSchemeInfo response!" );
    }
}
