package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.common.SchemeRegistry;
import lombok.Getter;
import lombok.Setter;

import static com.ccs.conclave.api.common.SchemeRegistry.*;

public class OrgDataProvider {
    @Setter  @Getter
    public class SchemeInfo {
        private String schemeCode;
        private String identifier;
        private String uri;
        private String legalName;
        private String streetName;
        private String locality;
        private String region;
        private String postcode;
        private String countryCode;
    }

    public SchemeInfo getInfo(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = new SchemeInfo();
        switch (schemeRegistry) {
            case COMPANIES_HOUSE:
                schemeInfo.setSchemeCode(getSchemeCode(COMPANIES_HOUSE));
                schemeInfo.setIdentifier("1800000");
                schemeInfo.setLegalName("BRITISH TELECOMMUNICATIONS PUBLIC LIMITED COMPANY");
                schemeInfo.setUri("");
                schemeInfo.setStreetName("22 Baker Street");
                schemeInfo.setPostcode("W1U 3BW");
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
