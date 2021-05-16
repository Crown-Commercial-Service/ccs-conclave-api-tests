package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LoginDetails {
    private String challengeRequired;
    private String challengeName;
    private String sessionId;
    private String idToken;
    private String accessToken;
    private String refreshToken;
}
