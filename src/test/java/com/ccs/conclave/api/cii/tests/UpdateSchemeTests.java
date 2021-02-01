package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.data.SchemeRegistry;
import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.*;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;

public class UpdateSchemeTests extends BaseClass {

    @Test
    public void updateScheme_COH_into_DUNS() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(DUN_AND_BRADSTREET_WITH_COH);
        // GetScheme response without additional identifiers
        String responseStr = getSchemeInfoWithoutAddIdentifiers(schemeInfo, DUN_AND_BRADSTREET_WITH_COH);
        // Get expected SchemeInfo without additional identifiers
        SchemeInfo expectedSchemeInfo = getInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);

        // Perform Post Operation/ register organisation with only Primary Identifier
        Response response = RestRequests.postSchemeInfo(responseStr);

        // verify the post response and ensure only Primary identifier is used for organisation registration
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);


        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(DUN_AND_BRADSTREET_WITH_COH);
        Assert.assertTrue(additionalSchemesInfo.size() == 1, "Only one additional identifier is expected, please check the test data!");

        AdditionalSchemeInfo additionalSchemeInfo = additionalSchemesInfo.get(0);
        response = RestRequests.updateScheme(getCCSOrgId(), additionalSchemeInfo);
        verifyResponseCodeForUpdatedResource(response);
        verifyUpdatedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo);
    }

    @Test
    public void updateScheme_COH_and_CHC_into_SC() {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(SCOTLAND_CHARITY_WITH_COH_CHC);
        // GetScheme response without additional identifiers
        String responseStr = getSchemeInfoWithoutAddIdentifiers(schemeInfo, SCOTLAND_CHARITY_WITH_COH_CHC);
        // Get expected SchemeInfo without additional identifiers
        SchemeInfo expectedSchemeInfo = getInfoWithoutAddIdentifiers(SCOTLAND_CHARITY_WITH_COH_CHC);

        // Perform Post Operation/ register organisation with only Primary Identifier
        Response response = RestRequests.postSchemeInfo(responseStr);

        // verify the post response and ensure only Primary identifier is used for organisation registration
        verifyPostSchemeInfoResponse(expectedSchemeInfo, response);


        // get only AdditionalIdentifiers from the given Scheme
        List<AdditionalSchemeInfo> additionalSchemesInfo = getAdditionalIdentifierInfo(SCOTLAND_CHARITY_WITH_COH_CHC);
        Assert.assertTrue(additionalSchemesInfo.size() == 2, "Two additional identifier are expected, please check the test data!");

        AdditionalSchemeInfo additionalSchemeInfo1 = additionalSchemesInfo.get(0);
        response = RestRequests.updateScheme(getCCSOrgId(), additionalSchemeInfo1);
        verifyResponseCodeForUpdatedResource(response);
        verifyUpdatedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo1);

        AdditionalSchemeInfo additionalSchemeInfo2 = additionalSchemesInfo.get(1);
        response = RestRequests.updateScheme(getCCSOrgId(), additionalSchemeInfo2);
        verifyResponseCodeForUpdatedResource(response);
        verifyUpdatedScheme(schemeInfo.getIdentifier().getId(), additionalSchemeInfo2);
    }

    // Todo @Test
    public void updateScheme_InvalidScheme_into_CHC() {

    }

    // Todo @Test
    public void updateScheme_InvalidOrgId() {

    }

    private String getSchemeInfoWithoutAddIdentifiers(SchemeInfo schemeInfo, SchemeRegistry scheme) {
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before using it

        String[] strings = response.asString().split("additionalIdentifiers\":\\[(.*?)\\]");
        // Bug: CON-543 - Remove hardcoded string below when the issue is fixed
        String responseWithoutAddIdentifiers = strings[0] + "additionalIdentifiers\":[]" + strings[1];
        return responseWithoutAddIdentifiers;
    }

}
