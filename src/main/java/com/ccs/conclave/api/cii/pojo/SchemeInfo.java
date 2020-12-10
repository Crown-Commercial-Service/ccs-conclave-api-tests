package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SchemeInfo {
    private String name;
    private Identifier identifier;
    private AdditionalIdentifiers additionalIdentifiers;
    private Address address;
    private ContactPoint contactPoint;
}
