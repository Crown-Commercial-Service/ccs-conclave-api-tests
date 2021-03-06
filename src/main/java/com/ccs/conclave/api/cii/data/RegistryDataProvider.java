package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.*;
import com.ccs.conclave.api.cii.requests.RequestTestEndpoints;

import java.util.ArrayList;
import java.util.List;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.deleteOrganisation;

public class RegistryDataProvider {

    public SchemeInfo getExpectedSchemeInfo(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = new SchemeInfo();
        Identifier identifier = new Identifier();
        Identifier additionalIdentifier1 = new Identifier();
        Identifier additionalIdentifier2 = new Identifier();
        Identifier additionalIdentifier3 = new Identifier();
        Identifier additionalIdentifier4 = new Identifier();
        Identifier additionalIdentifier5 = new Identifier();
        List<Identifier> additionalIdentifiers = new ArrayList<>();
        Address address = new Address();
        ContactPoint contactPoint = new ContactPoint();
        switch (schemeRegistry) {
            case COMPANIES_HOUSE:
                schemeInfo.setName("CIRCLE HEALTH LIMITED");
                identifier.setId("05042771");
                identifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                identifier.setLegalName("CIRCLE HEALTH LIMITED");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // Salesforce identifier
                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier1.setId("001b000003V2vepAAB~10052453");
                additionalIdentifier1.setUri("/services/data/v45.0/sobjects/Account/001b000003V2vepAAB");
                additionalIdentifier1.setLegalName("CIRCLE HEALTH LIMITED");
                additionalIdentifier1.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("England");
                address.setLocality("London");
                address.setPostalCode("EC4M 6XH");
                address.setRegion("");
                address.setStreetAddress("1st Floor 30 Cannon Street");
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

                // Salesforce identifier
                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier1.setId("0014J00000Ov486QAB~10590792");
                additionalIdentifier1.setUri("/services/data/v45.0/sobjects/Account/0014J00000Ov486QAB");
                additionalIdentifier1.setLegalName("West Suffolk College");
                additionalIdentifier1.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

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

            case DUN_AND_BRADSTREET_WITH_COH_WITH_DIFF_SF_ID:
                schemeInfo.setName("Placeholder-No real data available");
                identifier.setId("909123456");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET_WITH_COH_WITH_DIFF_SF_ID));
                identifier.setLegalName("");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);
                break;

            case DUN_AND_BRADSTREET_WALES:
                schemeInfo.setName("HMPS SHARED SERVICES");
                identifier.setId("210019203");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("HMPS SHARED SERVICES");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // Salesforce identifier
                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier1.setId("001b000003OCd9RAAT~10046325");
                additionalIdentifier1.setUri("/services/data/v45.0/sobjects/Account/001b000003OCd9RAAT");
                additionalIdentifier1.setLegalName("Home Office Immigration And Nationalisation");
                additionalIdentifier1.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

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

                // Salesforce identifier
                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier2.setId("0014J00000Ov47rQAB~10590777");
                additionalIdentifier2.setUri("/services/data/v45.0/sobjects/Account/0014J00000Ov47rQAB");
                additionalIdentifier2.setLegalName("BIOS HEALTH LTD");
                additionalIdentifier2.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier2);
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

            case DUN_AND_BRADSTREET_WITH_CHC_AND_COH:
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

                // Salesforce identifier
                additionalIdentifier3.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier3.setId("001b000003YNthvAAD~10121852");
                additionalIdentifier3.setUri("/services/data/v45.0/sobjects/Account/001b000003YNthvAAD");
                additionalIdentifier3.setLegalName("CHAIGELEY EDUCATIONAL FOUNDATION");
                additionalIdentifier3.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier3);
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

                // Salesforce identifier
                additionalIdentifier3.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier3.setId("0001b000003YNthvAAD~10121852");
                additionalIdentifier3.setUri("/services/data/v45.0/sobjects/Account/001b000003YNthvAAD");
                additionalIdentifier3.setLegalName("CHAIGELEY EDUCATIONAL FOUNDATION");
                additionalIdentifier3.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier3);
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
                schemeInfo.setName("ADVENTIST DEVELOPMENT AND RELIEF AGENCY - UK");
                identifier.setId("1074937");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                identifier.setLegalName("ADVENTIST DEVELOPMENT AND RELIEF AGENCY - UK");
                identifier.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=1074937&subId=0");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                additionalIdentifier1.setId("SC037726");
                additionalIdentifier1.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC037726");
                additionalIdentifier1.setLegalName("Adventist Development and Relief Agency – UK");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("Scotland");
                address.setLocality("Garston");
                address.setPostalCode("WD25 9JZ");
                address.setRegion("Watford, Herts");
                address.setStreetAddress("Stanborough Park");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("NotEmpty");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("NotEmpty");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION_WITH_COH_AND_SC:
                schemeInfo.setName("3SPACE");
                identifier.setId("1136377");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_COH_AND_SC));
                identifier.setLegalName("3SPACE");
                identifier.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=1136377&subId=0");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("07221470");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("3SPACE");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                additionalIdentifier2.setId("SC042130");
                additionalIdentifier2.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC042130");
                additionalIdentifier2.setLegalName("3Space");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                // No Salesforce identifier

                address.setCountryName("WOODFORD GREEN");
                address.setLocality("19-20 BOURNE COURT");
                address.setPostalCode("IG8 8HD");
                address.setRegion("SOUTHEND ROAD");
                address.setStreetAddress("3SPACE, C/O RAFFINGER STUART LTD");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("NotEmpty");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("NotEmpty");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;


            case CHARITIES_COMMISSION_WITH_COH:
                schemeInfo.setName("9 Lives Furniture");
                identifier.setId("1096086");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_COH));
                identifier.setLegalName("9 Lives Furniture");
                identifier.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=1096086&subId=0");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("04375649");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("9 LIVES FURNITURE");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                // No Salesforce identifier

                address.setCountryName("");
                address.setLocality("HERTFORDSHIRE");
                address.setPostalCode("WD3 1HA");
                address.setRegion("");
                address.setStreetAddress("25 WHARF LANE, RICKMANSWORTH");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("NotEmpty");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("NotEmpty");
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

                // No Salesforce identifier

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

            case SCOTLAND_CHARITY_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS:
                schemeInfo.setName("Action for Blind People");
                identifier.setId("SC040050");
                identifier.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS));
                identifier.setLegalName("Action for Blind People");
                identifier.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC040050");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier1.setId("205913");
                additionalIdentifier1.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=205913&subId=0");
                additionalIdentifier1.setLegalName("ACTION FOR BLIND PEOPLE");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier2.setId("226227");
                additionalIdentifier2.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=226227&subId=0");
                additionalIdentifier2.setLegalName("THE ROYAL NATIONAL INSTITUTE OF BLIND PEOPLE");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier3.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier3.setId("00026688");
                additionalIdentifier3.setUri("");
                additionalIdentifier3.setLegalName("ACTION FOR BLIND PEOPLE");
                additionalIdentifiers.add(additionalIdentifier3);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier4.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier4.setId("RC000500");
                additionalIdentifier4.setUri("");
                additionalIdentifier4.setLegalName("ROYAL NATIONAL INSTITUTE OF BLIND PEOPLE");
                additionalIdentifiers.add(additionalIdentifier4);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier5.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                additionalIdentifier5.setId("SC039316");
                additionalIdentifier5.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC039316");
                additionalIdentifier5.setLegalName("Royal National Institute of Blind People");
                additionalIdentifiers.add(additionalIdentifier5);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                // No Salesforce identifier

                address.setCountryName("Scotland");
                address.setLocality("105 Judd Street");
                address.setPostalCode("WC1H 9NE");
                address.setRegion("London");
                address.setStreetAddress("Action for Blind People");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
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

                // No Salesforce identifier

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
                identifier.setId("NIC103185");
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

                // Salesforce identifier
                additionalIdentifier3.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier3.setId("001b000003YNr2cAAD~10111645");
                additionalIdentifier3.setUri("/services/data/v45.0/sobjects/Account/001b000003YNr2cAAD");
                additionalIdentifier3.setLegalName("ACORN CHRISTIAN FOUNDATION");
                additionalIdentifier3.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier3);
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

            case SALES_FORCE:
                schemeInfo.setName("Sylvan Corporation"); // Salesforce is only for internal search
                identifier.setId("001b000003OCdIZAA1~10046880");
                identifier.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                identifier.setUri("/services/data/v45.0/sobjects/Account/001b000003OCdIZAA1");
                identifier.setLegalName("Sylvan Corporation");
                identifier.setHidden("true");
                schemeInfo.setIdentifier(identifier);
                break;

            case SFID_WITH_VALID_DUNS_INVALID_COH:
                // Sales force return COH but it is invalid - this data may not be available in future after SF data cleansing activity
                schemeInfo.setName("C.A. PETRIDES LIMITED");
                identifier.setId("114173941");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("C.A. PETRIDES LIMITED");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // Salesforce identifier
                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier1.setId("001b000003YNcxdAAD~10057668");
                additionalIdentifier1.setUri("/services/data/v45.0/sobjects/Account/001b000003YNcxdAAD");
                additionalIdentifier1.setLegalName("C.A. PETRIDES LIMITED");
                additionalIdentifier1.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("Cyprus");
                address.setLocality("Limassol");
                address.setPostalCode("3012");
                address.setRegion("");
                address.setStreetAddress("46 FRAGKLINOU ROUSVELT");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case SFID_WITH_VALID_DUNS_UNKNOWN_COH:
                // Sales force return COH as 'unknown' string
                schemeInfo.setName("GREENWOOD&BELL");
                identifier.setId("114173941");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("GREENWOOD&BELL");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // Salesforce identifier
                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier1.setId("001b000003WaB1fAAF~10055423");
                additionalIdentifier1.setUri("/services/data/v45.0/sobjects/Account/001b000003WaB1fAAF");
                additionalIdentifier1.setLegalName("GREENWOOD & BELL");
                additionalIdentifier1.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("United States");
                address.setLocality("NORWICH");
                address.setPostalCode("NR15 2JP");
                address.setRegion("");
                address.setStreetAddress("Provender Mill, The Street, Aslacton");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case SFID_WITH_VALID_COH_INVALID_DUNS:
                schemeInfo.setName("placeholder-no data available.");
                identifier.setId("");
                identifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                identifier.setLegalName("");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);
                break;

            case SFID_WITH_VALID_COH_UNKNOWN_DUNS:
                schemeInfo.setName("placeholder- no data available.");
                identifier.setId("");
                identifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                identifier.setLegalName("");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);
                break;

            case SFID_WITH_INVALID_COH_INVALID_DUNS:
                schemeInfo.setName("placeholder - no data available.");
                identifier.setId("");
                identifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                identifier.setLegalName("");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);
                break;

            case SFID_WITH_DUNS_CHC_NO_COH:
                // Sales force return DUNS and no COH: DUNs has COH in external registry
                schemeInfo.setName("THE SNOWDROP PROJECT");
                identifier.setId("222468678");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("THE SNOWDROP PROJECT");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier1.setId("1158856");
                additionalIdentifier1.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=1158856&subId=0");
                additionalIdentifier1.setLegalName("THE SNOWDROP PROJECT");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                // Salesforce identifier
                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier2.setId("001b000003YQRqSAAX~10540930");
                additionalIdentifier2.setUri("/services/data/v45.0/sobjects/Account/001b000003YQRqSAAX");
                additionalIdentifier2.setLegalName("The Snowdrop Project");
                additionalIdentifier2.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("United States");
                address.setLocality("SHEFFIELD");
                address.setPostalCode("S3 8LT");
                address.setRegion("");
                address.setStreetAddress("7 Castle Street the Snowdrop Project Castle Green");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + schemeRegistry);
        }

        String ccsOrgId = RequestTestEndpoints.getRegisteredOrgId(schemeInfo.getIdentifier().getId());
        // Delete Database entry if the Org. is already registered
        deleteOrganisation(ccsOrgId);
        for (Identifier id : schemeInfo.getAdditionalIdentifiers()) {
            ccsOrgId = RequestTestEndpoints.getRegisteredOrgId(id.getId());
            deleteOrganisation(ccsOrgId);
        }

        return schemeInfo;
    }
}