package com.ccs.conclave.api.common;

public enum SchemeRegistry {
    COMPANIES_HOUSE("GB-COH"),
    CHARITIES_COMMISSION("GB-CHC"),
    DUNS_AND_BRADSTREET("US-DUN");

    private String schemeCode;

    SchemeRegistry(String code) {
        this.schemeCode = code;
    }

    public static String getSchemeCode(SchemeRegistry registry) {
        return registry.schemeCode;
    }
};
