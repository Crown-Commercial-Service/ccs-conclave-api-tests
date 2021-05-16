package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.LoginDetails;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ConclaveLoginResponse {

    private LoginDetails loginDetails;

    public ConclaveLoginResponse(LoginDetails data) {
        this.loginDetails = data;
    }
}
