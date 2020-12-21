package com.ccs.conclave.api.cii.verification;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.pojo.SchemeName;
import com.ccs.conclave.api.cii.response.SchemeInfoResponse;
import com.ccs.conclave.api.cii.response.SchemeNamesResponse;
import io.restassured.response.Response;
import org.testng.Assert;

import static com.ccs.conclave.api.common.SchemeRegistry.*;

public class VerifyResponses {

    public static void verifyGetSchemeInfoResponse(OrgDataProvider actualRes, SchemeInfoResponse expectedRes) {
        Assert.assertEquals(actualRes, expectedRes, "GetSchemeInfoResponse is not matching!!");
    }

    public static void verifyGetSchemeNamesResponse(Response response) {
        SchemeNamesResponse schemeNamesResponse = response.as(SchemeNamesResponse.class);
        SchemeName schemeNameCOH = schemeNamesResponse.getSchemeCOH();
        Assert.assertEquals(schemeNameCOH.getSchemeRegisterCode(), getSchemeCode(COMPANIES_HOUSE), "Wrong SchemeCode!");
        Assert.assertEquals(schemeNameCOH.getSchemeName(), getSchemeName(COMPANIES_HOUSE), "Wrong SchemeName!");
        Assert.assertEquals(schemeNameCOH.getSchemeURI(), getSchemeURL(COMPANIES_HOUSE), "Wrong SchemeURL!");
        Assert.assertEquals(schemeNameCOH.getSchemeCountryCode(), getSchemeCountryCode(COMPANIES_HOUSE), "Wrong CountryCode!");

        SchemeName schemeNameDUNS = schemeNamesResponse.getSchemeCOH();
        Assert.assertEquals(schemeNameDUNS.getSchemeRegisterCode(), getSchemeCode(DUNS_AND_BRADSTREET), "Wrong SchemeCode!");
        Assert.assertEquals(schemeNameDUNS.getSchemeName(), getSchemeName(DUNS_AND_BRADSTREET), "Wrong SchemeName!");
        Assert.assertEquals(schemeNameDUNS.getSchemeURI(), getSchemeURL(DUNS_AND_BRADSTREET), "Wrong SchemeURL!");
        Assert.assertEquals(schemeNameDUNS.getSchemeCountryCode(), getSchemeCountryCode(DUNS_AND_BRADSTREET), "Wrong CountryCode!");

        SchemeName schemeNameCHC = schemeNamesResponse.getSchemeCOH();
        Assert.assertEquals(schemeNameCHC.getSchemeRegisterCode(), getSchemeCode(CHARITIES_COMMISSION), "Wrong SchemeCode!");
        Assert.assertEquals(schemeNameCHC.getSchemeName(), getSchemeName(CHARITIES_COMMISSION), "Wrong SchemeName!");
        Assert.assertEquals(schemeNameCHC.getSchemeURI(), getSchemeURL(CHARITIES_COMMISSION), "Wrong SchemeURL!");
        Assert.assertEquals(schemeNameCHC.getSchemeCountryCode(), getSchemeCountryCode(CHARITIES_COMMISSION), "Wrong CountryCode!");

        SchemeName schemeNameNIC = schemeNamesResponse.getSchemeCOH();
        Assert.assertEquals(schemeNameNIC.getSchemeRegisterCode(), getSchemeCode(NORTHERN_ISLAND_CHARITY), "Wrong SchemeCode!");
        Assert.assertEquals(schemeNameNIC.getSchemeName(), getSchemeName(NORTHERN_ISLAND_CHARITY), "Wrong SchemeName!");
        Assert.assertEquals(schemeNameNIC.getSchemeURI(), getSchemeURL(NORTHERN_ISLAND_CHARITY), "Wrong SchemeURL!");
        Assert.assertEquals(schemeNameNIC.getSchemeCountryCode(), getSchemeCountryCode(NORTHERN_ISLAND_CHARITY), "Wrong CountryCode!");

        SchemeName schemeNameSC = schemeNamesResponse.getSchemeCOH();
        Assert.assertEquals(schemeNameSC.getSchemeRegisterCode(), getSchemeCode(SCOTLAND_CHARITY), "Wrong SchemeCode!");
        Assert.assertEquals(schemeNameSC.getSchemeName(), getSchemeName(SCOTLAND_CHARITY), "Wrong SchemeName!");
        Assert.assertEquals(schemeNameSC.getSchemeURI(), getSchemeURL(SCOTLAND_CHARITY), "Wrong SchemeURL!");
        Assert.assertEquals(schemeNameSC.getSchemeCountryCode(), getSchemeCountryCode(SCOTLAND_CHARITY), "Wrong CountryCode!");


    }
}
