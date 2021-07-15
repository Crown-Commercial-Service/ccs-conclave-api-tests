package com.ccs.conclave.api.cii.tests;

import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.requests.RestRequests;
import com.ccs.conclave.api.common.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.ccs.conclave.api.cii.data.OrgDataProvider.getExpSchemeInfoWithOnlySFIdentifier;
import static com.ccs.conclave.api.cii.data.OrgDataProvider.getExpectedSchemeInfo;
import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.getAllRegisteredSchemesInfo;
import static com.ccs.conclave.api.cii.verification.VerifyEndpointResponses.*;

public class DataMigrationPostEndpointTests extends BaseClass {

    private String getSFId(String sfInfo) {
        return sfInfo.split("(?=~)")[0];
    }

    private String getSFUrn(String sfInfo) {
        return sfInfo.split("(?=~)")[1].replace("~", "");
    }

    @Test
    public void postSFInfo_using_SFID_has_COH() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(COMPANIES_HOUSE);

        SchemeInfo schemeInfoWithSF = getExpSchemeInfoWithOnlySFIdentifier(COMPANIES_HOUSE);
        String sfId = getSFId(schemeInfoWithSF.getAdditionalIdentifiers().get(0).getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfid", sfId);

        // COH is Primary:true hidden:false, SF id is Primary:false, hidden:true
        SchemeInfo expectedSchemeInfo = new SchemeInfo();
        expectedSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        expectedSchemeInfo.getIdentifier().setHidden("false");
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes); // verify the hidden:false ids

        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId(postSchemeRes));
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);

        // Duplicate check
        postSchemeRes = RestRequests.postSFInfo("sfid", sfId);
        verifyResponseCodeForDuplicateResource(postSchemeRes);
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);


    }


    @Test
    public void postSFInfo_using_SFURN_has_DUNS() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(DUN_AND_BRADSTREET);

        SchemeInfo schemeInfoWithSF = getExpSchemeInfoWithOnlySFIdentifier(DUN_AND_BRADSTREET);
        String sfUrn = getSFUrn(schemeInfoWithSF.getAdditionalIdentifiers().get(0).getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", sfUrn);

        // DUNS is Primary:true hidden:false, SF id is Primary:false, hidden:true
        SchemeInfo expectedSchemeInfo = new SchemeInfo();
        expectedSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        expectedSchemeInfo.getIdentifier().setHidden("false");
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes); // verify the hidden:false ids

        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId(postSchemeRes));
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);
    }

    @Test
    public void postSFInfo_using_SFURN_has_DUNS_and_COH() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_COH);

        SchemeInfo schemeInfoWithSF = getExpSchemeInfoWithOnlySFIdentifier(DUN_AND_BRADSTREET_WITH_COH);
        String sfUrn = getSFUrn(schemeInfoWithSF.getAdditionalIdentifiers().get(0).getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", sfUrn);

        // COH is Primary:true hidden:false, DUNs is Primary:false, hidden:false, SF id is Primary:false, hidden:true
        SchemeInfo expectedSchemeInfo = new SchemeInfo();
        expectedSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        expectedSchemeInfo.getIdentifier().setHidden("false");
//        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
//        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes); // verify the hidden:false ids

        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId(postSchemeRes));
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);
    }

    @Test // Defect CON-1063
    public void postSFInfo_using_SFURN_has_DUNS_COH_and_CHC() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);

        SchemeInfo schemeInfoWithSF = getExpSchemeInfoWithOnlySFIdentifier(DUN_AND_BRADSTREET_WITH_CHC_AND_COH);
        String sfUrn = getSFUrn(schemeInfoWithSF.getAdditionalIdentifiers().get(0).getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", sfUrn);

        // COH is Primary:true hidden:false, DUNs and CHC are Primary:false, hidden:false, SF id is Primary:false, hidden:true
        SchemeInfo expectedSchemeInfo = new SchemeInfo();
        expectedSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        expectedSchemeInfo.getIdentifier().setHidden("false");

//        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
//        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(1));
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes); // verify the hidden:false ids

        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(2));
        expectedSchemeInfo.getAdditionalIdentifiers().get(2).setHidden("true");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId(postSchemeRes));
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);
    }

    @Test
    public void postSFInfo_using_SFURN_has_No_DUNS_or_COH() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(SALES_FORCE);

        String sfId = getSFId(schemeInfo.getIdentifier().getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfid", sfId);

        // SF id is Primary:false, hidden:true
        SchemeInfo expectedSchemeInfo = new SchemeInfo();
        Identifier identifier = expectedSchemeInfo.getIdentifier();
        identifier.setId("114173941");
        identifier.setLegalName("");
        identifier.setUri("");
        identifier.setScheme(getSchemeCode(DUN_AND_BRADSTREET));
        identifier.setHidden("false");

        List<Identifier> additionalIdentifiers = expectedSchemeInfo.getAdditionalIdentifiers();
        additionalIdentifiers.get(0).setId("");
        additionalIdentifiers.get(0).setLegalName("");
        additionalIdentifiers.get(0).setScheme("");
        additionalIdentifiers.get(0).setUri("");
        additionalIdentifiers.get(0).setHidden("true");
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");

        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getIdentifier());
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");

        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId(postSchemeRes));
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);

    }

    @Test
    public void postSFInfo_using_SFURN_has_Valid_DUNS_Invalid_COH() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(SFID_WITH_VALID_DUNS_INVALID_COH);

        SchemeInfo schemeInfoWithSF = getExpSchemeInfoWithOnlySFIdentifier(SFID_WITH_VALID_DUNS_INVALID_COH);
        String sfUrn = getSFUrn(schemeInfoWithSF.getAdditionalIdentifiers().get(0).getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", sfUrn);

        // DUN is Primary:true hidden:false, SF id is Primary:false, hidden:true
        SchemeInfo expectedSchemeInfo = new SchemeInfo();
        expectedSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        expectedSchemeInfo.getIdentifier().setHidden("false");
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes); // verify the hidden:false ids

        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId(postSchemeRes));
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);
    }

    @Test
    public void postSFInfo_using_SFURN_has_Valid_DUNS_UNKNOWN_COH() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(SFID_WITH_VALID_DUNS_UNKNOWN_COH);

        SchemeInfo schemeInfoWithSF = getExpSchemeInfoWithOnlySFIdentifier(SFID_WITH_VALID_DUNS_UNKNOWN_COH);
        String sfUrn = getSFUrn(schemeInfoWithSF.getAdditionalIdentifiers().get(0).getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", sfUrn);

        // DUN is Primary:true hidden:false, SF id is Primary:false, hidden:true
        SchemeInfo expectedSchemeInfo = new SchemeInfo();
        expectedSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        expectedSchemeInfo.getIdentifier().setHidden("false");
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes); // verify the hidden:false ids

        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId(postSchemeRes));
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);
    }

    @Test
    public void postSFInfo_using_SFURN_has_Valid_COH_Invalid_DUNs() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(SFID_WITH_VALID_COH_INVALID_DUNS);

        SchemeInfo schemeInfoWithSF = getExpSchemeInfoWithOnlySFIdentifier(SFID_WITH_VALID_COH_INVALID_DUNS);
        String sfUrn = getSFUrn(schemeInfoWithSF.getAdditionalIdentifiers().get(0).getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", sfUrn);

        // COH is Primary:true hidden:false, SF id is Primary:false, hidden:true
        SchemeInfo expectedSchemeInfo = new SchemeInfo();
        expectedSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        expectedSchemeInfo.getIdentifier().setHidden("false");
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes); // verify the hidden:false ids

        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId(postSchemeRes));
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);
    }

    @Test
    public void postSFInfo_using_SFURN_has_Valid_COH_Unknown_DUNs() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(SFID_WITH_VALID_COH_UNKNOWN_DUNS);

        SchemeInfo schemeInfoWithSF = getExpSchemeInfoWithOnlySFIdentifier(SFID_WITH_VALID_COH_UNKNOWN_DUNS);
        String sfUrn = getSFUrn(schemeInfoWithSF.getAdditionalIdentifiers().get(0).getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", sfUrn);

        // COH is Primary:true hidden:false, SF id is Primary:false, hidden:true
        SchemeInfo expectedSchemeInfo = new SchemeInfo();
        expectedSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        expectedSchemeInfo.getIdentifier().setHidden("false");
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes); // verify the hidden:false ids

        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("true");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId(postSchemeRes));
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);
    }

    @Test
    public void postSFInfo_using_SFID_has_DUNs_CHC_No_COH() {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(SFID_WITH_DUNS_CHC_NO_COH);

        SchemeInfo schemeInfoWithSF = getExpSchemeInfoWithOnlySFIdentifier(SFID_WITH_DUNS_CHC_NO_COH);
        String sfUrn = getSFUrn(schemeInfoWithSF.getAdditionalIdentifiers().get(0).getId());

        // Perform Post Operation
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", sfUrn);

        // DUNs is Primary:true, hidden:false;CHC is Primary:false, hidden:false; SF id is Primary:true, hidden:true
        SchemeInfo expectedSchemeInfo = new SchemeInfo();
        expectedSchemeInfo.setIdentifier(schemeInfo.getIdentifier());
        expectedSchemeInfo.getIdentifier().setHidden("false");
        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
        expectedSchemeInfo.getAdditionalIdentifiers().get(0).setHidden("false");
        verifyPostSchemeInfoResponse(expectedSchemeInfo, postSchemeRes); // verify the hidden:false ids

        expectedSchemeInfo.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(1));
        expectedSchemeInfo.getAdditionalIdentifiers().get(1).setHidden("true");
        Response registeredSchemesRes = getAllRegisteredSchemesInfo(getCCSOrgId(postSchemeRes));
        verifyAllRegisteredSchemes(registeredSchemesRes, expectedSchemeInfo);
    }

    @Test // Defect CON-1063
    public void postSFInfo_with_Invalid_QueryParam() {
        Response postSchemeRes = RestRequests.postSFInfo("sfurn", "0014J00000Ov486QAC");
        verifyInvalidIdResponse(postSchemeRes);

        postSchemeRes = RestRequests.postSFInfo("sfid", "106607928");
        verifyInvalidIdResponse(postSchemeRes);

        postSchemeRes = RestRequests.postSFInfo("sfURN", "0014J00000Ov486QAB");
        verifyBadRequestResponse(postSchemeRes);

        postSchemeRes = RestRequests.postSFInfo("sfID", "10590792");
        verifyBadRequestResponse(postSchemeRes);
    }
}

