package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.*;
import com.ccs.conclave.api.common.BaseClass;

import java.util.ArrayList;
import java.util.List;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.*;

public class OrgDataProvider extends BaseClass {

    public static SchemeInfo getInfo(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = new SchemeInfo();
        Identifier identifier = new Identifier();
        Identifier additionalIdentifier1 = new Identifier();
        Identifier additionalIdentifier2 = new Identifier();
        Identifier additionalIdentifier3 = new Identifier();
        Identifier additionalIdentifier4 = new Identifier();
        Identifier additionalIdentifier5 = new Identifier();
        Identifier additionalIdentifier6 = new Identifier();
        Identifier additionalIdentifier7 = new Identifier();
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
                address.setStreetAddress("Upper Floor, The Granary, Stanley Grange Ormskirk Road, Knowsley");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case DUN_AND_BRADSTREET:
                schemeInfo.setName("West Suffolk College");
                identifier.setId("229841838");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("West Suffolk College");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // No additionalIdentifier

                address.setCountryName("United Kingdom");
                address.setLocality("BURY ST. EDMUNDS");
                address.setPostalCode("IP33 3RL");
                address.setRegion("");
                address.setStreetAddress("Out Risbygate");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case DUN_AND_BRADSTREET_WALES:
                schemeInfo.setName("HMPS SHARED SERVICES");
                identifier.setId("210019203");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("HMPS SHARED SERVICES");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // No additionalIdentifier

                address.setCountryName("United Kingdom");
                address.setLocality("NEWPORT");
                address.setPostalCode("NP10 8FZ");
                address.setRegion("");
                address.setStreetAddress("M.o.j Shared Service Centre, Phoenix House, Celtic Springs Business Park");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case DUN_AND_BRADSTREET_NI:
                schemeInfo.setName("Northern Ireland Housing Executive");
                identifier.setId("218124716");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("Northern Ireland Housing Executive");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // No additionalIdentifier

                address.setCountryName("United Kingdom");
                address.setLocality("BELFAST");
                address.setPostalCode("BT1 1FE");
                address.setRegion("");
                address.setStreetAddress("Spencer House, 71 Royal Avenue");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case DUN_AND_BRADSTREET_SCOTLAND:
                schemeInfo.setName("Glasgow City Council");
                identifier.setId("214926136");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("Glasgow City Council");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // No additionalIdentifier

                address.setCountryName("United Kingdom");
                address.setLocality("GLASGOW");
                address.setPostalCode("G31 5LP");
                address.setRegion("");
                address.setStreetAddress("Caroline Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case DUN_AND_BRADSTREET_IRELAND:
                schemeInfo.setName("First Data International");
                identifier.setId("218082698");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("First Data International");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // No additionalIdentifier

                address.setCountryName("United Kingdom");
                address.setLocality("ROTHERHAM");
                address.setPostalCode("S66 8RY");
                address.setRegion("");
                address.setStreetAddress("Lowton Way");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;


            case DUN_AND_BRADSTREET_WITH_COH:
                schemeInfo.setName("BIOS HEALTH LTD");
                identifier.setId("220857124");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET_WITH_COH));
                identifier.setLegalName("BIOS HEALTH LTD");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("09575301");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("BIOS HEALTH LTD");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("United Kingdom");
                address.setLocality("CAMBRIDGE");
                address.setPostalCode("CB2 1NN");
                address.setRegion("");
                address.setStreetAddress("8 Bateman Mews");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
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
                additionalIdentifier1.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=1060403&subId=0");
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
                address.setStreetAddress("Lymm Road, Thelwall");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION:
                schemeInfo.setName("1 SIGNAL REGIMENT PRI FUND");
                identifier.setId("1183905");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                identifier.setLegalName("1 SIGNAL REGIMENT PRI FUND");
                identifier.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=1183905&subId=0");
                schemeInfo.setIdentifier(identifier);

                address.setCountryName("");
                address.setLocality("Beaconside");
                address.setPostalCode("ST18 0AQ");
                address.setRegion("Stafford");
                address.setStreetAddress("Ministry of Defence, Beacon Barracks");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("NotEmpty");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("NotEmpty");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION_WITH_TWO_COH:
                schemeInfo.setName("THE CEDAR CENTRE");
                identifier.setId("802955");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_TWO_COH));
                identifier.setLegalName("THE CEDAR CENTRE");
                identifier.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=802955&subId=0");
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
                address.setCountryName("");
                address.setLocality("17 Arden Crescent");
                address.setPostalCode("E14 9WA");
                address.setRegion("LONDON");
                address.setStreetAddress("CEDAR CENTRE, THE CEDAR CENTRE");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("NotEmpty");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION_WITH_SC:
                schemeInfo.setName("MOUNTAINS ANIMAL SANCTUARY");
                identifier.setId("290356");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_SC));
                identifier.setLegalName("MOUNTAINS ANIMAL SANCTUARY");
                identifier.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=290356&subId=0");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                additionalIdentifier1.setId("SC037536");
                additionalIdentifier1.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC037536");
                additionalIdentifier1.setLegalName("Mountains Animal Sanctuary");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);
                address.setCountryName("");
                address.setLocality("");
                address.setPostalCode("");
                address.setRegion("");
                address.setStreetAddress("");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION_WITH_COH:
                schemeInfo.setName("47 CHARITY");
                identifier.setId("1168304");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_COH));
                identifier.setLegalName("47 CHARITY");
                identifier.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=1168304&subId=0");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("CE007062");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("47 CHARITY");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("");
                address.setLocality("READING");
                address.setPostalCode("RG5 4QS");
                address.setRegion("");
                address.setStreetAddress("63 WELFORD ROAD, WOODLEY");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("secretary@47charity.org");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("0118 944 2554");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS:
                schemeInfo.setName("ACTION FOR BLIND PEOPLE");
                identifier.setId("205913");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS));
                identifier.setLegalName("ACTION FOR BLIND PEOPLE");
                identifier.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=205913&subId=0");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier1.setId("226227");
                additionalIdentifier1.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=226227&subId=0");
                additionalIdentifier1.setLegalName("THE ROYAL NATIONAL INSTITUTE OF BLIND PEOPLE");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier2.setId("00026688");
                additionalIdentifier2.setUri("");
                additionalIdentifier2.setLegalName("ACTION FOR BLIND PEOPLE");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier3.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier3.setId("RC000500");
                additionalIdentifier3.setUri("");
                additionalIdentifier3.setLegalName("ROYAL NATIONAL INSTITUTE OF BLIND PEOPLE");
                additionalIdentifiers.add(additionalIdentifier3);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier4.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                additionalIdentifier4.setId("SC039316");
                additionalIdentifier4.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC039316");
                additionalIdentifier4.setLegalName("Royal National Institute of Blind People");
                additionalIdentifiers.add(additionalIdentifier4);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier5.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                additionalIdentifier5.setId("SC040050");
                additionalIdentifier5.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC040050");
                additionalIdentifier5.setLegalName("Action for Blind People");
                additionalIdentifiers.add(additionalIdentifier5);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("");
                address.setLocality("105 JUDD STREET");
                address.setPostalCode("WC1H 9NE");
                address.setRegion("LONDON");
                address.setStreetAddress("ROYAL NATIONAL INSTITUTE FOR THE, BLIND");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("NotEmpty");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("NotEmpty");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case NORTHERN_CHARITY_WITH_COH:
                schemeInfo.setName("Craigavon Christian Youth");
                identifier.setId("NIC107049");
                identifier.setScheme(SchemeRegistry.getSchemeCode(NORTHERN_CHARITY_WITH_COH));
                identifier.setLegalName("Craigavon Christian Youth");
                identifier.setUri("http://www.charitycommissionni.org.uk/charity-details/?regid=107049&subid=0");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("NI603345");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("CRAIGAVON CHRISTIAN YOUTH");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("Northern Ireland");
                address.setLocality("Lurgan");
                address.setPostalCode("BT66 6LJ");
                address.setRegion("Craigavon");
                address.setStreetAddress("75 Kilvergan Road");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("NotEmpty");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("NotEmpty");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case NORTHERN_CHARITY:
                schemeInfo.setName("Lifereach Ni");
                identifier.setId("103185");
                identifier.setScheme(SchemeRegistry.getSchemeCode(NORTHERN_CHARITY));
                identifier.setLegalName("Lifereach Ni");
                identifier.setUri("http://www.charitycommissionni.org.uk/charity-details/?regid=103185&subid=0");
                schemeInfo.setIdentifier(identifier);

                address.setCountryName("Northern Ireland");
                address.setLocality("Belfast");
                address.setPostalCode("BT2 7JD");
                address.setRegion("");
                address.setStreetAddress("11A Bruce Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("NotEmpty");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("NotEmpty");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case SCOTLAND_CHARITY:
                schemeInfo.setName("11th Edinburgh North East Scout Group");
                identifier.setId("SC045754");
                identifier.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                identifier.setLegalName("11th Edinburgh North East Scout Group");
                identifier.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC045754");
                schemeInfo.setIdentifier(identifier);

                address.setCountryName("Scotland");
                address.setLocality("Edinburgh");
                address.setPostalCode("EH7 5AL");
                address.setRegion("");
                address.setStreetAddress("8 Allanfield Place");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;


            case SCOTLAND_CHARITY_WITH_CHC_COH:
                schemeInfo.setName("Acorn Christian Foundation");
                identifier.setId("SC042491");
                identifier.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY_WITH_CHC_COH));
                identifier.setLegalName("Acorn Christian Foundation");
                identifier.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC042491");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier1.setId("1080011");
                additionalIdentifier1.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=1080011&subId=0");
                additionalIdentifier1.setLegalName("ACORN CHRISTIAN FOUNDATION");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier2.setId("03851139");
                additionalIdentifier2.setUri("");
                additionalIdentifier2.setLegalName("ACORN CHRISTIAN FOUNDATION");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("Scotland");
                address.setLocality("Camberley");
                address.setPostalCode("GU15 1EJ");
                address.setRegion("Surrey");
                address.setStreetAddress("124 Upper Chobham Road Camberley Surrey");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case INVALID_SCHEME:
                schemeInfo.setName("Invalid Name");
                identifier.setId("XYZ603366");
                identifier.setScheme(SchemeRegistry.getSchemeCode(INVALID_SCHEME));
                identifier.setLegalName("Invalid Name");
                identifier.setUri("http://www.acormchristian.org");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(INVALID_SCHEME));
                additionalIdentifier1.setId("XYZ603355");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("Invalid Name");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + schemeRegistry);
        }


        // Delete Database entry if the Org. is already registered
        deleteOrganisationWithIdTestEndPt(schemeInfo.getIdentifier().getId());
        for (Identifier id : schemeInfo.getAdditionalIdentifiers()) {
            deleteOrganisationWithIdTestEndPt(id.getId());
        }

        return schemeInfo;
    }

    // remove additional identifiers from test data to perform Update scheme tests
    public static SchemeInfo getInfoWithoutAddIdentifiers(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = getInfo(schemeRegistry);

        SchemeInfo schemeInfoModified = new SchemeInfo();
        schemeInfoModified.setName(schemeInfo.getName());
        schemeInfoModified.setIdentifier(schemeInfo.getIdentifier());
        schemeInfoModified.setContactPoint(schemeInfo.getContactPoint());
        schemeInfoModified.setAddress(schemeInfo.getAddress());
        return schemeInfoModified;
    }

    // This method returns only AdditionalSchemesInfo
    public static List<AdditionalSchemeInfo> getAdditionalIdentifierInfo(SchemeRegistry schemeRegistry) {
        List<AdditionalSchemeInfo> additionalSchemesInfo = new ArrayList<>();
        SchemeInfo schemeInfo = getInfo(schemeRegistry);
        for (Identifier addIdentifier : schemeInfo.getAdditionalIdentifiers()) {
            AdditionalSchemeInfo additionalSchemeInfo = new AdditionalSchemeInfo();
            additionalSchemeInfo.setIdentifier(addIdentifier);
            additionalSchemesInfo.add(additionalSchemeInfo);
        }
        return additionalSchemesInfo;
    }
}