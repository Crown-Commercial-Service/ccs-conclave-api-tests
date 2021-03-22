package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import io.restassured.response.Response;
import org.testng.Assert;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.verifyGetSchemeInfoResponse;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.verifyResponseCodeForSuccess;

public class RequestPayloads {
    public static String getSchemeInfoWithInvalidAddIdentifier(SchemeRegistry scheme) {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(scheme);
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);
        return response.asString().replaceAll(schemeInfo.getAdditionalIdentifiers().get(0).getId(), "55667788776");
    }

    public static String getSchemeInfoWithAddIdentifierOfAnotherScheme(SchemeRegistry scheme) {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(scheme);
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response);

        Identifier addIdOfCurrentScheme = schemeInfo.getAdditionalIdentifiers().get(0);
        Identifier addIdOfAnotherScheme = OrgDataProvider.getExpectedSchemeInfo(CHARITIES_COMMISSION_WITH_SC).getAdditionalIdentifiers().get(0);
        return response.asString().replaceAll(addIdOfCurrentScheme.getScheme(), addIdOfAnotherScheme.getScheme())
                .replaceAll(addIdOfCurrentScheme.getId(), addIdOfAnotherScheme.getId());
    }

    // Modify the response to update Valid Scheme of Primary Scheme with Invalid Scheme
    public static String getSchemeInfoWithInvalidPrimaryScheme() throws InterruptedException {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(COMPANIES_HOUSE);
        Thread.sleep(500);
        Response response = RestRequests.getSchemeInfo(COMPANIES_HOUSE, schemeInfo.getIdentifier().getId());
        //verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it
        verifyResponseCodeForSuccess(response);
        return response.asString().replaceAll(getSchemeCode(COMPANIES_HOUSE), getSchemeCode(INVALID_SCHEME));
    }

    // Modify the response to update Valid Identifier of Primary Scheme with Invalid Identifier
    public static String getSchemeInfoWithInvalidPrimaryID() {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(DUN_AND_BRADSTREET_WALES);
        Response response = RestRequests.getSchemeInfo(DUN_AND_BRADSTREET_WALES, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it
        return response.asString().replaceAll(schemeInfo.getIdentifier().getId(), "9988776655");
    }

    public static String getSchemeInfoWithoutAddIdentifierSection(SchemeRegistry scheme) throws InterruptedException {

        String id = OrgDataProvider.getExpectedSchemeInfo(scheme).getIdentifier().getId();
        Thread.sleep(500);
        Response response = RestRequests.getSchemeInfo(scheme, id);
        //verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it
        verifyResponseCodeForSuccess(response);
        String[] strings = response.asString().split("additionalIdentifiers\":\\[(.*?)],\"");
        return strings[0] + strings[1];
    }

    public static String getSchemeInfoWithoutAddIdentifiers(SchemeRegistry scheme) {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(scheme);
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it

        String[] strings = response.asString().split("additionalIdentifiers\":\\[(.*?)]");
        return strings[0] + "additionalIdentifiers\":[]" + strings[1];
    }

    public static String getSchemeInfoWithFirstAddIdentifier(SchemeRegistry scheme) {
        SchemeInfo schemeInfo = OrgDataProvider.getExpectedSchemeInfo(scheme);
        Assert.assertTrue(schemeInfo.getAdditionalIdentifiers().size() > 0, "No additional identifiers in the test data!!");
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it

        String[] identifier = response.asString().split("additionalIdentifiers\":\\[");
        String[] addIdentifiers = identifier[1].split("},");
        String[] address = identifier[1].split("address\":\\{");
        return identifier[0] + "additionalIdentifiers\":[" + addIdentifiers[0] + "}" + "]," + "\"address\":{" + address[1];
    }
}
