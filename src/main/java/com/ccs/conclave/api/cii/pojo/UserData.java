package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UserData {
    private String firstName;
    private String lastName;
    private String userName;
    private int partyId = 1;
    private int title = 1;
    private String jobTitle;
    private String organisationId;
}
