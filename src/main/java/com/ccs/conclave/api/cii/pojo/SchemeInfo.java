package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SchemeInfo {
    private String name;
    private Identifier identifier;
    private List<AdditionalIdentifiers> additionalIdentifiers = new ArrayList<>();
    private Address address;
    private ContactPoint contactPoint;
}
