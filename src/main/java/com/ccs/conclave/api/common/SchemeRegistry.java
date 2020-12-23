package com.ccs.conclave.api.common;

public enum SchemeRegistry {
    COMPANIES_HOUSE("GB-COH", "Companies House", "GB", "URL"),
    CHARITIES_COMMISSION("GB-CHC", "Charities Commission", "GB", "URL"),
    DUNS_AND_BRADSTREET("GB-DUN", "Duns and BradStreet", "GB", "URL"),
    NORTHERN_ISLAND_CHARITY("GB-NIC", "Duns and BradStreet", "GB", "URL"),
    SCOTLAND_CHARITY("GB-SC", "Duns and BradStreet", "GB", "URL");

    private String schemeCode;
    private String schemeName;
    private String schemeURL;
    private String countryCode;

    SchemeRegistry(String schemeCode, String schemeName, String countryCode, String url) {
        this.schemeCode = schemeCode;
        this.schemeName = schemeName;
        this.countryCode = countryCode;
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
