package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.Schemes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Setter @Getter
public class GetSchemesResponse {
    public List<Schemes> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<Schemes> schemes) {
        this.schemes = schemes;
    }

    private List<Schemes> schemes = new ArrayList<>();
 //   private Schemes[] schemes;
//    private Schemes schemeCOH;
//    private Schemes schemeDUNS;
//    private Schemes schemeCHC;
//    private Schemes schemeSC;
//    private Schemes schemeNIC;
//    private Schemes schemeCOH1;
//    private Schemes schemeDUNS1;
//    private Schemes schemeCHC1;
//    private Schemes schemeSC1;
//    private Schemes schemeNIC1;
}
