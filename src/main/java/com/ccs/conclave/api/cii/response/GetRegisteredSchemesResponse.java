package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.RegisteredSchemeInfo;
import com.ccs.conclave.api.cii.pojo.SchemeInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetRegisteredSchemesResponse {
    private RegisteredSchemeInfo registeredSchemesInfo;

    public GetRegisteredSchemesResponse(RegisteredSchemeInfo schemeInfo) {
        registeredSchemesInfo = schemeInfo;
    }
}
