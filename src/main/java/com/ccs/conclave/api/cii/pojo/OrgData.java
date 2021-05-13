package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class OrgData {
    private String legalName = "Test Organisation";
    private String ciiOrganisationId;
    private ContactPoint contactPoint = new ContactPoint();
    private Address address = new Address();
    private String organisationUri = "";
    private boolean rightToBuy = false;
}
