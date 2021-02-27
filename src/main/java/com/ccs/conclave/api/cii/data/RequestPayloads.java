package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import io.restassured.response.Response;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.verifyGetSchemeInfoResponse;

public class RequestPayloads {
    public static String getSchemeInfoWithInvalidAddIdentifier(SchemeInfo schemeInfo, SchemeRegistry scheme) {
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
        return response.asString().replaceAll(schemeInfo.getAdditionalIdentifiers().get(0).getId(), "55667788776");
    }

    public static String getSchemeInfoWithAddIdentifierofAnotherScheme(SchemeInfo schemeInfo, SchemeRegistry scheme) {
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
        return response.asString().replaceAll(schemeInfo.getAdditionalIdentifiers().get(0).getScheme(), getSchemeCode(SCOTLAND_CHARITY)).replaceAll(schemeInfo.getAdditionalIdentifiers().get(0).getId(), "SC037536");
    }

    public static String getSchemeInfoWithInvalidPrimaryScheme(SchemeInfo schemeInfo, SchemeRegistry scheme) {
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it
        return response.asString().replaceAll(getSchemeCode(scheme), getSchemeCode(INVALID_SCHEME));
    }

    public static String getSchemeInfoWithInvalidPrimaryID(SchemeInfo schemeInfo, SchemeRegistry scheme) {
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it
        return response.asString().replaceAll(schemeInfo.getIdentifier().getId(), "9988776655");
    }

    public static String getSchemeInfoWithoutAddIdentifierSection(SchemeInfo schemeInfo, SchemeRegistry scheme) {
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it
        String[] strings = response.asString().split("additionalIdentifiers\":\\[(.*?)\\],\"");
        return strings[0] + strings[1];
    }

    public static String getSchemeInfoWithEmptyAddIdentifiers(SchemeInfo schemeInfo, SchemeRegistry scheme) {
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it

        String[] strings = response.asString().split("additionalIdentifiers\":\\[(.*?)\\]");
        // Bug: CON-543 - Remove hardcoded string below when the issue is fixed
        return strings[0] + "additionalIdentifiers\":[]" + strings[1];
    }
}
