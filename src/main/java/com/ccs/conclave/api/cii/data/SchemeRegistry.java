package com.ccs.conclave.api.cii.data;

public enum SchemeRegistry {
    COMPANIES_HOUSE("GB-COH", "Companies House",  "https://api.company-information.service.gov.uk", "Company Registration Number", "GB"),
    DUNS_AND_BRADSTREET("US-DUN", "Dun and Bradstreet",  "https://api.company-information.service.gov.uk", "DUNS Number","US"),
    CHARITIES_COMMISSION("GB-CHC", "Charities Commission for England and Wales",  "https://findthatcharity.uk" , "Registered Charity Number","GB"),
    NORTHERN_ISLAND_CHARITY("GB-NIC", "Northern Ireland Charities Commission","https://findthatcharity.uk", "Registered Charity Number", "GB"),
    SCOTLAND_CHARITY("GB-SC", "Scottish Charities Commission", "https://findthatcharity.uk", "Registered Charity Number", "GB"),
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
};
