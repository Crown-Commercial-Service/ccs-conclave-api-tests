package com.ccs.conclave.api.cii.data;

public enum SchemeRegistry {
    COMPANIES_HOUSE("GB-COH", "Companies House", "https://api.company-information.service.gov.uk", "Company Registration Number", "GB"),
    DUN_AND_BRADSTREET("US-DUN", "Dun & Bradstreet", "https://plus.dnb.com", "DUNS Number", "US"),
    DUN_AND_BRADSTREET_WALES("US-DUN", "Dun & Bradstreet", "https://plus.dnb.com", "DUNS Number", "US"),
    DUN_AND_BRADSTREET_NI("US-DUN", "Dun & Bradstreet", "https://plus.dnb.com", "DUNS Number", "US"),
    DUN_AND_BRADSTREET_SCOTLAND("US-DUN", "Dun & Bradstreet", "https://plus.dnb.com", "DUNS Number", "US"),
    DUN_AND_BRADSTREET_IRELAND("US-DUN", "Dun & Bradstreet", "https://plus.dnb.com", "DUNS Number", "US"),
    DUN_AND_BRADSTREET_WITH_COH("US-DUN", "Dun & Bradstreet", "https://plus.dnb.com", "DUNS Number", "US"),
    DUN_AND_BRADSTREET_WITH_COH_AND_CHC("US-DUN", "Dun & Bradstreet", "https://plus.dnb.com", "DUNS Number", "US"),
    CHARITIES_COMMISSION("GB-CHC", "Charity Commission for England and Wales", "https://findthatcharity.uk", "Registered Charity Number", "GB"),
    CHARITIES_COMMISSION_WITH_TWO_COH("GB-CHC", "Charity Commission for England and Wales", "https://findthatcharity.uk", "Registered Charity Number", "GB"),
    CHARITIES_COMMISSION_WITH_SC("GB-CHC", "Charity Commission for England and Wales", "https://findthatcharity.uk", "Registered Charity Number", "GB"),
    CHARITIES_COMMISSION_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS("GB-CHC", "Charity Commission for England and Wales", "https://findthatcharity.uk", "Registered Charity Number", "GB"),
    NORTHERN_CHARITY_WITH_COH("GB-NIC", "The Charity Commission for Northern Ireland", "https://findthatcharity.uk", "Registered Charity Number", "GB"),
    NORTHERN_CHARITY("GB-NIC", "The Charity Commission for Northern Ireland", "https://findthatcharity.uk", "Registered Charity Number", "GB"),
    SCOTLAND_CHARITY("GB-SC", "Office of The Scottish Charity Regulator (OSCR)", "https://findthatcharity.uk", "Registered Charity Number", "GB"),
    SCOTLAND_CHARITY_WITH_COH_CHC("GB-SC", "Office of The Scottish Charity Regulator (OSCR)", "https://findthatcharity.uk", "Registered Charity Number", "GB"),
    INVALID_SCHEME("", "", "", "", "");

    private String schemeCode;
    private String schemeName;
    private String schemeURL;
    private String schemeIdentifier;
    private String countryCode;

    SchemeRegistry(String schemeCode, String schemeName, String url, String schemeIdentifier, String countryCode) {
        this.schemeCode = schemeCode;
        this.schemeName = schemeName;
        this.countryCode = countryCode;
        this.schemeIdentifier = schemeIdentifier;
        this.schemeURL = url;
    }

    public static String getSchemeCode(SchemeRegistry registry) {
        return registry.schemeCode;
    }

    public static String getSchemeName(SchemeRegistry registry) {
        return registry.schemeName;
    }

    public static String getSchemeURL(SchemeRegistry registry) {
        return registry.schemeURL;
    }

    public static String getSchemeCountryCode(SchemeRegistry registry) {
        return registry.countryCode;
    }

    public static String getSchemeIdentifier(SchemeRegistry registry) {
        return registry.schemeIdentifier;
    }
}