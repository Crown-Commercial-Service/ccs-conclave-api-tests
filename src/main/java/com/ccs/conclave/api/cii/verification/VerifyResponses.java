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
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getId(), expectedSchemeInfo.getIdentifier().getId(), "Wrong Identifier:id in response!");
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getLegalName(), expectedSchemeInfo.getIdentifier().getLegalName(), "Wrong Identifier:legalName in response!");
        Assert.assertEquals(actualSchemeInfo.getIdentifier().getUri(), expectedSchemeInfo.getIdentifier().getUri(), "Wrong Identifier:url in response!");

        Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().size(), expectedSchemeInfo.getAdditionalIdentifiers().size(), "AdditionalIdentifier array size is invalid!");
        if(actualSchemeInfo.getAdditionalIdentifiers().size() == 1){
            logger.info("Additional Identifier :ID " + actualSchemeInfo.getAdditionalIdentifiers().get(0).getId());
            Assert.assertTrue(actualSchemeInfo.getAdditionalIdentifiers().size() > 0);
            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(0).getId(), expectedSchemeInfo.getAdditionalIdentifiers().get(0).getId(), "Wrong Additional Identifier:id in response!");
            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(0).getScheme(), expectedSchemeInfo.getAdditionalIdentifiers().get(0).getScheme(), "Wrong Additional Identifier:scheme in response!");
            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(0).getLegalName(), expectedSchemeInfo.getAdditionalIdentifiers().get(0).getLegalName(), "Wrong Additional Identifier:legal name in response!");
            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(0).getUri(), expectedSchemeInfo.getAdditionalIdentifiers().get(0).getUri(), "Wrong Additional Identifier:Uri in response!");
        }
        //Defect CON-470
//        else if(actualSchemeInfo.getAdditionalIdentifiers().size() == 2){
//            logger.info("Additional Identifier :ID " + actualSchemeInfo.getAdditionalIdentifiers().get(0).getId());
//            Assert.assertTrue(actualSchemeInfo.getAdditionalIdentifiers().size() > 0);
//            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(1).getId(), expectedSchemeInfo.getAdditionalIdentifiers().get(0).getId(), "Wrong Additional Identifier:id in response!");
//            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(1).getScheme(), expectedSchemeInfo.getAdditionalIdentifiers().get(0).getScheme(), "Wrong Additional Identifier:scheme in response!");
//            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(1).getLegalName(), expectedSchemeInfo.getAdditionalIdentifiers().get(0).getLegalName(), "Wrong Additional Identifier:legal name in response!");
//            Assert.assertEquals(actualSchemeInfo.getAdditionalIdentifiers().get(1).getUri(), expectedSchemeInfo.getAdditionalIdentifiers().get(0).getUri(), "Wrong Additional Identifier:Uri in response!");
//        }

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
        Assert.assertEquals(actualSchemeInfo.getContactPoint().getUrl(), expectedSchemeInfo.getContactPoint().getUrl(), "Wrong contactPoint:url in response!");
    }

    public static void verifyInvalidGetSchemeResponse(int errorCode, Response response) {
        switch (errorCode) {
            case 400:
                verifyStatusCode(response, errorCode);
                Assert.assertEquals(response.getBody().asString(), "{\"schemeId\":[\"can't be blank\",\"No such scheme registered\"]}", "Wrong contactPoint:url in response!");
                break;

            case 401:
                //verifyStatusCode(response, errorCode);
               // Bug: CON-450 Assert.assertEquals(response.getBody().asString(), "{}", "Wrong contactPoint:url in response!");
                break;
        }
    }

    public static void verifyInvalidSchemeNameResponse(Response response) {
        // Bug:CON-450 verifyStatusCode(response, 400);
        // Assert.assertEquals(response.getBody().asString(), "", "Wrong contactPoint:url in response!");
    }

    public static void verifyGetSchemesResponse(Response response) throws IOException {
        verifyStatusCode(response, 200);
        logger.info("GetSchemesResponse" + response.asString());

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
        Assert.assertEquals(actualRes.getSchemeInfo(), expectedRes.getInfo(scheme), "Wrong PostSchemeInfo response!");
    }
}
