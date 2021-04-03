package com.ccs.conclave.api.common;

import static com.ccs.conclave.api.common.BaseClass.getProperty;

public class Endpoints {

    public static String getSchemesURI = getProperty("getSchemesURI");
    public static String getSchemeInfoURI = getProperty("getSchemeInfoURI");
    public static String postSchemeInfoURI = getProperty("postSchemeInfoURI");
    public static String updateSchemeURI = getProperty("updateSchemeURI");
    public static String deleteSchemeURI = getProperty("deleteSchemeURI");
    public static String deleteOrganisationURI = getProperty("deleteOrganisationURI");
    public static String adminGetSchemeInfoURI = getProperty("adminGetSchemeInfoURI");
    public static String getRegisteredSchemesURI = getProperty("getRegisteredSchemesURI");
    public static String getAllRegisteredSchemesURI = getProperty("getAllRegisteredSchemesURI");
}
