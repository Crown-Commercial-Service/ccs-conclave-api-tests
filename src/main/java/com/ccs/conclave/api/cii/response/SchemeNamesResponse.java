package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.SchemeName;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SchemeNamesResponse {
    private SchemeName schemeCOH;
    private SchemeName schemeDUNS;
    private SchemeName schemeCHC;
    private SchemeName schemeNIC;
    private SchemeName schemeSC;
}
