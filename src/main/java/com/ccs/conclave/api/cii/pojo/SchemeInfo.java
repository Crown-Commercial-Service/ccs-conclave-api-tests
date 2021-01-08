package com.ccs.conclave.api.cii.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SchemeInfo {
    private String name;
    private Identifier identifier;
    private List<Identifier> additionalIdentifiers = new ArrayList<>();
    private Address address;
    private ContactPoint contactPoint;
}
