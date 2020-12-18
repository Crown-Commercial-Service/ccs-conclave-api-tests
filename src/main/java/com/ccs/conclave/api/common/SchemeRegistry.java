package com.ccs.conclave.api.common;

public enum SchemeRegistry {
    COMPANIES_HOUSE("GB-COH", "Companies House", "GB", "URL"),
    CHARITIES_COMMISSION("GB-CHC", "Charities Commission", "GB", "URL"),
    DUNS_AND_BRADSTREET("US-DUN", "Duns and BradStreet", "US", "URL");

    private static String schemeCode;
    private static String schemeName;
    private static String schemeURL;
    private static String countryCode;

    SchemeRegistry(String schemeCode, String schemeName, String countryCode, String url) {
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
