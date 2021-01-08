package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.*;

import java.util.ArrayList;
import java.util.List;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;

public class OrgDataProvider {

    public static SchemeInfo getInfo(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = new SchemeInfo();
        Identifier identifier = new Identifier();
        Identifier additionalIdentifier = new Identifier();
        List<Identifier> additionalIdentifiers = new ArrayList<>();
        Address address = new Address();
        ContactPoint contactPoint = new ContactPoint();

        switch (schemeRegistry) {
            case COMPANIES_HOUSE:
                schemeInfo.setName("AI RECRUITMENT TECHNOLOGIES LIMITED");
                identifier.setId("07651107");
                identifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                identifier.setLegalName("AI RECRUITMENT TECHNOLOGIES LIMITED");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // No additionalIdentifier for COMPANIES_HOUSE

                address.setCountryName("England");
                address.setLocality("Prescot");
                address.setPostalCode("L34 4AT");
                address.setRegion("Merseyside");
                address.setStreetAddress("Upper Floor, The Granary, Stanley Grange Ormskirk Road");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUrl("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case DUN_AND_BRADSTREET:
                schemeInfo.setName("CHAIGELEY EDUCATIONAL FOUNDATION");
                identifier.setId("378509368");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // No additionalIdentifier

                address.setCountryName("United Kingdom");
                address.setLocality("WARRINGTON");
                address.setPostalCode("WA4 2TE");
                address.setRegion("");
                address.setStreetAddress("Lymm Road Thelwall");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUrl("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case DUN_AND_BRADSTREET_WITH_COH:
                schemeInfo.setName("CHAIN GANG LIMITED");
                identifier.setId("297663445");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET_WITH_COH));
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier.setId("02029405");
                additionalIdentifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier.setUri("");
                additionalIdentifier.setLegalName("CHAIN GANG LIMITED LIMITED");
                additionalIdentifiers.add(additionalIdentifier);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("United Kingdom");
                address.setLocality("LONDON");
                address.setPostalCode("SW11 3SX");
                address.setRegion("");
                address.setStreetAddress("Unit 4, River Reach Business Park 1 Gartons Way");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUrl("");
                schemeInfo.setContactPoint(contactPoint);
                break;


            case CHARITIES_COMMISSION:
                schemeInfo.setName("THE NATIONAL COUNCIL FOR VOLUNTARY ORGANISATIONS");
                identifier.setId("225922");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                identifier.setUri("http://www.ncvo.org.uk");
                schemeInfo.setIdentifier(identifier);

//                additionalIdentifier.setId("GB-COH-00198344");
//                additionalIdentifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
////                identifier.setUri("");
//                additionalIdentifiers.setIdentifier(additionalIdentifier);

                address.setCountryName("United Kingdom");
                address.setLocality("Society Building");
                address.setPostalCode("N1 9RL");
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
                address.setPostalCode("BT42 3AH");
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
                address.setPostalCode("PH1 1BL");
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
