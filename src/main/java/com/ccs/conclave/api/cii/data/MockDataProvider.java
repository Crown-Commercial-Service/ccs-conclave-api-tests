package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.*;
import com.ccs.conclave.api.common.BaseClass;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;
import static com.ccs.conclave.api.cii.requests.RestRequests.deleteOrganisationWithIdTestEndPt;

public class MockDataProvider {

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
                schemeInfo.setName("Company Example 07612345");
                identifier.setId("07612345");
                identifier.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                identifier.setLegalName("AI RECRUITMENT TECHNOLOGIES LIMITED");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // Salesforce identifier
                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier1.setId("NSO7IUSHF98HFP9WEH9FQZ~56734565467");
                additionalIdentifier1.setUri("/services/data/v46.0/subjects/Accout/NSO7IUSHF98HFP9WEH9FQZ");
                additionalIdentifier1.setLegalName("Dummy organisation");
                additionalIdentifier1.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("Country");
                address.setLocality("Locality");
                address.setPostalCode("A1 2BC");
                address.setRegion("Region");
                address.setStreetAddress("123 Example Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case DUN_AND_BRADSTREET:
                schemeInfo.setName("Company Example 404123456");
                identifier.setId("404123456");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("Company Example 404123456");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // Salesforce identifier
                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier1.setId("NSO7IUSHF98HFP9WEH9FNQ~56734565467");
                additionalIdentifier1.setUri("/services/data/v46.0/subjects/Accout/NSO7IUSHF98HFP9WEH9FNQ2");
                additionalIdentifier1.setLegalName("Dummy organisation");
                additionalIdentifier1.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("Country");
                address.setLocality("Locality");
                address.setPostalCode("AB1C 2DE");
                address.setRegion("");
                address.setStreetAddress("123 Fake Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case DUN_AND_BRADSTREET_WALES:
                schemeInfo.setName("Company Example 404123456");
                identifier.setId("404123456");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET));
                identifier.setLegalName("Company Example 404123456");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                // Salesforce identifier
                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier1.setId("NSO7IUSHF98HFP9WEH9FNQ~56734565467");
                additionalIdentifier1.setUri("/services/data/v46.0/subjects/Accout/NSO7IUSHF98HFP9WEH9FNQ2");
                additionalIdentifier1.setLegalName("Dummy organisation");
                additionalIdentifier1.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("Country");
                address.setLocality("Locality");
                address.setPostalCode("AB1C 2DE");
                address.setRegion("");
                address.setStreetAddress("123 Fake Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

             case DUN_AND_BRADSTREET_WITH_COH:
                schemeInfo.setName("Company Example 505123456");
                identifier.setId("505123456");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET_WITH_COH));
                identifier.setLegalName("Company Example 505123456");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("09012345");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("Company Example 09012345");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                // Salesforce identifier
                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
                additionalIdentifier2.setId("NSO7IUSHF98HFP9WEH9FHE~56734565478");
                additionalIdentifier2.setUri("/services/data/v46.0/subjects/Accout/NSO7IUSHF98HFP9WEH9FHE");
                additionalIdentifier2.setLegalName("Dummy organisation");
                additionalIdentifier2.setHidden("true");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("United Kingdom");
                address.setLocality("Locality");
                address.setPostalCode("AB1C 2DE");
                address.setRegion("");
                address.setStreetAddress("123 Fake Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case DUN_AND_BRADSTREET_WITH_CHC_AND_COH:
                schemeInfo.setName("Company Example 606123456");
                identifier.setId("378509368");
                identifier.setScheme(SchemeRegistry.getSchemeCode(DUN_AND_BRADSTREET_WITH_COH));
                identifier.setLegalName("Company Example 606123456");
                identifier.setUri("");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier1.setId("101123");
                additionalIdentifier1.setUri("http://www.example.org.uk");
                additionalIdentifier1.setLegalName("Charity Example 101123");
                additionalIdentifiers.add(additionalIdentifier1);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier2.setId("06012345");
                additionalIdentifier2.setUri("");
                additionalIdentifier2.setLegalName("Company Example 06012345");
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

                address.setCountryName("Country");
                address.setLocality("Locality");
                address.setPostalCode("AB1C 2DE");
                address.setRegion("");
                address.setStreetAddress("123 Fake Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION:
                schemeInfo.setName("Charity Example 202123");
                identifier.setId("202123");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                identifier.setLegalName("Charity Example 202123");
                identifier.setUri("http://www.example.org.uk");
                schemeInfo.setIdentifier(identifier);

                address.setCountryName("");
                address.setLocality("Locality");
                address.setPostalCode("A1 2BC");
                address.setRegion("Stafford");
                address.setStreetAddress("123 Example Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("example@email.com");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("07123456789");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION_WITH_TWO_COH:
                schemeInfo.setName("Charity Example 303123");
                identifier.setId("303123");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_TWO_COH));
                identifier.setLegalName("Charity Example 303123");
                identifier.setUri("http://www.example.org.uk");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("04012345");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("Company Example 04012345");
                additionalIdentifiers.add(additionalIdentifier1);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier2.setId("07012345");
                additionalIdentifier2.setUri("");
                additionalIdentifier2.setLegalName("Company Example 07012345");
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
                address.setLocality("Locality");
                address.setPostalCode("A1 2BC");
                address.setRegion("Test Region");
                address.setStreetAddress("123 Example Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("07123456789");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION_WITH_SC:
                schemeInfo.setName("Charity Example 404123");
                identifier.setId("404123");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                identifier.setLegalName("Charity Example 404123");
                identifier.setUri("http://www.example.org.uk");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                additionalIdentifier1.setId("SC012345");
                additionalIdentifier1.setUri("http://www.example.org.uk");
                additionalIdentifier1.setLegalName("Charity Example SC012345");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                address.setCountryName("");
                address.setLocality("Locality");
                address.setPostalCode("A1 2BC");
                address.setRegion("Test Region");
                address.setStreetAddress("123 Example Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("example@email.com");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("07123456789");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION_WITH_COH_AND_SC:
                schemeInfo.setName("Charity Example 505123");
                identifier.setId("505123");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_COH_AND_SC));
                identifier.setLegalName("Charity Example 505123");
                identifier.setUri("http://www.example.org.uk");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("08012345");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("Company Example 08012345");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
                additionalIdentifier2.setId("SC022345");
                additionalIdentifier2.setUri("http://www.example.org.uk");
                additionalIdentifier2.setLegalName("Charity Example SC022345");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                // No Salesforce identifier

                address.setCountryName("");
                address.setLocality("Locality");
                address.setPostalCode("A1 2BC");
                address.setRegion("Test Region");
                address.setStreetAddress("123 Example Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("example@email.com");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("07123456789");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;


            case CHARITIES_COMMISSION_WITH_COH:
                schemeInfo.setName("Charity Example 606123");
                identifier.setId("606123");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_COH));
                identifier.setLegalName("Charity Example 606123");
                identifier.setUri("http://www.example.org.uk");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier1.setId("03012345");
                additionalIdentifier1.setUri("");
                additionalIdentifier1.setLegalName("Company Example 03012345");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                // No Salesforce identifier

                address.setCountryName("");
                address.setLocality("Locality");
                address.setPostalCode("A1 2BC");
                address.setRegion("Test Region");
                address.setStreetAddress("123 Example Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("example@email.com");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("07123456789");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case CHARITIES_COMMISSION_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS:
                schemeInfo.setName("Charity Example 909123");
                identifier.setId("909123");
                identifier.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS));
                identifier.setLegalName("Charity Example 909123");
                identifier.setUri("http://www.example.org.uk");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier1.setId("1012345");
                additionalIdentifier1.setUri("http://www.example.org.uk");
                additionalIdentifier1.setLegalName("Charity Example 1012345");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier2.setId("RC000123");
                additionalIdentifier2.setUri("");
                additionalIdentifier2.setLegalName("Company Example RC000123");
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
                address.setLocality("Locality");
                address.setPostalCode("A1 2BC");
                address.setRegion("Test Region");
                address.setStreetAddress("123 Example Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("example@email.com");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("07123456789");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

            case SCOTLAND_CHARITY_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS:
                schemeInfo.setName("Charity Example SC032345");
                identifier.setId("SC032345");
                identifier.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS));
                identifier.setLegalName("Charity Example SC032345");
                identifier.setUri("http://www.example.org.uk");
                schemeInfo.setIdentifier(identifier);

                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier1.setId("909123");
                additionalIdentifier1.setUri("http://www.example.org.uk");
                additionalIdentifier1.setLegalName("Charity Example 909123");
                additionalIdentifiers.add(additionalIdentifier1);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
                additionalIdentifier2.setId("1012345");
                additionalIdentifier2.setUri("http://www.example.org.uk");
                additionalIdentifier2.setLegalName("Charity Example 1012345");
                additionalIdentifiers.add(additionalIdentifier2);
                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);

                additionalIdentifier3.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
                additionalIdentifier3.setId("RC000123");
                additionalIdentifier3.setUri("");
                additionalIdentifier3.setLegalName("Company Example RC000123");
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

                address.setCountryName("");
                address.setLocality("Locality");
                address.setPostalCode("A1 2BC");
                address.setRegion("Test Region");
                address.setStreetAddress("123 Example Street");
                schemeInfo.setAddress(address);

                contactPoint.setName("");
                contactPoint.setEmail("");
                contactPoint.setFaxNumber("");
                contactPoint.setTelephone("");
                contactPoint.setUri("");
                schemeInfo.setContactPoint(contactPoint);
                break;

//            case NORTHERN_CHARITY_WITH_COH:
//                schemeInfo.setName("Craigavon Christian Youth");
//                identifier.setId("NIC107049");
//                identifier.setScheme(SchemeRegistry.getSchemeCode(NORTHERN_CHARITY_WITH_COH));
//                identifier.setLegalName("Craigavon Christian Youth");
//                identifier.setUri("http://www.charitycommissionni.org.uk/charity-details/?regid=107049&subid=0");
//                schemeInfo.setIdentifier(identifier);
//
//                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
//                additionalIdentifier1.setId("NI603345");
//                additionalIdentifier1.setUri("");
//                additionalIdentifier1.setLegalName("CRAIGAVON CHRISTIAN YOUTH");
//                additionalIdentifiers.add(additionalIdentifier1);
//                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);
//
//                // No Salesforce identifier
//
//                address.setCountryName("Northern Ireland");
//                address.setLocality("Lurgan");
//                address.setPostalCode("BT66 6LJ");
//                address.setRegion("Craigavon");
//                address.setStreetAddress("75 Kilvergan Road");
//                schemeInfo.setAddress(address);
//
//                contactPoint.setName("");
//                contactPoint.setEmail("NotEmpty");
//                contactPoint.setFaxNumber("");
//                contactPoint.setTelephone("NotEmpty");
//                contactPoint.setUri("");
//                schemeInfo.setContactPoint(contactPoint);
//                break;
//
//            case NORTHERN_CHARITY:
//                schemeInfo.setName("Lifereach Ni");
//                identifier.setId("103185");
//                identifier.setScheme(SchemeRegistry.getSchemeCode(NORTHERN_CHARITY));
//                identifier.setLegalName("Lifereach Ni");
//                identifier.setUri("http://www.charitycommissionni.org.uk/charity-details/?regid=103185&subid=0");
//                schemeInfo.setIdentifier(identifier);
//
//                address.setCountryName("Northern Ireland");
//                address.setLocality("Belfast");
//                address.setPostalCode("BT2 7JD");
//                address.setRegion("");
//                address.setStreetAddress("11A Bruce Street");
//                schemeInfo.setAddress(address);
//
//                contactPoint.setName("");
//                contactPoint.setEmail("NotEmpty");
//                contactPoint.setFaxNumber("");
//                contactPoint.setTelephone("NotEmpty");
//                contactPoint.setUri("");
//                schemeInfo.setContactPoint(contactPoint);
//                break;
//
//            case SCOTLAND_CHARITY:
//                schemeInfo.setName("11th Edinburgh North East Scout Group");
//                identifier.setId("SC045754");
//                identifier.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY));
//                identifier.setLegalName("11th Edinburgh North East Scout Group");
//                identifier.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC045754");
//                schemeInfo.setIdentifier(identifier);
//
//                address.setCountryName("Scotland");
//                address.setLocality("Edinburgh");
//                address.setPostalCode("EH7 5AL");
//                address.setRegion("");
//                address.setStreetAddress("8 Allanfield Place");
//                schemeInfo.setAddress(address);
//
//                contactPoint.setName("");
//                contactPoint.setEmail("");
//                contactPoint.setFaxNumber("");
//                contactPoint.setTelephone("");
//                contactPoint.setUri("");
//                schemeInfo.setContactPoint(contactPoint);
//                break;
//
//
//            case SCOTLAND_CHARITY_WITH_CHC_COH:
//                schemeInfo.setName("Acorn Christian Foundation");
//                identifier.setId("SC042491");
//                identifier.setScheme(SchemeRegistry.getSchemeCode(SCOTLAND_CHARITY_WITH_CHC_COH));
//                identifier.setLegalName("Acorn Christian Foundation");
//                identifier.setUri("https://www.oscr.org.uk/about-charities/search-the-register/charity-details?number=SC042491");
//                schemeInfo.setIdentifier(identifier);
//
//                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(CHARITIES_COMMISSION));
//                additionalIdentifier1.setId("1080011");
//                additionalIdentifier1.setUri("https://register-of-charities.charitycommission.gov.uk/charity-details/?regId=1080011&subId=0");
//                additionalIdentifier1.setLegalName("ACORN CHRISTIAN FOUNDATION");
//                additionalIdentifiers.add(additionalIdentifier1);
//                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);
//
//                additionalIdentifier2.setScheme(SchemeRegistry.getSchemeCode(COMPANIES_HOUSE));
//                additionalIdentifier2.setId("03851139");
//                additionalIdentifier2.setUri("");
//                additionalIdentifier2.setLegalName("ACORN CHRISTIAN FOUNDATION");
//                additionalIdentifiers.add(additionalIdentifier2);
//                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);
//
//                // Salesforce identifier
//                additionalIdentifier3.setScheme(SchemeRegistry.getSchemeCode(SALES_FORCE));
//                additionalIdentifier3.setId("001b000003YNr2cAAD~10111645");
//                additionalIdentifier3.setUri("/services/data/v45.0/sobjects/Account/001b000003YNr2cAAD");
//                additionalIdentifier3.setLegalName("ACORN CHRISTIAN FOUNDATION");
//                additionalIdentifier3.setHidden("true");
//                additionalIdentifiers.add(additionalIdentifier3);
//                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);
//
//                address.setCountryName("Scotland");
//                address.setLocality("Camberley");
//                address.setPostalCode("GU15 1EJ");
//                address.setRegion("Surrey");
//                address.setStreetAddress("124 Upper Chobham Road Camberley Surrey");
//                schemeInfo.setAddress(address);
//
//                contactPoint.setName("");
//                contactPoint.setEmail("");
//                contactPoint.setFaxNumber("");
//                contactPoint.setTelephone("");
//                contactPoint.setUri("");
//                schemeInfo.setContactPoint(contactPoint);
//                break;
//
//            case INVALID_SCHEME:
//                schemeInfo.setName("Invalid Name");
//                identifier.setId("XYZ603366");
//                identifier.setScheme(SchemeRegistry.getSchemeCode(INVALID_SCHEME));
//                identifier.setLegalName("Invalid Name");
//                identifier.setUri("http://www.acormchristian.org");
//                schemeInfo.setIdentifier(identifier);
//
//                additionalIdentifier1.setScheme(SchemeRegistry.getSchemeCode(INVALID_SCHEME));
//                additionalIdentifier1.setId("XYZ603355");
//                additionalIdentifier1.setUri("");
//                additionalIdentifier1.setLegalName("Invalid Name");
//                additionalIdentifiers.add(additionalIdentifier1);
//                schemeInfo.setAdditionalIdentifiers(additionalIdentifiers);
//                break;
//
//            case SALES_FORCE:
//                schemeInfo.setName("Dummy data"); // Salesforce is only for internal search
//                identifier.setId("");
//                identifier.setHidden("true");
//                schemeInfo.setIdentifier(identifier);
//                break;

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


}