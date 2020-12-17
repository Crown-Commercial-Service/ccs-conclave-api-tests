package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Identifier {
    private String scheme;
    private String id;
    private String legalName;
    private String uri;
}
