package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import io.restassured.response.Response;
import org.testng.Assert;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.verifyGetSchemeInfoResponse;

public class RequestPayloads {
    public static String getSchemeInfoWithInvalidAddIdentifier(SchemeRegistry scheme) {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(scheme);
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
        return response.asString().replaceAll(schemeInfo.getAdditionalIdentifiers().get(0).getId(), "55667788776");
    }

    public static String getSchemeInfoWithAddIdentifierofAnotherScheme(SchemeRegistry scheme) {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(scheme);
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
        String schemeName = getSchemeCode(SCOTLAND_CHARITY);
        String additionalIdentifier = OrgDataProvider.getInfo(CHARITIES_COMMISSION_WITH_SC).getAdditionalIdentifiers().get(0).getId();
        return response.asString().replaceAll(schemeInfo.getAdditionalIdentifiers().get(0).getScheme(), schemeName).replaceAll(schemeInfo.getAdditionalIdentifiers().get(0).getId(), additionalIdentifier);
    }

    // Modify the response to update Valid Scheme of Primary Scheme with Invalid Scheme
    public static String getSchemeInfoWithInvalidPrimaryScheme() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(COMPANIES_HOUSE);
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it
        return response.asString().replaceAll(getSchemeCode(COMPANIES_HOUSE), getSchemeCode(INVALID_SCHEME));
    }

    // Modify the response to update Valid Identifier of Primary Scheme with Invalid Identifier
    public static String getSchemeInfoWithInvalidPrimaryID() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WALES);
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WALES, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it
        return response.asString().replaceAll(schemeInfo.getIdentifier().getId(), "9988776655");
    }

    public static String getSchemeInfoWithoutAddIdentifierSection(SchemeRegistry scheme) {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(scheme);
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it
        String[] strings = response.asString().split("additionalIdentifiers\":\\[(.*?)\\],\"");
        return strings[0] + strings[1];
    }

    public static String getSchemeInfoWithEmptyAddIdentifiers(SchemeRegistry scheme) {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(scheme);
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it

        String[] strings = response.asString().split("additionalIdentifiers\":\\[(.*?)\\]");
        return strings[0] + "additionalIdentifiers\":[]" + strings[1];
    }

    public static String getSchemeInfoWithFirstAddIdentifier(SchemeRegistry scheme) {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(scheme);
        Assert.assertTrue(schemeInfo.getAdditionalIdentifiers().size() > 0, "No additional identifiers in the test data!!");
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it

        String[] identifier = response.asString().split("additionalIdentifiers\":\\[");
        String[] addIdentifiers = identifier[1].split("},");
        String[] address = identifier[1].split("address\":\\{");
        return identifier[0] + "additionalIdentifiers\":[" + addIdentifiers[0] + "}" + "]," + "\"address\":{" + address[1];
    }
}
