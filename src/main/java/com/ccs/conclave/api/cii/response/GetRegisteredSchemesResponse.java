package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.RegisteredSchemeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetRegisteredSchemesResponse {
    private RegisteredSchemeInfo registeredSchemesInfo;

    public GetRegisteredSchemesResponse(RegisteredSchemeInfo schemeInfo) {
        registeredSchemesInfo = schemeInfo;
    }
}
