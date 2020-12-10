package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.AdditionalIdentifiers;
import com.ccs.conclave.api.cii.pojo.Address;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.cii.response.SchemeInfoResponse;
import com.ccs.conclave.api.common.SchemeRegistry;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.ccs.conclave.api.common.SchemeRegistry.*;

public class OrgDataProvider {
    private SchemeInfoResponse schemeInfoResponse;

    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public void setSchemeInfoResponse(String response) throws JsonProcessingException {
        this.schemeInfoResponse = objectMapper.readValue(response, SchemeInfoResponse.class);
    }

    public SchemeInfoResponse getSchemeInfoResponse() {
        return this.schemeInfoResponse;
    }

    public static SchemeInfo getInfo(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = new SchemeInfo();
        Identifier identifier = new Identifier();
        AdditionalIdentifiers additionalIdentifiers = new AdditionalIdentifiers();
        Address address = new Address();
        switch (schemeRegistry) {
            case COMPANIES_HOUSE:
                schemeInfo.setName("BRITISH TELECOMMUNICATIONS PUBLIC LIMITED COMPANY");

                identifier.setId("1800000");
                identifier.setLegalName("BRITISH TELECOMMUNICATIONS PUBLIC LIMITED COMPANY");
                identifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                identifier.setId("1800000");
                identifier.setLegalName("BRITISH TELECOMMUNICATIONS PUBLIC LIMITED COMPANY");
                identifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                identifier.setUri("");
                additionalIdentifiers.setIdentifier(identifier);

                address.setCountryName("");
                address.setLocality("");
                address.setPostcode("");
                address.setRegion("");
                address.setStreetAddress("");

                schemeInfo.setAddress(address);

                break;

            case DUNS_AND_BRADSTREET:
                break;

            case CHARITIES_COMMISSION:
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + schemeRegistry);
        }
        return schemeInfo;
    }
}
