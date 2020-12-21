package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.Address;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.common.SchemeRegistry;

import static com.ccs.conclave.api.common.SchemeRegistry.*;

public class OrgDataProvider {

    public static SchemeInfo getInfo(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = new SchemeInfo();
        Identifier identifier = new Identifier();
//        AdditionalIdentifiers additionalIdentifiers = new AdditionalIdentifiers();
//        Identifier additionalIdentifier = new Identifier();
        Address address = new Address();
        switch (schemeRegistry) {
            case COMPANIES_HOUSE:
                schemeInfo.setName("BRITISH TELECOMMUNICATIONS PUBLIC LIMITED COMPANY");
                identifier.setId("1800000");
                identifier.setLegalName("BRITISH TELECOMMUNICATIONS PUBLIC LIMITED COMPANY");
                identifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

//                additionalIdentifier.setId("");
//                additionalIdentifier.setLegalName("");
//                additionalIdentifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
//                identifier.setUri("");
//                additionalIdentifiers.setIdentifier(additionalIdentifier);

                address.setCountryName("");
                address.setLocality("");
                address.setPostcode("");
                address.setRegion("");
                address.setStreetAddress("");

                schemeInfo.setAddress(address);

                // TODO Contact info

                break;

            case DUNS_AND_BRADSTREET:
                // TODO Duns data
                break;

            case CHARITIES_COMMISSION:
                // TODO Charity data
                break;

            case NORTHERN_ISLAND_CHARITY:
                // TODO Charity data
                break;

            case SCOTLAND_CHARITY:
                // TODO Charity data
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + schemeRegistry);
        }
        return schemeInfo;
    }
}
