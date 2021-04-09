package com.ccs.conclave.api.cii.data;

public enum SchemeRegistry {
    COMPANIES_HOUSE("GB-COH", "Companies House",  "GB"),
    DUN_AND_BRADSTREET("US-DUN", "Dun & Bradstreet",  "US"),
    DUN_AND_BRADSTREET_WALES("US-DUN", "Dun & Bradstreet",  "US"),
    DUN_AND_BRADSTREET_WITH_COH("US-DUN", "Dun & Bradstreet",  "US"),
    DUN_AND_BRADSTREET_WITH_CHC_AND_COH("US-DUN", "Dun & Bradstreet",  "US"),
    CHARITIES_COMMISSION("GB-CHC", "Charity Commission for England and Wales", "GB"),
    CHARITIES_COMMISSION_WITH_TWO_COH("GB-CHC", "Charity Commission for England and Wales",  "GB"),
    CHARITIES_COMMISSION_WITH_COH("GB-CHC", "Charity Commission for England and Wales",  "GB"),
    CHARITIES_COMMISSION_WITH_SC("GB-CHC", "Charity Commission for England and Wales",  "GB"),
    CHARITIES_COMMISSION_WITH_COH_AND_SC("GB-CHC", "Charity Commission for England and Wales",  "GB"),
    CHARITIES_COMMISSION_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS("GB-CHC", "Charity Commission for England and Wales",  "GB"),
    NORTHERN_CHARITY_WITH_COH("GB-NIC", "The Charity Commission for Northern Ireland",  "GB"),
    NORTHERN_CHARITY("GB-NIC", "The Charity Commission for Northern Ireland",  "GB"),
    SCOTLAND_CHARITY("GB-SC", "Office of The Scottish Charity Regulator (OSCR)",  "GB"),
    SCOTLAND_CHARITY_WITH_CHC_COH("GB-SC", "Office of The Scottish Charity Regulator (OSCR)",  "GB"),
    SCOTLAND_CHARITY_WITH_KNOWN_AND_UNKNOWN_IDENTIFIERS("GB-SC", "Office of The Scottish Charity Regulator (OSCR)",  "GB"),
    SALES_FORCE("GB-CCS", "The Crown Commercial Service",  "GB"),
    INVALID_SCHEME("GB-XYZ", "",  "");

    private final String schemeCode;
    private final String schemeName;
    private final String countryCode;

    SchemeRegistry(String schemeCode, String schemeName, String countryCode) {
        this.schemeCode = schemeCode;
        this.schemeName = schemeName;
        this.countryCode = countryCode;
    }

    public static String getSchemeCode(SchemeRegistry registry) {
        return registry.schemeCode;
    }

    public static String getSchemeName(SchemeRegistry registry) {
        return registry.schemeName;
    }

    public static String getSchemeCountryCode(SchemeRegistry registry) {
        return registry.countryCode;
    }
}