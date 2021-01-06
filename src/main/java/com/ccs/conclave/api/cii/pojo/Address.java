package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Address {
    private String streetAddress;
    private String locality;
    private String region;
    private String postalCode;
    private String countryName;
}
