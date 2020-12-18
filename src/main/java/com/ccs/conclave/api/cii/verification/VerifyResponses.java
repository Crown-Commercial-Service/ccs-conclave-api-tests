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

        // Todo verify all the scheme name details
    }
}
