package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.*;

import java.util.ArrayList;
import java.util.List;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;

public class OrgDataProvider {

    public static SchemeInfo getInfo(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = new SchemeInfo();
        Identifier identifier = new Identifier();
        Identifier additionalIdentifier1 = new Identifier();
        Identifier additionalIdentifier2 = new Identifier();
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
                schemeInfo.setName("Thus Group Holdings Ltd");
                identifier.setId("211221260");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("Thus Group Holdings Ltd");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // No additionalIdentifier

                address.setCountryName("United Kingdom");
                address.setLocality("LONDON");
                address.setPostalCode("EC1Y 8LZ");
                address.setRegion("");
                address.setStreetAddress("Finsbury Tower, 103-105 Bunhill Row");
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
                identifier.setLegalName("CHAIN GANG LIMITED");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("02029405");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("CHAIN GANG LIMITED");
                additionalIdentifiers.add(additionalIdentifier1);
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

            case DUN_AND_BRADSTREET_WITH_COH_AND_CHC:
                schemeInfo.setName("CHAIGELEY EDUCATIONAL FOUNDATION");
                identifier.setId("378509368");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET_WITH_COH));
                identifier.setLegalName("CHAIGELEY EDUCATIONAL FOUNDATION");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier1.setId("1060403");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("CHAIGELEY EDUCATIONAL FOUNDATION");
                additionalIdentifiers.add(additionalIdentifier1);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier2.setId("03301881");
                additionalIdentifier2.setUri("");
                additionalIdentifier2.setLegalName("CHAIGELEY EDUCATIONAL FOUNDATION");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

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


            case CHARITIES_COMMISSION:
                schemeInfo.setName("THE NATIONAL COUNCIL FOR VOLUNTARY ORGANISATIONS");
                identifier.setId("225922");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                identifier.setLegalName("THE NATIONAL COUNCIL FOR VOLUNTARY ORGANISATIONS");
                identifier.setUri("http://www.ncvo.org.uk");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("00198344");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("NATIONAL COUNCIL FOR VOLUNTARY ORGANISATIONS(THE)");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("UK");
                address.setLocality("Society Building");
                address.setPostalCode("N1 9RL");
                address.setRegion("8 Regents Wharf");
                address.setStreetAddress("National Council For Voluntary, Organisations");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("david.hauk@ncvo.org.uk");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("02075202538");
                contactPoint.setUrl(""); // Bug CON-450
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION_WITH_ADD_IDENTIFIER:
                schemeInfo.setName("THE CEDAR CENTRE");
                identifier.setId("802955");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_ADD_IDENTIFIER));
                identifier.setLegalName("THE CEDAR CENTRE");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("02432836");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("THE CEDAR CENTRE");
                additionalIdentifiers.add(additionalIdentifier1);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier2.setId("CS003009");
                additionalIdentifier2.setUri("");
                additionalIdentifier2.setLegalName("THE CEDAR CENTRE");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("UK");
                address.setLocality("17 Arden Crescent");
                address.setPostalCode("E14 9WA");
                address.setRegion("LONDON");
                address.setStreetAddress("CEDAR CENTRE, THE CEDAR CENTRE");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("02077909085");
                contactPoint.setUrl("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case NORTHERN_ISLAND_CHARITY:
                schemeInfo.setName("Craigavon Christian Youth");
                identifier.setId("107049");
                identifier.setScheme(SchemeRegistry.getSchemeCode(NORTHERN_ISLAND_CHARITY));
                identifier.setLegalName("Craigavon Christian Youth");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("NI603345");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("CRAIGAVON CHRISTIAN YOUTH");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("UK");
                address.setLocality("Lurgan");
                address.setPostalCode("BT66 6LJ");
                address.setRegion("Craigavon");
                address.setStreetAddress("75 Kilvergan Road");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("ccyclubs@hotmail.com");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("07753483295");
                contactPoint.setUrl("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case SCOTLAND_CHARITY:
                schemeInfo.setName("Acorn Christian Foundation");
                identifier.setId("SC042491");
                identifier.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                identifier.setLegalName("Acorn Christian Foundation");
                identifier.setUri("http://www.acormchristian.org");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("03851139");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("Acorn Christian Foundation");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier2.setId("1080011");
                additionalIdentifier2.setUri("");
                additionalIdentifier2.setLegalName("Acorn Christian Foundation");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("UK");
                address.setLocality("Camberley");
                address.setPostalCode("GU15 1EJ");
                address.setRegion("Surrey");
                address.setStreetAddress("124 Upper Chobham Road");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUrl("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + schemeRegistry);
        }
        return schemeInfo;
    }
}
