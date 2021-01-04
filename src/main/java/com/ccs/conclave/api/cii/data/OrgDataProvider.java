package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.AdditionalIdentifiers;
import com.ccs.conclave.api.cii.pojo.Address;
import com.ccs.conclave.api.cii.pojo.Identifier;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import com.ccs.conclave.api.common.SchemeRegistry;

import static com.ccs.conclave.api.common.SchemeRegistry.*;

public class OrgDataProvider {

    public static SchemeInfo getInfo(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = new SchemeInfo();
        Identifier identifier = new Identifier();
        AdditionalIdentifiers additionalIdentifiers = new AdditionalIdentifiers();
        Identifier additionalIdentifier = new Identifier();
        Address address = new Address();
        switch (schemeRegistry) {
            case COMPANIES_HOUSE:
                schemeInfo.setName("BRITISH TELECOMMUNICATIONS PUBLIC LIMITED COMPANY");
                identifier.setId("1800000");
                identifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

//                additionalIdentifier.setId("");
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
                schemeInfo.setName("CHAIGELEY EDUCATIONAL FOUNDATION");
                identifier.setId("378509368");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUNS_AND_BRADSTREET));
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier.setId("1060403");
                additionalIdentifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
//                identifier.setUri("");
                additionalIdentifiers.setIdentifier(additionalIdentifier);

                address.setCountryName("United Kingdom");
                address.setLocality("WARRINGTON");
                address.setPostcode("WA4 2TE");
                address.setRegion("Europe");
                address.setStreetAddress("Chaigeley School Lymm Road Thelwall");

                schemeInfo.setAddress(address);

                break;

            case CHARITIES_COMMISSION:
                schemeInfo.setName("THE NATIONAL COUNCIL FOR VOLUNTARY ORGANISATIONS");
                identifier.setId("225922");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                identifier.setUri("http://www.ncvo.org.uk");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier.setId("GB-COH-00198344");
                additionalIdentifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
//                identifier.setUri("");
                additionalIdentifiers.setIdentifier(additionalIdentifier);

                address.setCountryName("United Kingdom");
                address.setLocality("Society Building");
                address.setPostcode("N1 9RL");
                address.setRegion("8 Regents Wharf");
                address.setStreetAddress("National Council For Voluntary, Organisations ");

                schemeInfo.setAddress(address);
                break;

            case NORTHERN_ISLAND_CHARITY:
                schemeInfo.setName("Supporting Communities Ni");
                identifier.setId("100005");
                identifier.setScheme(SchemeRegistry.getSchemeCode(NORTHERN_ISLAND_CHARITY));
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                address.setCountryName("");
                address.setLocality("");
                address.setPostcode("BT42 3AH");
                address.setRegion("");
                address.setStreetAddress("");

                schemeInfo.setAddress(address);
                break;

            case SCOTLAND_CHARITY:
                schemeInfo.setName("Abbeyfield Perth Society Ltd");
                identifier.setId("SC008993");
                identifier.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                address.setCountryName("Scotland");
                address.setLocality("25 Viewlands Road");
                address.setPostcode("PH1 1BL");
                address.setRegion("Perth");
                address.setStreetAddress("Viewlands House");

                schemeInfo.setAddress(address);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + schemeRegistry);
        }
        return schemeInfo;
    }
}
