package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.data.OrgDataProvider;
import com.ccs.conclave.api.cii.data.SchemeRegistry;
import com.ccs.conclave.api.cii.pojo.AdditionalSchemeInfo;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.*;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.verification.VerifyResponses.*;

public class UpdateSchemeTests extends BaseClass {
    private static List<Identifier> additionalIdentifiers = new ArrayList<>();

    @Test
    public void updateCOHToDUNSPrimaryScheme() {
        // GetScheme response without additional identifiers
        String responseStr = getSchemeInfoResponseWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH);

        // Perform Post Operation/ register organisation with only Primary Identifier
        Response response = RestRequests.postSchemeInfo(responseStr);
        verifyPostSchemeInfoResponse(getInfoWithoutAddIdentifiers(DUN_AND_BRADSTREET_WITH_COH), response); // verify the response using modified schemeInfo

        AdditionalSchemeInfo additionalSchemeInfoCOH = getAdditionalIdentifierInfo(COMPANIES_HOUSE_IS_A_DUNS_ADDITIONAL_IDENTIFIER);
        RestRequests.updateScheme(additionalSchemeInfoCOH);
    }

    private String getSchemeInfoResponseWithoutAddIdentifiers(SchemeRegistry scheme) {
        SchemeInfo schemeInfo = OrgDataProvider.getInfo(scheme);

        // Perform Get call to form the request payload for POST call
        Response response = RestRequests.getSchemeInfo(scheme, schemeInfo.getIdentifier().getId());
        //  verifyGetSchemeInfoResponse(schemeInfo, response); // verify Get SchemeInfo response before passing to Post

        String responseWithoutAddIdentifiers = response.getBody().jsonPath().get("name").toString() +
                response.getBody().jsonPath().get("identifier").toString() +
                response.getBody().jsonPath().get("address").toString() +
                response.getBody().jsonPath().get("contactPoint").toString();

        // Todo add additional identifiers in additionalIdentifiers list
        return responseWithoutAddIdentifiers;
    }

}
