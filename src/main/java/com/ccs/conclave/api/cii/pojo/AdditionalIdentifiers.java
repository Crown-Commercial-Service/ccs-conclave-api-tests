package com.ccs.conclave.api.cii.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class AdditionalIdentifiers {
    private Identifier identifier;
}
