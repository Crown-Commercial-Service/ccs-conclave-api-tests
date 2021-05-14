package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UserData {
    private String firstName = "Test";
    private String lastName = "User";
    private String userName;
    private int partyId = 1;
    private int title = 1;
    private String jobTitle = "Mr";
    private String organisationId;
}
