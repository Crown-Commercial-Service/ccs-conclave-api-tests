package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
public class Identifier {
    private String scheme;
    private String id;
    private String legalName;
    private String uri;

    public void setHidden(String hidden) {
        this.hidden = Optional.of(hidden);
    }

    private Optional<String> hidden;
}
