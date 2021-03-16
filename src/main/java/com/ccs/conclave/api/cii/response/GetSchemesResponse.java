package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.Scheme;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter @Getter
public class GetSchemesResponse {

    private List<Scheme> schemes;

    public GetSchemesResponse(List<Scheme> schemes) {
        this.schemes = schemes;
    }
}
