package com.ccs.conclave.api.cii.verification;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;

import com.ccs.conclave.api.cii.pojo.Schemes;
import com.ccs.conclave.api.cii.response.GetSchemeInfoResponse;
import com.ccs.conclave.api.cii.response.PostSchemeInfoResponse;
import com.ccs.conclave.api.cii.response.GetSchemesResponse;
import com.ccs.conclave.api.cii.data.SchemeRegistry;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;


public class VerifyResponses {

    public static void verifyGetSchemeInfoResponse(SchemeRegistry scheme, SchemeInfo schemeInfo, Response response) {
        GetSchemeInfoResponse actualRes = response.as(GetSchemeInfoResponse.class);
        Assert.assertEquals(actualRes.getSchemeInfo(), schemeInfo, "Wrong GetSchemeInfo response!" );
    }

    public static void verifyInvalidGetSchemeInfoResponse(Response response) {

    }

    public static void verifyGetSchemesResponse(Response response) throws JsonProcessingException {
       ObjectMapper objectMapper = new ObjectMapper();
//         ObjectMapper objectMapper = new ObjectMapper()
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
//                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        System.out.println(response.asString());
        GetSchemesResponse schemesResponses = objectMapper.readValue(response.asString(), GetSchemesResponse.class);

    //    GetSchemesResponse schemesResponse = response.as(GetSchemesResponse.class);
     //   List<Schemes> schemesList = Arrays.asList(response.getBody().as(Schemes[].class));
  //      GetSchemesResponse schemesResponse = response.getBody().as(GetSchemesResponse.class);
        System.out.println("response.asString()");
//        Schemes schemeCOH = schemesResponse.getSchemeCOH();
//        Assert.assertEquals(schemeCOH.getSchemeRegisterCode(), getSchemeCode(COMPANIES_HOUSE), "Wrong SchemeCode!");
//        Assert.assertEquals(schemeCOH.getSchemeName(), getSchemeName(COMPANIES_HOUSE), "Wrong SchemeName!");
//        Assert.assertEquals(schemeCOH.getSchemeUri(), getSchemeURL(COMPANIES_HOUSE), "Wrong SchemeURL!");
//        Assert.assertEquals(schemeCOH.getSchemeCountryCode(), getSchemeCountryCode(COMPANIES_HOUSE), "Wrong CountryCode!");
//
//        Schemes schemeDUNS = schemesResponse.getSchemeDUNS();
//        Assert.assertEquals(schemeDUNS.getSchemeRegisterCode(), getSchemeCode(DUNS_AND_BRADSTREET), "Wrong SchemeCode!");
//        Assert.assertEquals(schemeDUNS.getSchemeName(), getSchemeName(DUNS_AND_BRADSTREET), "Wrong SchemeName!");
//        Assert.assertEquals(schemeDUNS.getSchemeUri(), getSchemeURL(DUNS_AND_BRADSTREET), "Wrong SchemeURL!");
//        Assert.assertEquals(schemeDUNS.getSchemeCountryCode(), getSchemeCountryCode(DUNS_AND_BRADSTREET), "Wrong CountryCode!");
//
//        Schemes schemeCHC = schemesResponse.getSchemeCHC();
//        Assert.assertEquals(schemeCHC.getSchemeRegisterCode(), getSchemeCode(CHARITIES_COMMISSION), "Wrong SchemeCode!");
//        Assert.assertEquals(schemeCHC.getSchemeName(), getSchemeName(CHARITIES_COMMISSION), "Wrong SchemeName!");
//        Assert.assertEquals(schemeCHC.getSchemeUri(), getSchemeURL(CHARITIES_COMMISSION), "Wrong SchemeURL!");
//        Assert.assertEquals(schemeCHC.getSchemeCountryCode(), getSchemeCountryCode(CHARITIES_COMMISSION), "Wrong CountryCode!");
//
//        Schemes schemeNIC = schemesResponse.getSchemeNIC();
//        Assert.assertEquals(schemeNIC.getSchemeRegisterCode(), getSchemeCode(NORTHERN_ISLAND_CHARITY), "Wrong SchemeCode!");
//        Assert.assertEquals(schemeNIC.getSchemeName(), getSchemeName(NORTHERN_ISLAND_CHARITY), "Wrong SchemeName!");
//        Assert.assertEquals(schemeNIC.getSchemeUri(), getSchemeURL(NORTHERN_ISLAND_CHARITY), "Wrong SchemeURL!");
//        Assert.assertEquals(schemeNIC.getSchemeCountryCode(), getSchemeCountryCode(NORTHERN_ISLAND_CHARITY), "Wrong CountryCode!");
//
//        Schemes schemeSC = schemesResponse.getSchemeSC();
//        Assert.assertEquals(schemeSC.getSchemeRegisterCode(), getSchemeCode(SCOTLAND_CHARITY), "Wrong SchemeCode!");
//        Assert.assertEquals(schemeSC.getSchemeName(), getSchemeName(SCOTLAND_CHARITY), "Wrong SchemeName!");
//        Assert.assertEquals(schemeSC.getSchemeUri(), getSchemeURL(SCOTLAND_CHARITY), "Wrong SchemeURL!");
//        Assert.assertEquals(schemeSC.getSchemeCountryCode(), getSchemeCountryCode(SCOTLAND_CHARITY), "Wrong CountryCode!");
    }

    public static void verifyPostSchemeInfoResponse(SchemeRegistry scheme, OrgDataProvider expectedRes, PostSchemeInfoResponse actualRes) {
        Assert.assertEquals(actualRes.getSchemeInfo(), expectedRes.getInfo(scheme), "Wrong PostSchemeInfo response!" );
    }
}
