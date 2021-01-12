package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostSchemeInfo {
    private String name;
    private String OrgIdentifier;
    private Identifier identifier;
    private List<Identifier> additionalIdentifiers = new ArrayList<>();
    private Address address;
    private ContactPoint contactPoint;
}
