package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LoginData {
    private String username;
    private String password;
    private String client_id;
    private String client_secret;
}
