package com.ccs.conclave.api.cii.data;

import com.ccs.conclave.api.cii.pojo.*;
import com.ccs.conclave.api.common.BaseClass;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.ccs.conclave.api.cii.data.SchemeRegistry.*;


public class OrgDataProvider extends BaseClass {
    private static MockDataProvider mockDataProvider;
    private static RegistryDataProvider registryDataProvider;

    public static SchemeInfo getExpectedSchemeInfo(SchemeRegistry schemeRegistry) {
        if (mockDataProvider != null) {
            return mockDataProvider.getExpectedSchemeInfo(schemeRegistry);
        } else if (registryDataProvider != null) {
            return registryDataProvider.getExpectedSchemeInfo(schemeRegistry);
        }
        return null;
    }

    // remove additional identifiers from test data to perform Update scheme tests
    public static SchemeInfo getExpSchemeInfoWithoutAddIdentifiers(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(schemeRegistry);

        SchemeInfo schemeInfoModified = new SchemeInfo();
        schemeInfoModified.setName(schemeInfo.getName());
        schemeInfoModified.setIdentifier(schemeInfo.getIdentifier());
        schemeInfoModified.setContactPoint(schemeInfo.getContactPoint());
        schemeInfoModified.setAddress(schemeInfo.getAddress());
        return schemeInfoModified;
    }

    public static SchemeInfo getExpSchemeInfoWithFirstAddIdentifier(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(schemeRegistry);

        SchemeInfo schemeInfoModified = new SchemeInfo();
        schemeInfoModified.setName(schemeInfo.getName());
        schemeInfoModified.setIdentifier(schemeInfo.getIdentifier());
        schemeInfoModified.setContactPoint(schemeInfo.getContactPoint());
        schemeInfoModified.setAddress(schemeInfo.getAddress());

        Assert.assertTrue(schemeInfo.getAdditionalIdentifiers().size() > 0, "No additional identifier in test data!!");
        schemeInfoModified.getAdditionalIdentifiers().add(schemeInfo.getAdditionalIdentifiers().get(0));
        return schemeInfoModified;
    }

    // This method returns only AdditionalSchemesInfo without SF Id
    public static List<AdditionalSchemeInfo> getExpSchemeInfoWithOnlyAddIdentifiersExceptSF(SchemeRegistry schemeRegistry) {
        List<AdditionalSchemeInfo> additionalSchemesInfo = new ArrayList<>();
        SchemeInfo schemeInfo = getExpectedSchemeInfo(schemeRegistry);
        for (Identifier addIdentifier : schemeInfo.getAdditionalIdentifiers()) {
            if (!addIdentifier.getScheme().equals(getSchemeCode(SALES_FORCE))) {
                AdditionalSchemeInfo additionalSchemeInfo = new AdditionalSchemeInfo();
                additionalSchemeInfo.setIdentifier(addIdentifier);
                additionalSchemesInfo.add(additionalSchemeInfo);
            }
        }
        return additionalSchemesInfo;
    }

    public static List<AdditionalSchemeInfo> getExpSchemeInfoWithOnlyAddIdentifiersIncludeSF(SchemeRegistry schemeRegistry) {
        List<AdditionalSchemeInfo> additionalSchemesInfo = new ArrayList<>();
        SchemeInfo schemeInfo = getExpectedSchemeInfo(schemeRegistry);
        for (Identifier addIdentifier : schemeInfo.getAdditionalIdentifiers()) {
                AdditionalSchemeInfo additionalSchemeInfo = new AdditionalSchemeInfo();
                additionalSchemeInfo.setIdentifier(addIdentifier);
                additionalSchemesInfo.add(additionalSchemeInfo);
        }
        return additionalSchemesInfo;
    }

    public static SchemeInfo getExpSchemeInfoWithoutSFIdentifier(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(schemeRegistry);
        SchemeInfo schemeInfoModified = new SchemeInfo();
        schemeInfoModified.setName(schemeInfo.getName());
        schemeInfoModified.setIdentifier(schemeInfo.getIdentifier());
        schemeInfoModified.setContactPoint(schemeInfo.getContactPoint());
        schemeInfoModified.setAddress(schemeInfo.getAddress());

        for (Identifier identifier : schemeInfo.getAdditionalIdentifiers()) {
            if (!identifier.getScheme().equals(getSchemeCode(SALES_FORCE))) {
                schemeInfoModified.getAdditionalIdentifiers().add(identifier);
            }
        }
        return schemeInfoModified;
    }



    public static SchemeInfo getExpSchemeInfoWithoutSFIdentifier(SchemeInfo schemeInfo) {
        SchemeInfo schemeInfoModified = new SchemeInfo();
        schemeInfoModified.setName(schemeInfo.getName());
        schemeInfoModified.setIdentifier(schemeInfo.getIdentifier());
        schemeInfoModified.setContactPoint(schemeInfo.getContactPoint());
        schemeInfoModified.setAddress(schemeInfo.getAddress());

        for (Identifier identifier : schemeInfo.getAdditionalIdentifiers()) {
            if (!identifier.getScheme().equals(getSchemeCode(SALES_FORCE))) {
                schemeInfoModified.getAdditionalIdentifiers().add(identifier);
            }
        }
        return schemeInfoModified;
    }

    public static SchemeInfo getExpSchemeInfoWithOnlySFIdentifier(SchemeRegistry schemeRegistry) {
        SchemeInfo schemeInfo = getExpectedSchemeInfo(schemeRegistry);
        SchemeInfo schemeInfoModified = new SchemeInfo();
        schemeInfoModified.setName(schemeInfo.getName());
        schemeInfoModified.setIdentifier(schemeInfo.getIdentifier());
        schemeInfoModified.setContactPoint(schemeInfo.getContactPoint());
        schemeInfoModified.setAddress(schemeInfo.getAddress());

        for (Identifier identifier : schemeInfo.getAdditionalIdentifiers()) {
            if (identifier.getScheme().equals(getSchemeCode(SALES_FORCE))) {
                schemeInfoModified.getAdditionalIdentifiers().add(identifier);
            }
        }
        return schemeInfoModified;
    }

    public static void initMockTestDataProvider() {
        mockDataProvider = new MockDataProvider();
    }

    public static void initRegistryTestDataProvider() {
        registryDataProvider = new RegistryDataProvider();
    }
}